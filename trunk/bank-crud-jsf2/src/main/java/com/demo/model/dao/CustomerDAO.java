/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.demo.pojo.Customer;

@ManagedBean(name = "customerDAO")
@ApplicationScoped
public class CustomerDAO implements ICrudDAO<Customer, Integer> {

    private EntityManager entityManager;

    @Override
    public long count() {
        Query query = this.entityManager.createNamedQuery("Customer.countAll");

        Number result = (Number) query.getSingleResult();

        return result.longValue();
    }

    @Override
    public Customer create() {
        return new Customer();
    }

    @Override
    public void delete(Customer entity) {
        this.entityManager.getTransaction().begin();
        entity = this.entityManager.merge(entity);
        this.entityManager.remove(entity);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public int deleteAll() {
        Query query = this.entityManager.createNamedQuery("Customer.deleteAll");

        this.entityManager.getTransaction().begin();
        int result = query.executeUpdate();
        this.entityManager.getTransaction().commit();

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> findAll() {
        Query query = this.entityManager.createNamedQuery("Customer.findAll");

        List<Customer> result = query.getResultList();

        return result;
    }

    @Override
    public Customer findById(Integer id) {
        Customer entity = this.entityManager.find(Customer.class, id);

        return entity;
    }

    @PostConstruct
    public void postConstruct() {
        if (this.entityManager == null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");

            this.entityManager = entityManagerFactory.createEntityManager();
        }
    }

    @PreDestroy
    public void preDestroy() {
        this.entityManager = null;
    }

    @Override
    public void save(Customer entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(entity);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public Customer update(Customer entity) {
        this.entityManager.getTransaction().begin();
        entity = this.entityManager.merge(entity);
        this.entityManager.getTransaction().commit();

        return entity;
    }
}