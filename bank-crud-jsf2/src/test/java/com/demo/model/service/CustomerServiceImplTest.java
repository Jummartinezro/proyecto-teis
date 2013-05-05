/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demo.model.service;

import com.demo.model.dao.CustomerDAO;
import com.demo.pojo.Card;
import com.demo.pojo.Customer;
import com.demo.pojo.Gender;
import com.demo.util.GenreNotEditableException;
import java.util.Calendar;
import java.util.Date;
import junit.framework.TestCase;

/**
 *
 * @author juanmanuelmartinezromero
 */
public class CustomerServiceImplTest extends TestCase {

    CustomerServiceImpl customerServiceImpl;
    Customer customer;
    Calendar fecha1;

    public CustomerServiceImplTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        CustomerDAO dao = new CustomerDAO();
        dao.postConstruct();
        customerServiceImpl = new CustomerServiceImpl();
        customerServiceImpl.postConstruct();
        customerServiceImpl.setDao(dao);

        fecha1 = Calendar.getInstance();
        fecha1.setTimeInMillis(0);
        fecha1.set(Calendar.YEAR, 1990);
        fecha1.set(Calendar.MONTH, Calendar.JANUARY);
        fecha1.set(Calendar.DATE, 4);

        customer = new Customer("Customer 1",
                new Date(fecha1.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        customerServiceImpl.save(customer);
    }

//    @Override
//    protected void tearDown() throws Exception {
//        super.tearDown();
//    }
//
//    /**
//     * Test of count method, of class CustomerServiceImpl.
//     */
//    public void testCount() {
//        System.out.println("count");
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        long expResult = 0L;
//        long result = instance.count();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of create method, of class CustomerServiceImpl.
//     */
//    public void testCreate() {
//        System.out.println("create");
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        Customer expResult = null;
//        Customer result = instance.create();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class CustomerServiceImpl.
//     */
//    public void testDelete() {
//        System.out.println("delete");
//        Customer entity = null;
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        instance.delete(entity);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteAll method, of class CustomerServiceImpl.
//     */
//    public void testDeleteAll() {
//        System.out.println("deleteAll");
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        int expResult = 0;
//        int result = instance.deleteAll();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAll method, of class CustomerServiceImpl.
//     */
//    public void testFindAll() {
//        System.out.println("findAll");
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        List expResult = null;
//        List result = instance.findAll();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findById method, of class CustomerServiceImpl.
//     */
//    public void testFindById() {
//        System.out.println("findById");
//        Integer id = null;
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        Customer expResult = null;
//        Customer result = instance.findById(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of postConstruct method, of class CustomerServiceImpl.
//     */
//    public void testPostConstruct() {
//        System.out.println("postConstruct");
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        instance.postConstruct();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of preDestroy method, of class CustomerServiceImpl.
//     */
//    public void testPreDestroy() {
//        System.out.println("preDestroy");
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        instance.preDestroy();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of save method, of class CustomerServiceImpl.
//     */
//    public void testSave() {
//        System.out.println("save");
//        Customer entity = null;
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        instance.save(entity);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setDao method, of class CustomerServiceImpl.
//     */
//    public void testSetDao() {
//        System.out.println("setDao");
//        ICrudDAO<Customer, Integer> dao = null;
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        instance.setDao(dao);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of update method, of class CustomerServiceImpl.
//     */
//    public void testUpdate() {
//        System.out.println("update");
//        Customer entity = null;
//        CustomerServiceImpl instance = new CustomerServiceImpl();
//        Customer expResult = null;
//        Customer result = instance.update(entity);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test the action ocurred when the genre of a customer is gone to be
     * changed
     */
    public void testSexoNoModificable() {
        Customer customer2 = new Customer("Customer 2",
                new Date(fecha1.getTimeInMillis()), Gender.FEMALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);

        customer2.setId(customer.getId());
        try {
            customerServiceImpl.update(customer2);
            fail("A Exception was expected");
        } catch (GenreNotEditableException ex) {
            assertEquals("Trying to change the gender of a customer", ex.getMessage());
        }
    }

    public void testModificacionCorrecta() {

        fecha1.set(Calendar.YEAR, 1991);
        fecha1.set(Calendar.MONTH, Calendar.FEBRUARY);
        fecha1.set(Calendar.DATE, 5);

        Customer customer2 = new Customer("Customer 2",
                new Date(fecha1.getTimeInMillis()), Gender.MALE, "New Description",
                Card.MASTERCARD, Integer.valueOf(2), Boolean.FALSE, Boolean.TRUE);

        customer2.setId(customer.getId());

        try {
            customerServiceImpl.update(customer2);
            customer = customerServiceImpl.findById(customer2.getId());
            assertEquals(customer2.getAbout(), customer.getAbout());
            assertEquals(customer2.getBirthday(), customer.getBirthday());
            assertEquals(customer2.getCard(), customer.getCard());
            assertEquals(customer2.getGender(), customer.getGender());
            assertEquals(customer2.getId(), customer.getId());
            assertEquals(customer2.getLicense(), customer.getLicense());
            assertEquals(customer2.getMailingList(), customer.getMailingList());
            assertEquals(customer2.getName(), customer.getName());
            assertEquals(customer2.getNumberOfCards(), customer.getNumberOfCards());
        } catch (GenreNotEditableException ex) {
            fail("Is supposed that the Genre has not changed");
        }
    }
}
