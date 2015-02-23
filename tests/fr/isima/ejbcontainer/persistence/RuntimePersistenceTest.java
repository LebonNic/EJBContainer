package fr.isima.ejbcontainer.persistence;

import fr.isima.ejbcontainer.EJBContainer;
import fr.isima.ejbcontainer.normalTestClasses.INorfBean;
import fr.isima.ejbcontainer.persistence.command.PersistCommand;
import fr.isima.ejbcontainer.persistence.command.RemoveCommand;
import org.junit.Test;

import static org.junit.Assert.*;


public class RuntimePersistenceTest {
    @Test
    public void testEntityManagerPersistence(){
        int initialCommandsCount, finalCommandsCount;
        INorfBean bean = EJBContainer.getInstance().createBean(INorfBean.class);
        Object mockedEntity = new Object();

        initialCommandsCount = PersistCommand.getPersistCommandCount();
        bean.save(mockedEntity);
        bean.save(mockedEntity);
        finalCommandsCount = PersistCommand.getPersistCommandCount();

        assertEquals(finalCommandsCount - initialCommandsCount, 2);
    }

    @Test
    public void testEntityManagerDeletion(){
        int initialCommandsCount, finalCommandsCount;
        INorfBean bean = EJBContainer.getInstance().createBean(INorfBean.class);
        Object mockedEntity = new Object();

        initialCommandsCount = RemoveCommand.getRemoveCommandCount();
        bean.delete(mockedEntity);
        finalCommandsCount = RemoveCommand.getRemoveCommandCount();

        assertEquals(finalCommandsCount - initialCommandsCount, 1);
    }
}
