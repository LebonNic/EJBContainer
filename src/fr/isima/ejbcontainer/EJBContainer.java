package fr.isima.ejbcontainer;

import com.google.common.reflect.Reflection;
import fr.isima.ejbcontainer.annotations.EJB;
import fr.isima.ejbcontainer.annotations.PersistenceContext;
import fr.isima.ejbcontainer.annotations.Singleton;
import fr.isima.ejbcontainer.annotations.Stateless;
import fr.isima.ejbcontainer.exceptions.IncoherentAnnotationsUsage;
import fr.isima.ejbcontainer.exceptions.InjectionFailed;
import fr.isima.ejbcontainer.persistence.EntityManager;
import fr.isima.ejbcontainer.persistence.EntityManagerFactory;
import fr.isima.ejbcontainer.utils.ImplementationFinder;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EJBContainer {
    private static final Logger LOG = Logger.getLogger(EJBContainer.class.getName());

    private static EJBContainer container = null;
    private SingletonInstanceManager singletonInstanceManager = null;
    private StatelessInstanceManager statelessInstanceManager = null;
    private EntityManagerFactory entityManagerFactory = null;

    private EJBContainer() {
        this.singletonInstanceManager = new SingletonInstanceManager();
        this.statelessInstanceManager = new StatelessInstanceManager();
        this.entityManagerFactory = new EntityManagerFactory();
    }

    public SingletonInstanceManager getSingletonInstanceManager() {
        return this.singletonInstanceManager;
    }

    public StatelessInstanceManager getStatelessInstanceManager() {
        return this.statelessInstanceManager;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return this.entityManagerFactory;
    }

    public static EJBContainer getInstance() {
        if (EJBContainer.container == null) {
            synchronized (EJBContainer.class) {
                if (EJBContainer.container == null) {
                    LOG.log(Level.INFO, "Creation of the EJB container.");
                    EJBContainer.container = new EJBContainer();
                }
            }
        }
        return EJBContainer.container;
    }

    public <T> T createBean(Class<T> beanInterface) {
        Class<? extends T> clazz = ImplementationFinder.getImplementationForInterface(beanInterface);
        T proxy = createProxy(beanInterface, clazz);
        return proxy;
    }

    public <T> void manage(T object) {
        injectEJBInAnnotatedFields(object);
        injectEntityManagerInAnnotatedFields(object);
    }

    private <T> void injectEntityManagerInAnnotatedFields(T object) {
        Set<Field> persistenceContextAnnotatedFields = ReflectionUtils.getAllFields(object.getClass(),
                ReflectionUtils.withAnnotation(PersistenceContext.class));

        for (Field field : persistenceContextAnnotatedFields) {
            if (field.getType() == EntityManager.class) {
                LOG.log(Level.INFO, "Injecting an EntityManager in the field \"" + field.getName() + "\" of the " +
                        "object \"" + object.getClass().getName() + "\".");
                PersistenceContext persistenceContext = field.getAnnotation(PersistenceContext.class);
                String persistenceUnitName = persistenceContext.unitName();
                EntityManager manager = this.entityManagerFactory.createEntityManager(persistenceUnitName);

                boolean isAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    field.set(object, manager);
                    field.setAccessible(isAccessible);
                } catch (IllegalAccessException e) {
                    field.setAccessible(isAccessible);
                    throw new InjectionFailed("Failed to inject the EntityManager \"" + field.getName() + "\" " +
                            "in the object \"" + object.getClass().getName() + "\". Detail explanations: "
                            + e.getMessage() + ".");
                }
            }
        }
    }

    private <T> void injectEJBInAnnotatedFields(T object) {
        Set<Field> ejbAnnotatedFields = ReflectionUtils.getAllFields(object.getClass(),
                ReflectionUtils.withAnnotation(EJB.class));

        for (Field field : ejbAnnotatedFields) {
            LOG.log(Level.INFO, "Injecting an EJB in the field \"" + field.getName() + "\" of the object \"" +
                    object.getClass().getName() + "\".");
            Object beanToInject = createBean(field.getType());

            boolean isAccessible = field.isAccessible();
            try {
                field.setAccessible(true);
                field.set(object, beanToInject);
                field.setAccessible(isAccessible);
            } catch (IllegalAccessException e) {
                field.setAccessible(isAccessible);
                throw new InjectionFailed("Failed to inject the EJB \"" + field.getName() + "\" in the object \"" +
                        object.getClass().getName() + "\". Detail explanations: " + e.getMessage() + ".");
            }
        }
    }

    private <T> T createProxy(Class<T> beanInterface, Class<? extends T> clazz) {
        LOG.log(Level.INFO, "Creating a proxy to handle \"{0}\" methods calls.", clazz.getName());
        T proxy = null;

        boolean isSingleton = clazz.isAnnotationPresent(Singleton.class);
        boolean isStateless = clazz.isAnnotationPresent(Stateless.class);

        if (isSingleton && !isStateless) {
            proxy = Reflection.newProxy(beanInterface, new InvocationHandler(this.singletonInstanceManager,
                    beanInterface));
        } else if (!isSingleton && isStateless) {
            proxy = Reflection.newProxy(beanInterface, new InvocationHandler(this.statelessInstanceManager,
                    beanInterface));
        } else if (isSingleton && isStateless) {
            throw new IncoherentAnnotationsUsage("An EJB can't have both @Singleton and @Stateless annotations.");
        }

        return proxy;
    }
}
