package fr.isima.ejbcontainer;

import com.google.common.reflect.Reflection;
import fr.isima.ejbcontainer.annotations.EJB;
import fr.isima.ejbcontainer.annotations.Singleton;
import fr.isima.ejbcontainer.annotations.Stateless;
import fr.isima.ejbcontainer.exceptions.IncoherentAnnotationsUsage;
import fr.isima.ejbcontainer.exceptions.InjectionFailed;
import fr.isima.ejbcontainer.utils.ImplementationFinder;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EJBContainer {
    private static final Logger LOG = Logger.getLogger(EJBContainer.class.getName());

    private static EJBContainer container = null;
    private SingletonInstanceManager singletonManager = null;
    private StatelessInstanceManager statelessManager = null;

    private EJBContainer() {
        this.singletonManager = new SingletonInstanceManager();
        this.statelessManager = new StatelessInstanceManager();
    }

    public SingletonInstanceManager getSingletonInstanceManager() {
        return this.singletonManager;
    }

    public StatelessInstanceManager getStatelessInstanceManager() {
        return this.statelessManager;
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
            proxy = Reflection.newProxy(beanInterface, new InvocationHandler(this.singletonManager, beanInterface));
        } else if (!isSingleton && isStateless) {
            proxy = Reflection.newProxy(beanInterface, new InvocationHandler(this.statelessManager, beanInterface));
        } else if (isSingleton && isStateless) {
            throw new IncoherentAnnotationsUsage("An EJB can't have both @Singleton and @Stateless annotations.");
        }

        return proxy;
    }
}
