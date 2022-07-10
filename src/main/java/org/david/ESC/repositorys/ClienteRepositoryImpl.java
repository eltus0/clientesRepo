package org.david.ESC.repositorys;

import jakarta.persistence.EntityManager;
import org.david.ESC.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryImpl implements Repository<Cliente> {

    private EntityManager em;

    public ClienteRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Cliente> enlistar() {
        return em.createQuery("select c from Cliente c", Cliente.class).getResultList();
    }

    @Override
    public Cliente byid(long id) {
        return em.createQuery("select new Cliente(cliente_id, nombre, apellido, edad) from Cliente c where cliente_id =:parameter", Cliente.class)
                .setParameter("parameter", id)
                .getSingleResult();
    }

    @Override
    public void persistence(Cliente cliente) {

        if (cliente.getCliente_id() != null && cliente.getCliente_id() > 0) {
            em.merge(cliente);
        } else {
            em.persist(cliente);
        }
    }

    @Override
    public void delete(long id) {
        Cliente c = em.find(Cliente.class, id);
        System.out.println("adios cliente: " + c);
        em.remove(c);
    }
}
