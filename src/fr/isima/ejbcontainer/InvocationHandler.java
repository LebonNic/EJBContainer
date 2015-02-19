package fr.isima.ejbcontainer;

import com.google.common.reflect.AbstractInvocationHandler;
import fr.isima.ejbcontainer.exceptions.MethodInvocationFailed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvocationHandler extends AbstractInvocationHandler {
    private static final Logger LOG = Logger.getLogger(InvocationHandler.class.getName());
    private InstanceManager instanceManager;
    private Class<?> beanInterface;

    public InvocationHandler(InstanceManager instanceManager, Class<?> beanInterface){
        this.instanceManager = instanceManager;
        this.beanInterface = beanInterface;
    }

    @Override
    protected Object handleInvocation(Object o, Method method, Object[] args) throws Throwable{
        LOG.log(Level.INFO, "Executes method \"{0}\" from \"{1}\" with arguments \"{2}\".",
                new Object[]{method.getName(), this.beanInterface.getName(), args.toString()});

        Object bean = instanceManager.getInstance(this.beanInterface);
        Object result;

        try {
            result = method.invoke(bean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MethodInvocationFailed("An error occurred during the call of the method \"" + method.getName()
                    + "\" from \"" + this.beanInterface + "\". Detail explanations: " + e.getMessage() + ".");
        }

        return result;
    }
}
