package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.annotations.Singleton;
import fr.isima.ejbcontainer.exceptions.SingletonBeanInstantiationFailed;
import fr.isima.ejbcontainer.utils.ImplementationFinder;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingletonInstanceManager implements InstanceManager {
    private static final Logger LOG = Logger.getLogger(SingletonInstanceManager.class.getName());
    private Map<Class<?>, Object> interfaceToImplementation = new HashMap<>();

    @Override
    public <T> T getInstance(Class<T> beanInterface) {
        LOG.log(Level.INFO, "The manager tries to answer a \"getInstance\" request for the interface \"" +
                beanInterface.getName() + "\".");
        T bean = (T) this.interfaceToImplementation.get(beanInterface);

        if (bean == null) {
            synchronized (Singleton.class) {
                if (this.interfaceToImplementation.get(beanInterface) == null) {
                    Class<? extends T> clazz = ImplementationFinder.getImplementationForInterface(beanInterface);
                    try {
                        bean = clazz.newInstance();
                        EJBContainer.getInstance().manage(bean);
                        this.interfaceToImplementation.put(clazz, bean);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new SingletonBeanInstantiationFailed("Failed to instantiate the singleton bean named: \"" +
                                clazz.getName() + "\". Detail explanations: " + e.getMessage() + ".");
                    }
                } else {
                    bean = (T) this.interfaceToImplementation.get(beanInterface);
                }
            }
        }
        return bean;
    }

    @Override
    public <T> void freeInstance(Class<?> beanInterface, T bean) {

    }

    public int getInstanceCount() {
        return this.interfaceToImplementation.size();
    }
}
