package fr.isima.ejbcontainer.errorTestClasses;

import fr.isima.ejbcontainer.annotations.Singleton;
import fr.isima.ejbcontainer.annotations.Stateless;

@Stateless
@Singleton
public interface IBarBean {
    public void buisinessMethod();
}
