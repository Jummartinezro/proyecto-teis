/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import com.demo.model.service.ICustomerService;
import com.demo.model.service.CustomerServiceImpl;
import com.demo.model.service.DataFactory;
import com.demo.model.service.IDataFactory;
import com.demo.pojo.Card;
import com.demo.pojo.Customer;
import com.demo.pojo.Gender;
import com.demo.util.RequiredAttributeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean(name = "customerBean")
@SessionScoped
public class CustomerBean implements Serializable {

    private static final long serialVersionUID = 1L;
    // ---------------------------
    // Bean property start
    // ---------------------------
    // service to access business logic
    @ManagedProperty("#{customerServiceImpl}")
    private ICustomerService service;
    // factory to populate the application with data
    @ManagedProperty("#{dataFactory}")
    private IDataFactory dataFactory;
    // list of all customers in the application
    private DataModel<Customer> customers;
    // customer to edit or create
    private Customer selectedCustomer;
    // flag that indicates whether editing
    private boolean update;
    // number of exists customers
    private long count;
    // ---------------------------
    // Bean property end
    // ---------------------------

    // ---------------------------
    // Bean constructor/post constructor/pre destroy start
    // ---------------------------
    public CustomerBean() {
        super();
    }

    @PostConstruct
    public void postConstruct() {
        init();
    }

    private void init() {
        if (this.service == null) {
            this.service = new CustomerServiceImpl();
            System.err.println("*** WARNING *** Service not injected.");
        }

        if (this.dataFactory == null) {
            this.dataFactory = new DataFactory();
            System.err.println("*** WARNING *** DataFactory not injected.");
        }

        long count = this.service.count();
        if (count == 0L) {
            this.dataFactory.createData();
        }
        refresh();
    }

    @PreDestroy
    public void preDestroy() {
        destroy();
    }

    private void destroy() {
        this.service = null;
        this.dataFactory = null;
        this.customers = null;
        this.selectedCustomer = null;
        this.update = false;
        this.count = 0L;
    }
    // ---------------------------
    // Bean constructor/post constructor/pre destroy end
    // ---------------------------

    // ---------------------------
    // Bean actions start
    // ---------------------------
    public String cancel() {
        refresh();

        return "CRUD_PAGE";
    }

    public void clear(ActionEvent event) {
        int deleted = this.service.deleteAll();
        System.out.println(String.format("Deleted: %s", deleted));

        refresh();
    }

    public void delete(ActionEvent event) {
        Customer customer = this.customers.getRowData();
        this.service.delete(customer);

        refresh();
    }

    public void edit(ActionEvent event) {
        this.selectedCustomer = this.customers.getRowData();
        this.update = true;
    }

    public void refresh(ActionEvent event) {
        refresh();
    }

    public void save(ActionEvent event) {
        if (this.update) {
            try {
                this.service.update(this.selectedCustomer);
            } catch (Exception ex) {
                FacesContext fc = FacesContext.getCurrentInstance();
                String item = "";
                String message = ex.getMessage();
                if (message.equals("Trying to put a null gender") || message.equals("Trying to change the gender of a customer")) {
                    item = "itemForm:gender";
                } else if (message.equals("Trying to put a null name") || message.equals("Trying to put a length Name less than 5!")
                        || message.equals("Trying to put a length Name higher than 30!")) {
                    item = "itemForm:name";
                } else if (message.equals("Trying to put a null Birthday!") || message.equals("Trying to put a underage customer!")) {
                    item = "itemForm:birthday";
                } else if (message.equals("Trying to put a length About higher than 250!")) {
                    item = "itemForm:about";
                } else if (message.equals("Trying to put a null Card!")) {
                    item = "itemForm:card";
                } else if (message.equals("Trying to put a null Mailing List!")) {
                    item = "itemForm:mailingList";
                } else if (message.equals("Trying to put a null Mail!") || message.equals("Trying to put a invalid eMail!")) {
                    item = "itemForm:email";
                }
                fc.addMessage(item, new FacesMessage(ex.getMessage()));
                return;
            }
        } else {
            try {
                this.service.save(this.selectedCustomer);
            } catch (RequiredAttributeException ex) {
                Logger.getLogger(CustomerBean.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        }

        refresh();
    }

    private void refresh() {
        this.count = this.service.count();
        this.customers = new ListDataModel<Customer>();
        List<Customer> customerList = this.service.findAll();
        this.customers.setWrappedData(customerList);
        this.selectedCustomer = (Customer) this.service.create();
        this.update = false;
    }
    // ---------------------------
    // Bean actions end
    // ---------------------------

    // ---------------------------
    // Bean get enumeration items start
    // ---------------------------
    public Card[] getCardValues() {
        return Card.values();
    }

    public Gender[] getGenderValues() {
        return Gender.values();
    }
    // ---------------------------
    // Bean get enumeration items end
    // ---------------------------

    // ---------------------------
    // Bean getters/setters start
    // ---------------------------
    public long getCount() {
        return this.count;
    }

    public DataModel<Customer> getCustomers() {
        return this.customers;
    }

    public Customer getSelectedCustomer() {
        return this.selectedCustomer;
    }

    public boolean isUpdate() {
        return this.update;
    }

    public void setDataFactory(IDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setCustomers(DataModel<Customer> customers) {
        this.customers = customers;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void setService(ICustomerService service) {
        this.service = service;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
    // ---------------------------
    // Bean getters/setters end
    // ---------------------------
}