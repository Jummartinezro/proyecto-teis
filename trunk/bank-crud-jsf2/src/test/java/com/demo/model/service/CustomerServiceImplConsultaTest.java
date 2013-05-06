package com.demo.model.service;

import com.demo.model.dao.CustomerDAO;
import com.demo.pojo.Card;
import com.demo.pojo.Customer;
import com.demo.pojo.Gender;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Alexander
 */
public class CustomerServiceImplConsultaTest extends TestCase {

    CustomerServiceImpl customerServiceImpl;
    List listaClientes;

    public CustomerServiceImplConsultaTest(String testName) {
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

        customerServiceImpl.deleteAll();

        Customer customer = new Customer();
        customer.setName("TestCustomer");
        Calendar fecha1 = Calendar.getInstance();
        fecha1.setTimeInMillis(0);
        fecha1.set(Calendar.YEAR, 1990);
        fecha1.set(Calendar.MONTH, Calendar.JANUARY);
        fecha1.set(Calendar.DATE, 4);
        customer.setBirthday(fecha1.getTime());
        customer.setGender(Gender.MALE);
        customer.setCard(Card.MASTERCARD);
        customer.seteMail("abc@def.com");
        customer.setLicense(true);
        customer.setAbout("xxx");
        customer.setNumberOfCards(1);
        customer.setMailingList(true);

        Customer customer2 = new Customer();
        customer2.setName("TestCustomer");
        Calendar fecha2 = Calendar.getInstance();
        fecha2.setTimeInMillis(0);
        fecha2.set(Calendar.YEAR, 1991);
        fecha2.set(Calendar.MONTH, Calendar.MAY);
        fecha2.set(Calendar.DATE, 5);
        customer2.setBirthday(fecha2.getTime());
        customer2.setGender(Gender.MALE);
        customer2.setCard(Card.MASTERCARD);
        customer2.seteMail("ghi@jkl.es");
        customer2.setLicense(true);
        customer2.setAbout("xxx");
        customer2.setNumberOfCards(1);
        customer2.setMailingList(true);

        customerServiceImpl.save(customer);
        customerServiceImpl.save(customer2);

        listaClientes = new ArrayList();
        listaClientes.add(customer);
        listaClientes.add(customer2);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of count method, of class CustomerServiceImpl.
     */
    public void testCount() {
        long expResult = listaClientes.size();
        long result = customerServiceImpl.count();
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class CustomerServiceImpl.
     */
    public void testFindAll() {
        List expResult = listaClientes;
        List result = customerServiceImpl.findAll();
        assertEquals(expResult, result);
    }

    /**
     * Test of findById method, of class CustomerServiceImpl.
     */
    public void testFindById() {
        
        try {
            customerServiceImpl.findById(null);
            fail("A Exception was expected");
        } catch (Exception ex) {
            assertEquals("id to load is required for loading", ex.getMessage());
        }
           
        Customer expResult = null;
        Customer result = customerServiceImpl.findById(-10);
        assertEquals(expResult, result);
        
        expResult = (Customer)listaClientes.get(0);
        result = customerServiceImpl.findById(1);
        assertEquals(expResult, result);
    }
}
