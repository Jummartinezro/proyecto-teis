/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.dao;

import java.io.Serializable;
import java.util.List;

public abstract interface ICrudDAO<T, ID extends Serializable> {

    long count();

    T create();

    void delete(T entity);

    int deleteAll();

    List<T> findAll();

    T findById(ID id);

    void save(T entity);

    T update(T entity);
}
