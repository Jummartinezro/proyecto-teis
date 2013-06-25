/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.service;

import java.util.List;

import com.demo.pojo.Customer;
import com.demo.util.GenreNotEditableException;
import com.demo.util.RequiredAttributeException;

public abstract interface ICustomerService {

    long count();

    Customer create();

    void delete(Customer entity);

    int deleteAll();

    List<Customer> findAll();

    Customer findById(Integer id);

    void save(Customer entity) throws RequiredAttributeException;

    Customer update(Customer entity) throws Exception;
}
