package org.david.ESC.entity.preandpost;

import jakarta.persistence.*;

@Embeddable
public class Auditoria {

    @PrePersist
    public void prePersistence(){
        System.out.println("Antes de guardar");
    }

    @PostPersist
    public void postPersistence(){
        System.out.println("Despues de guardar");
    }

    @PreUpdate
    public void preUpdate(){
        System.out.println("Antes de actualizar");
    }

    @PostUpdate
    public void postUpdate(){
        System.out.println("Despues de actualizar");
    }

    @PreRemove
    public void preRemove(){
        System.out.println("Antes de eliminar");
    }

    @PostRemove
    public void postRemove(){
        System.out.println("Despues de eliminar");
    }
}
