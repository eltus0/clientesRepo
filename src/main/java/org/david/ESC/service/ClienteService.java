package org.david.ESC.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import org.david.ESC.entity.Cliente;
import org.david.ESC.repositorys.ClienteRepositoryImpl;
import org.david.ESC.repositorys.Repository;
import org.david.ESC.util.OrderBy;

import java.util.List;

public class ClienteService {

    private EntityManager em ;
    private Repository<Cliente> clienteRepo;

    public ClienteService(EntityManager em) {
        this.clienteRepo = new ClienteRepositoryImpl(em);
        this.em = em;
    }

    public List<Cliente> jpaenlistar() {
        return clienteRepo.enlistar();
    }

    public Cliente jpabyid(Long id) {
        return clienteRepo.byid(id);
    }

    public void jpapersistence(Cliente cliente) {
        try {
            em.getTransaction().begin();
            clienteRepo.persistence(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void jparemove(Long id) {
        try {
            em.getTransaction().begin();
            clienteRepo.delete(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    private CriteriaBuilder criteria;
    private CriteriaQuery<Cliente> clienteCriteriaQuery;
    private CriteriaQuery<Object[]> datosCriteriaQuery;
    private CriteriaQuery<String> datoStringCriteriaQuery;
    private Root<Cliente> fromcliente;


    public List<Cliente> crienlistconcidenceandorder(String concidence, OrderBy order){
        criteria = em.getCriteriaBuilder();

        clienteCriteriaQuery = criteria.createQuery(Cliente.class);
        fromcliente = clienteCriteriaQuery.from(Cliente.class);

        Predicate predicateconcidence = criteria.like(fromcliente.get("nombre"), "%" + concidence + "%");
        Order orderby = order.toString().equals("asc")? criteria.asc(fromcliente.get("nombre")): criteria.desc(fromcliente.get("nombre"));
        clienteCriteriaQuery.select(fromcliente).where(predicateconcidence).orderBy(orderby);

        return em.createQuery(clienteCriteriaQuery).getResultList();
    }

    public List<Object[]> cridatosdeclientesin(Integer... ids){
        criteria = em.getCriteriaBuilder();

        datosCriteriaQuery = criteria.createQuery(Object[].class);
        fromcliente = datosCriteriaQuery.from(Cliente.class);

        datosCriteriaQuery.multiselect(fromcliente.get("cliente_id"), fromcliente.get("nombre"), fromcliente.get("apellido"), fromcliente.get("edad"))
                .where(fromcliente.get("cliente_id").in(ids));

        return em.createQuery(datosCriteriaQuery).getResultList();
    }

    public String crinombrecompleteid(Long id){
        criteria = em.getCriteriaBuilder();

        datoStringCriteriaQuery = criteria.createQuery(String.class);
        fromcliente = datoStringCriteriaQuery.from(Cliente.class);

        ParameterExpression<Long> paramid = criteria.parameter(Long.class, "idparam");
        Predicate predicateequals = criteria.equal(fromcliente.get("cliente_id"), paramid);

        datoStringCriteriaQuery.select(criteria.upper(fromcliente.get("nombre"))).where(predicateequals);
        return em.createQuery(datoStringCriteriaQuery).setParameter("idparam", id).getSingleResult();
    }

}
