/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.demo.model.dao.CustomerDAO;
import com.demo.model.dao.ICrudDAO;
import com.demo.pojo.Customer;

@ManagedBean(name = "customerServiceImpl")
@ApplicationScoped
public class CustomerServiceImpl implements ICustomerService
{
    @ManagedProperty("#{customerDAO}")
    private ICrudDAO<Customer, Integer> dao;

    @Override
    public long count()
    {
        return this.dao.count();
    }

    @Override
    public Customer create()
    {
        return this.dao.create();
    }

    @Override
    public void delete(Customer entity)
    {
        this.dao.delete(entity);
    }

    @Override
    public int deleteAll()
    {
        return this.dao.deleteAll();
    }

    @Override
    public List<Customer> findAll()
    {
        return new ArrayList<Customer>(this.dao.findAll());
    }

    @Override
    public Customer findById(Integer id)
    {
        return this.dao.findById(id);
    }

    @PostConstruct
    public void postConstruct()
    {
        if (this.dao == null)
        {
            this.dao = new CustomerDAO();
            System.err.println("*** WARNING *** Dao not injected.");
        }
    }

    @PreDestroy
    public void preDestroy()
    {
        this.dao = null;
    }

    @Override
    public void save(Customer entity)
    {
        this.dao.save(entity);
    }

    public void setDao(ICrudDAO<Customer, Integer> dao)
    {
        this.dao = dao;
    }

    @Override
    public Customer update(Customer entity)
    {
        return this.dao.update(entity);
    }
}