package fr.isima.ejbcontainer.errorTestClasses;

import fr.isima.ejbcontainer.annotations.Singleton;

@Singleton
public class FooSingletonImplDuplicate implements IFooBean {
    @Override
    public void buisinessMethod() {

    }
}
