/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.demo.pojo.Card;
import com.demo.pojo.Customer;
import com.demo.pojo.Gender;

@ManagedBean(name = "dataFactory")
@ApplicationScoped
public class DataFactory implements IDataFactory {

    public static final int DEFAULT_DATA_SIZE = 10;

    public static Boolean getRandomBoolean() {
        if (getRandomNumberOfCards() > 75) {
            return true;
        }

        return false;
    }

    public static Card getRandomCard() {
        int size = Card.values().length;
        return Card.fromId((int) Math.floor(Math.random() * size));
    }

    public static Date getRandomDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.set((int) Math.floor(Math.random() * 50 + 1950),
                (int) Math.floor(Math.random() * 12),
                (int) Math.floor(Math.random() * 29));

        return calendar.getTime();
    }

    public static Gender getRandomGender() {
        int size = Gender.values().length;
        return Gender.fromId((int) Math.floor(Math.random() * size));
    }

    public static Integer getRandomNumberOfCards() {
        return (int) Math.floor(Math.random() * 151);
    }
    private EntityManager entityManager;

    public DataFactory() {
        super();
    }

    private void createCustomers() {
        List<Customer> list = getRandomCustomerList(DEFAULT_DATA_SIZE);

        int index = 0;
        this.entityManager.getTransaction().begin();
        for (Customer customer : list) {
            if (index % 20 == 0) {
                this.entityManager.clear();
                this.entityManager.flush();
            }
            this.entityManager.persist(customer);
            index++;
        }
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void createData() {
        createCustomers();
    }

    private List<Customer> getRandomCustomerList(int size) {
        if (size < 0) {
            return null;
        }

        List<Customer> list = new ArrayList<Customer>();

        for (int i = 0; i < size; i++) {
            Customer customer = new Customer("name " + i, getRandomDate(),
                    getRandomGender(), "test info " + i, getRandomCard(),
                    getRandomNumberOfCards(), getRandomBoolean(), Boolean.TRUE, "random@mail.com");

            list.add(customer);
        }

        return list;
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
}
