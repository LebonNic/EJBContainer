package fr.isima.ejbcontainer.persistence;

import fr.isima.ejbcontainer.annotations.PersistenceContext;
import fr.isima.ejbcontainer.exceptions.EntityManagerInstantiationFailed;
import fr.isima.ejbcontainer.exceptions.ImplementationNotFound;
import fr.isima.ejbcontainer.exceptions.MultipleImplementationsFound;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntityManagerFactory {
    private static final Logger LOG = Logger.getLogger(EntityManagerFactory.class.getName());

    private Map<String, Class<? extends EntityManager>> persistenceUnitNameToImplementation = new HashMap<>();
    private Map<Class<? extends EntityManager>, Object> classToInstance =  new HashMap<>();

    public EntityManagerFactory(){
        loadEntityManagerImplementations();
    }

    private void loadEntityManagerImplementations(){
        LOG.log(Level.INFO, "Searching for entity managers...");

        Reflections reflections = new Reflections();

        for (Class<? extends EntityManager> manager : reflections.getSubTypesOf(EntityManager.class)){
            if(manager.isAnnotationPresent(PersistenceContext.class)){
                PersistenceContext annotation = manager.getAnnotation(PersistenceContext.class);
                String persistenceUnitName = annotation.unitName();

                if(this.persistenceUnitNameToImplementation.get(persistenceUnitName) != null){
                    throw new MultipleImplementationsFound("The persistence unit name \"" + persistenceUnitName + "\"" +
                            "is used with more than one implementation of an EntityManger.");
                }
                LOG.log(Level.INFO, "EntityManager \"" + manager.getName() + "\" found for persistence unit name \""
                        + persistenceUnitName + "\".");
                this.persistenceUnitNameToImplementation.put(persistenceUnitName, manager);
            }
        }
        LOG.log(Level.INFO, "Searching done.");
    }

    public EntityManager createEntityManager(String persistenceUnitName){
        Class<? extends EntityManager> managerImplementation =
                this.persistenceUnitNameToImplementation.get(persistenceUnitName);
        EntityManager manager;

        if(managerImplementation == null){
            throw new ImplementationNotFound("No implementation of an EntityManager with the persistence unit name \"" +
                    persistenceUnitName + "\" was found");
        }

        try {
            LOG.log(Level.INFO, "");
            manager = managerImplementation.newInstance();
            this.classToInstance.put(managerImplementation, manager);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EntityManagerInstantiationFailed("Failed to instantiate the EntityManager named \""
                    + managerImplementation.getSimpleName() + "\". Detail explanations: " + e.getMessage() + ".");
        }

        return manager;
    }
}
