package fr.isima.ejbcontainer.utils;

import fr.isima.ejbcontainer.annotations.PostConstruct;
import fr.isima.ejbcontainer.annotations.PreDestroy;
import fr.isima.ejbcontainer.exceptions.MethodInvocationFailed;
import fr.isima.ejbcontainer.exceptions.MethodSignatureRejected;
import fr.isima.ejbcontainer.exceptions.MultipleImplementationsFound;
import org.reflections.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import static org.reflections.ReflectionUtils.withAnnotation;

public class BeanOperationManager {
    public static <T> void postConstruct(T bean) {
        Set<Method> methods = ReflectionUtils.getAllMethods(bean.getClass(), withAnnotation(PostConstruct.class));

        if (methods.size() > 1) {
            throw new MultipleImplementationsFound("Several methods are annotated @PostConstruct in the bean \""
                    + bean.getClass().getSimpleName() + "\".");
        }

        if (!methods.isEmpty()) {
            Method method = methods.iterator().next();
            if (method.getParameterCount() > 0) {
                throw new MethodSignatureRejected("A method annotated with @PostConstruct can't have any " +
                        "parameters. See method \"" + method.getName() + "\" in the bean \"" +
                        bean.getClass().getSimpleName() + "\".");
            }
            if (method.getReturnType() != Void.TYPE) {
                throw new MethodSignatureRejected("A method annotated with @PostConstruct has to return void. " +
                        "See method \"" + method.getName() + "\" in the bean \"" + bean.getClass().getSimpleName()
                        + "\".");
            }
            if (method.getExceptionTypes().length > 0) {
                throw new MethodSignatureRejected("A method annotated with @PostConstruct can't throw any unchecked " +
                        "exceptions. See method \"" + method.getName() + "\" in the bean \""
                        + bean.getClass().getSimpleName() + "\".");
            }
            if (Modifier.isStatic(method.getModifiers())) {
                throw new MethodSignatureRejected("A method annotated with @PostConstruct can't be static." +
                        " See method \"" + method.getName() + "\" in the bean \"" + bean.getClass().getSimpleName()
                        + "\".");
            }

            try {
                method.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MethodInvocationFailed("Failed to invoke method \"" + method.getName() + "\" of the bean \"" +
                        bean.getClass().getSimpleName() + "\". Detail explanations: " + e.getMessage() + ".");
            }
        }

    }

    public static <T> void preDestroy(T bean) {
        Set<Method> methods = ReflectionUtils.getAllMethods(bean.getClass(), withAnnotation(PreDestroy.class));

        if (methods.size() > 1) {
            throw new MultipleImplementationsFound("Several methods are annotated @PreDestroy in the bean \""
                    + bean.getClass().getSimpleName() + "\".");
        }

        if (!methods.isEmpty()) {
            Method method = methods.iterator().next();
            if (method.getParameterCount() > 0) {
                throw new MethodSignatureRejected("A method annotated with @PreDestroy can't have any " +
                        "parameters. See method \"" + method.getName() + "\" in the bean \"" +
                        bean.getClass().getSimpleName() + "\".");
            }
            if (method.getReturnType() != Void.TYPE) {
                throw new MethodSignatureRejected("A method annotated with @PreDestroy has to return void. " +
                        "See method \"" + method.getName() + "\" in the bean \"" + bean.getClass().getSimpleName()
                        + "\".");
            }
            if (method.getExceptionTypes().length > 0) {
                throw new MethodSignatureRejected("A method annotated with @PreDestroy can't throw any unchecked " +
                        "exceptions. See method \"" + method.getName() + "\" in the bean \""
                        + bean.getClass().getSimpleName() + "\".");
            }
            if (Modifier.isStatic(method.getModifiers())) {
                throw new MethodSignatureRejected("A method annotated with @PreDestroy can't be static." +
                        " See method \"" + method.getName() + "\" in the bean \"" + bean.getClass().getSimpleName()
                        + "\".");
            }

            try {
                method.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new MethodInvocationFailed("Failed to invoke method \"" + method.getName() + "\" of the bean \"" +
                        bean.getClass().getSimpleName() + "\". Detail explanations: " + e.getMessage() + ".");
            }
        }
    }
}
