/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.dao;

import java.io.Serializable;
import java.util.List;

public abstract interface ICrudDAO<T, ID extends Serializable> {

    abstract long count();

    abstract T create();

    abstract void delete(T entity);

    abstract int deleteAll();

    abstract List<T> findAll();

    abstract T findById(ID id);

    abstract void save(T entity);

    abstract T update(T entity);
}
