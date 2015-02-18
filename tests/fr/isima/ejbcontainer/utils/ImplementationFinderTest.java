package fr.isima.ejbcontainer.utils;

import fr.isima.ejbcontainer.errorTestClasses.AnNonImplementedInterface;
import fr.isima.ejbcontainer.errorTestClasses.IFooBean;
import fr.isima.ejbcontainer.exceptions.ImplementationNotFound;
import fr.isima.ejbcontainer.exceptions.MultipleImplementationsFound;
import fr.isima.ejbcontainer.normalTestClasses.BeanInterface;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImplementationFinderTest {
    @Test
    public void testToFindImplementation(){
        Class<? extends BeanInterface> clazz = ImplementationFinder.getImplementationForInterface(BeanInterface.class);
        assertNotNull(clazz);
    }

    @Test(expected = MultipleImplementationsFound.class)
    public void testToFindDuplicateImplementations(){
        ImplementationFinder.getImplementationForInterface(IFooBean.class);
    }

    @Test(expected = ImplementationNotFound.class)
    public void testToFindNonImplementedInterface(){
        ImplementationFinder.getImplementationForInterface(AnNonImplementedInterface.class);
    }
}