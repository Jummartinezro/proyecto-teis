/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.service;

import java.util.List;

import com.demo.pojo.Customer;
import com.demo.util.GenreNotEditableException;
import com.demo.util.RequiredAttributeException;

public abstract interface ICustomerService {

    abstract long count();

    abstract Customer create();

    abstract void delete(Customer entity);

    abstract int deleteAll();

    abstract List<Customer> findAll();

    abstract Customer findById(Integer id);

    abstract void save(Customer entity) throws RequiredAttributeException;

    abstract Customer update(Customer entity) throws Exception;
}
