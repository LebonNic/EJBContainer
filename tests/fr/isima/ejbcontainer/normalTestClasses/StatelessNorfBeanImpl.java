package fr.isima.ejbcontainer.normalTestClasses;

import fr.isima.ejbcontainer.annotations.*;
import fr.isima.ejbcontainer.persistence.EntityManager;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StatelessNorfBeanImpl implements INorfBean{

    @PersistenceContext(unitName = "mock")
    EntityManager entityManager = null;

    private boolean isPostConstructMethodCalled = false;

    @PostConstruct
    public void finalizeConstruction(){
        this.isPostConstructMethodCalled = true;
    }

    @Override
    public void businessMethod() {

    }

    @Override
    public void save(Object entity){
        entityManager.persist(entity);
    }

    @Override
    public void delete(Object entity){
        entityManager.remove(entity);
    }

    @Override
    public boolean isPostConstructMethodCalled(){
        return this.isPostConstructMethodCalled;
    }
}
