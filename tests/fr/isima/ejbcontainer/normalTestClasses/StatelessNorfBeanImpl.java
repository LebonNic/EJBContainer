package fr.isima.ejbcontainer.normalTestClasses;

import fr.isima.ejbcontainer.annotations.Stateless;
import fr.isima.ejbcontainer.annotations.TransactionAttribute;
import fr.isima.ejbcontainer.annotations.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StatelessNorfBeanImpl implements INorfBean{
    @Override
    public void businessMethod() {

    }
}
