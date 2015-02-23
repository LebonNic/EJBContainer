package fr.isima.ejbcontainer.persistence;

import fr.isima.ejbcontainer.EJBContainer;
import fr.isima.ejbcontainer.exceptions.ImplementationNotFound;
import fr.isima.ejbcontainer.normalTestClasses.BusinessServices;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityManagerFactoryTest {

    @Test
    public void testEntityManagerInjection(){
        BusinessServices services = new BusinessServices();
        EJBContainer.getInstance().manage(services);
        assertNotNull(services.publicEntityManager);
        assertNotNull(services.getProtectedEJB());
        assertNotNull(services.getPrivateEJB());
    }

    @Test (expected = ImplementationNotFound.class)
    public void testCreateAnEntityManagerWithAnImaginaryUnitName(){
        EntityManagerFactory factory = new EntityManagerFactory();
        factory.createEntityManager("AnImaginaryUnitName");
    }
}