/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.service;

import java.util.List;

import com.demo.pojo.Customer;
import com.demo.util.GenreNotEditableException;

public abstract interface ICustomerService
{
    public abstract long count();

    public abstract Customer create();

    public abstract void delete(Customer entity);

    public abstract int deleteAll();

    public abstract List<Customer> findAll();

    public abstract Customer findById(Integer id);

    public abstract void save(Customer entity);

    public abstract Customer update(Customer entity) throws GenreNotEditableException;
}