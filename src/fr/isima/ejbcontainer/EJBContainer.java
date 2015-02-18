package fr.isima.ejbcontainer;

import com.google.common.reflect.Reflection;
import fr.isima.ejbcontainer.annotations.Singleton;
import fr.isima.ejbcontainer.annotations.Stateless;
import fr.isima.ejbcontainer.exceptions.IncoherentAnnotationsUsage;
import fr.isima.ejbcontainer.utils.ImplementationFinder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EJBContainer {
    private static final Logger LOG = Logger.getLogger(EJBContainer.class.getName());

    private static EJBContainer container = null;
    private SingletonInstanceManager singletonManager = null;

    private EJBContainer(){
        this.singletonManager = new SingletonInstanceManager();
    }

    public SingletonInstanceManager getSingletonInstanceManager(){
        return this.singletonManager;
    }

    public static EJBContainer getInstance(){
        if(EJBContainer.container == null){
            synchronized (EJBContainer.class){
                if(EJBContainer.container == null){
                    LOG.log(Level.INFO, "Creation of the EJB container.");
                    EJBContainer.container = new EJBContainer();
                }
            }
        }
        return EJBContainer.container;
    }

    public <T> T createBean(Class<T> beanInterface){
        Class<? extends T> clazz = ImplementationFinder.getImplementationForInterface(beanInterface);
        T proxy = createProxy(beanInterface, clazz);
        return proxy;
    }

    private <T> T createProxy(Class<T> beanInterface, Class<? extends T> clazz){
        LOG.log(Level.INFO, "Creating a proxy to handle \"{0}\" methods calls.", clazz.getName());
        T proxy = null;

        boolean isSingleton = clazz.isAnnotationPresent(Singleton.class);
        boolean isStateless = clazz.isAnnotationPresent(Stateless.class);

        if(isSingleton && !isStateless){
            proxy = Reflection.newProxy(beanInterface, new InvocationHandler(this.singletonManager, beanInterface));
        }
        else if(!isSingleton && isStateless){
            //TODO create a proxy linked to a StatelessInstanceManager
        }
        else if(isSingleton && isStateless){
            throw new IncoherentAnnotationsUsage("An EJB can't have both @Singleton and @Stateless annotations.");
        }

        return proxy;
    }
}
