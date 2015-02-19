package fr.isima.ejbcontainer.errorTestClasses;

import fr.isima.ejbcontainer.annotations.Singleton;
import fr.isima.ejbcontainer.annotations.Stateless;

@Stateless
@Singleton
public class BarImplWithIncoherentAnnotationsUsage implements IBarBean {
    @Override
    public void buisinessMethod() {

    }
}
