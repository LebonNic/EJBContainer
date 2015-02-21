package fr.isima.ejbcontainer.normalTestClasses;

import fr.isima.ejbcontainer.annotations.EJB;
import fr.isima.ejbcontainer.annotations.Singleton;

@Singleton
public class SingletonBeanImpl implements IBean {

    @EJB
    IQuxBean bean;

    @Override
    public void businessMethod() {
        bean.businessMethod();
    }
}
