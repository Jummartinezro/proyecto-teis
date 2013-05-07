/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.model.service;

import com.demo.model.dao.CustomerDAO;
import com.demo.model.dao.ICrudDAO;
import com.demo.pojo.Customer;
import com.demo.util.GenreNotEditableException;
import com.demo.util.InvalidAboutAttributeException;
import com.demo.util.InvalidBirthdayAttributeException;
import com.demo.util.InvalidNameAttributeException;
import com.demo.util.RequiredAttributeException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean(name = "customerServiceImpl")
@ApplicationScoped
public class CustomerServiceImpl implements ICustomerService {

    public final static int MIN_LENGTH_NAME = 5;
    public final static int MAX_LENGTH_NAME = 30;
    public final static int MAX_LENGTH_ABOUT = 250;
    public final static int ADULT_AGE = 18;
    
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
    public Customer update(Customer entity) throws Exception {
        Customer ret = this.dao.findById(entity.getId());
        if (isChangingGender(entity)) {
            throw new GenreNotEditableException("Trying to change the gender of a customer");
        } else if (entity.getName().length() <= MIN_LENGTH_NAME) {
            throw new InvalidNameAttributeException("Trying to put a length Name less than 5!");
        } else if (entity.getName().length() >= MAX_LENGTH_NAME) {
            throw new InvalidNameAttributeException("Trying to put a length Name higher than 30!");
        } else if (entity.getBirthday() == null) {
            throw new InvalidBirthdayAttributeException("Trying to put a null Birthday!");
        } else if (entity.getAbout().length() > 250) {
            throw new InvalidAboutAttributeException("Trying to put a length About higher than 250!");
        } else if (entity.getCard() == null) {
            throw new RequiredAttributeException("Trying to put a null Card!");
        } else if (entity.getMailingList() == null) {
            throw new RequiredAttributeException("Trying to put a null Mailing List!");
        } else if (entity.getBirthday() == null) {
            throw new RequiredAttributeException("Trying to put a null Birthday!");
        } else if (isNotAdult(entity)) {
            throw new InvalidBirthdayAttributeException("Trying to put a underage customer!");
        } else if (isNotValidEmail(entity)) {
            throw new InvalidBirthdayAttributeException("Trying to put a invalid eMail!");
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
    
    private boolean isNotAdult(Customer customer) {
        int factor = 0;
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        cal.setTime(customer.getBirthday());
        
        if (today.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)) {
            factor = -1;
        }
        
        int age = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR) + factor;
        
        if (age >= ADULT_AGE) {
            return false;
        }
        return true;       
    }
    
    private boolean isNotValidEmail(Customer customer) {      
        String re = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
                + "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\"
                + "\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*"
                + "[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]"
                + "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|"
                + "[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x5"
                + "3-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (!customer.geteMail().matches(re)) {
            return true;
        }
        return false;
    }
}