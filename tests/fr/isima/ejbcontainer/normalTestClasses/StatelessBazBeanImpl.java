package fr.isima.ejbcontainer.normalTestClasses;

import fr.isima.ejbcontainer.annotations.EJB;
import fr.isima.ejbcontainer.annotations.Stateless;

@Stateless
public class StatelessBazBeanImpl implements IBazBean {

    @EJB
    private INorfBean bean;

    @Override
    public void businessMethod() {
        bean.businessMethod();
    }
}
