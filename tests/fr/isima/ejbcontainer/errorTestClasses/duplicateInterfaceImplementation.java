package fr.isima.ejbcontainer.errorTestClasses;

import fr.isima.ejbcontainer.annotations.Singleton;

@Singleton
public class DuplicateInterfaceImplementation implements AnOtherBeanInterface {
    @Override
    public void buisinessMethod() {

    }
}
