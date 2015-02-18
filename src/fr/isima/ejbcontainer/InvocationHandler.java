package fr.isima.ejbcontainer;

import com.google.common.reflect.AbstractInvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
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
        Object result = null;

        try {
            result = method.invoke(bean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            //TODO Throw EJBInvocationException
        }

        return result;
    }
}