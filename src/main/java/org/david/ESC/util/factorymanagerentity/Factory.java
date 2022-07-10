package org.david.ESC.util.factorymanagerentity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Factory {

    private static final EntityManagerFactory FACTORY = factory();

    private static EntityManagerFactory factory(){
        return Persistence.createEntityManagerFactory("api");
    }

    public static EntityManager getManager(){
        return FACTORY.createEntityManager();
    }
}
