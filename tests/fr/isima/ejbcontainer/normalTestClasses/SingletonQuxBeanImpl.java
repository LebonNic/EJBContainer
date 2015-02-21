package fr.isima.ejbcontainer.normalTestClasses;

import fr.isima.ejbcontainer.annotations.Singleton;
import fr.isima.ejbcontainer.annotations.TransactionAttribute;
import fr.isima.ejbcontainer.annotations.TransactionAttributeType;

@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SingletonQuxBeanImpl implements IQuxBean {

    @Override
    public void businessMethod() {
    }
}
