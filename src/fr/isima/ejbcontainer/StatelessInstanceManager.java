package fr.isima.ejbcontainer;

import fr.isima.ejbcontainer.exceptions.StatelessBeanInstantiationFailed;
import fr.isima.ejbcontainer.utils.ImplementationFinder;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatelessInstanceManager implements InstanceManager {
    private static final Logger LOG = Logger.getLogger(StatelessInstanceManager.class.getName());
    private Map<Class<?>, Stack<Object>> interfaceToImplementationPool = new HashMap<>();

    @Override
    public <T> T getInstance(Class<T> beanInterface) {
        LOG.log(Level.INFO, "The manager tries to answer a \"getInstance\" request for the interface \"" +
                beanInterface.getName() + "\".");
        Stack<Object> implementationPool = this.interfaceToImplementationPool.get(beanInterface);
        T bean;

        if (implementationPool != null) {
            if (implementationPool.isEmpty()) {
                bean = instantiateEJB(beanInterface);
            } else {
                LOG.log(Level.INFO, "A bean is retrieved from the pool to support the \"getInstance\" call.");
                bean = (T) implementationPool.pop();
            }
        } else {
            LOG.log(Level.INFO, "A new beans pool is created to support the \"getInstance\" call.");
            implementationPool = new Stack<>();
            this.interfaceToImplementationPool.put(beanInterface, implementationPool);
            bean = instantiateEJB(beanInterface);
        }

        return bean;
    }

    private <T> T instantiateEJB(Class<T> beanInterface) {
        LOG.log(Level.INFO, "A new bean is created to support the \"getInstance\" call.");
        T bean = null;
        Class<? extends T> clazz = ImplementationFinder.getImplementationForInterface(beanInterface);
        try {
            bean = clazz.newInstance();
            EJBContainer.getInstance().manage(bean);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new StatelessBeanInstantiationFailed("Failed to instantiate a stateless bean of type: \"" +
                    clazz.getName() + "\". Detail explanations: " + e.getMessage() + ".");
        }
        return bean;
    }

    @Override
    public <T> void freeInstance(Class<?> beanInterface, T bean) {
        Stack<Object> implementationPool = this.interfaceToImplementationPool.get(beanInterface);
        if (implementationPool != null) {
            implementationPool.push(bean);
        } else {
            //TODO throw an exception
        }

    }

    public Stack<Object> getImplementationPool(Class<?> beanInterface) {
        Stack<Object> implementationPool = this.interfaceToImplementationPool.get(beanInterface);
        return implementationPool;
    }
}
