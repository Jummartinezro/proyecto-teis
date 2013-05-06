/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.service;

import com.demo.model.dao.CustomerDAO;
import com.demo.model.dao.ICrudDAO;
import com.demo.pojo.Customer;
import com.demo.util.GenreNotEditableException;
import com.demo.util.RequiredAttributeException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "customerServiceImpl")
@ApplicationScoped
public class CustomerServiceImpl implements ICustomerService {

    @ManagedProperty("#{customerDAO}")
    private ICrudDAO<Customer, Integer> dao;

    @Override
    public long count() {
        return this.dao.count();
    }

    @Override
    public Customer create() {
        return this.dao.create();
    }

    @Override
    public void delete(Customer entity) {
        this.dao.delete(entity);
    }

    @Override
    public int deleteAll() {
        return this.dao.deleteAll();
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<Customer>(this.dao.findAll());
    }

    @Override
    public Customer findById(Integer id) {
        return this.dao.findById(id);
    }

    @PostConstruct
    public void postConstruct() {
        if (this.dao == null) {
            this.dao = new CustomerDAO();
            System.err.println("*** WARNING *** Dao not injected.");
        }
    }

    @PreDestroy
    public void preDestroy() {
        this.dao = null;
    }

    @Override
    public void save(Customer entity) throws RequiredAttributeException {
        try {
            this.dao.save(entity);
        } catch (Exception ex) {
            throw new RequiredAttributeException("Trying to persist an object without one or many empty (null) attributes");
        }
    }

    public void setDao(ICrudDAO<Customer, Integer> dao) {
        this.dao = dao;
    }

    @Override
    public Customer update(Customer entity) throws GenreNotEditableException {
        Customer ret = this.dao.findById(entity.getId());
        if (isChangingGender(entity)) {
            throw new GenreNotEditableException("Trying to change the gender of a customer");
        } else {
            ret = this.dao.update(entity);
        }
        return ret;
    }

    /**
     * Detect if the customer's gender is gone to be changed.
     *
     * @param customer the customer to be analized
     * @return
     * <code>true</code> if the customer's gender is gone to be changed,
     * <code>false</code> in other case.
     */
    private boolean isChangingGender(Customer customer) {
        return findById(customer.getId()).getGender() != customer.getGender()
                ? true : false;
    }
}