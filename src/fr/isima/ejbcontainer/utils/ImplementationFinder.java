package fr.isima.ejbcontainer.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.isima.ejbcontainer.annotations.Singleton;
import fr.isima.ejbcontainer.annotations.Stateless;
import fr.isima.ejbcontainer.exceptions.ImplementationNotFound;
import fr.isima.ejbcontainer.exceptions.MultipleImplementationsFound;
import org.reflections.Reflections;

public class ImplementationFinder {
    private static final Logger LOG = Logger.getLogger(ImplementationFinder.class.getName());
    private static Map<Class<?>, Class<?>> interfaceToClass = new HashMap<>();

    private static <T> Class<? extends T> findImplementationForInterface(Class<T> beanInterface) {
        Class<?> implementation = null;

        Reflections reflection = new Reflections();

        Set<Class<?>> classes = reflection.getTypesAnnotatedWith(Singleton.class);
        classes.addAll(reflection.getTypesAnnotatedWith(Stateless.class));

        for (Class<?> clazz : classes) {
            if (beanInterface.isAssignableFrom(clazz)) {
                if (implementation == null) {
                    LOG.log(Level.INFO, "Class \"{0}\" matches the interface \"{1}\".", new Object[]{clazz.getName(),
                            beanInterface.getName()});
                    implementation = clazz;
                } else {
                    throw new MultipleImplementationsFound("The interface named \"" + beanInterface.getName()
                            + "\" matches more than one implementation.");
                }
            }
        }

        if (implementation == null) {
            throw new ImplementationNotFound("No implementations corresponding to the interface \"" +
                    beanInterface.getName() + "\" have been found.");
        }

        return (Class<? extends T>) implementation;
    }

    public static <T> Class<? extends T> getImplementationForInterface(Class<T> beanInterface) {
        LOG.log(Level.INFO, "Searching implementations for interface \"{0}\"...", beanInterface.getName());
        Class<? extends T> clazz = (Class<? extends T>) ImplementationFinder.interfaceToClass.get(beanInterface);

        if (clazz == null) {
            clazz = ImplementationFinder.findImplementationForInterface(beanInterface);
            ImplementationFinder.interfaceToClass.put(beanInterface, clazz);
        } else {
            LOG.log(Level.INFO, "The finder uses the HashMap to get the matching implementation faster.");
        }

        return clazz;
    }
}
