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
    
    public void testValidacionDeCampos() {
        
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        
        Customer customer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        customerServiceImpl.save(customer);
        
        //NAME
        
        Customer customerShortName = new Customer("Juan",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
 
        customerShortName.setId(customer.getId());
        try {
            customerServiceImpl.update(customerShortName);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a length name less than 5!", e.getMessage());
        }
        
        Customer customerGoodName = new Customer("Carlos Andres Gil",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);

        customerGoodName.setId(customer.getId());
        try {
            customerServiceImpl.update(customerGoodName);
            assertEquals(customerServiceImpl.findById(customer.getId()).getName(), customerGoodName.getName());
        } catch (Exception e) {
            System.out.println("¡Unexpected error!");
        }
        
        Customer customerLongName = new Customer("Adriana Carolina Otálora Quecan",
                new Date(fechaA.getTimeInMillis()), Gender.FEMALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        customerLongName.setId(customer.getId());
        try {
            customerServiceImpl.update(customerLongName);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a length name higher than 30!", e.getMessage());
        }
        
        //BIRTHDAY
        
        Calendar fecha1 = Calendar.getInstance();
        Calendar fecha2 = Calendar.getInstance();
        Calendar fecha3 = Calendar.getInstance();
        fecha1.set(Calendar.YEAR, 1980);
        fecha1.set(Calendar.MONTH, Calendar.JULY);
        fecha1.set(Calendar.DATE, 19);
        fecha2.set(Calendar.YEAR, 2000);
        fecha2.set(Calendar.MONTH, Calendar.SEPTEMBER);
        fecha2.set(Calendar.DATE, 30);
        fecha3.set(Calendar.YEAR, 1991);
        fecha3.set(Calendar.MONTH, Calendar.FEBRUARY);
        fecha3.set(Calendar.DATE, 31);
        
        Customer adultCustomer = new Customer("Customer de Validación",
                new Date(fecha1.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        adultCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(adultCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getBirthday(), adultCustomer.getBirthday());
        } catch (Exception e) {
            System.out.println("¡Unexpected error!");
        }
        
        Customer underageCustomer = new Customer("Customer de Validación",
                new Date(fecha2.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        underageCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(underageCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a underage customer!", e.getMessage());         
        }
        
        Customer nullBirthdayCustomer = new Customer("Customer de Validación",
                null, Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        nullBirthdayCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(underageCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a null birthday!", e.getMessage());
        }
        
        //GENRE
        
        Customer nullGenreCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), null, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        nullGenreCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullGenreCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a null genre!", e.getMessage());           
        }
        
        //ABOUT
        
        Customer goodAboutCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Descripción de este cliente",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        goodAboutCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(goodAboutCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getAbout(), goodAboutCustomer.getAbout());
        } catch (Exception e) {
            System.out.println("¡Unexpected error!");
        }
        
        Customer longAboutCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Lorem ipsum ad his "
                + "scripta blandit partiendo, eum fastidii accumsan euripidis "
                + "in, eum liber hendrerit an. Qui ut wisi vocibus suscipiantur, "
                + "quo dicit ridens inciderint id. Quo mundi lobortis reformidans "
                + "eu, legimus senserit definiebas an eos. Eu sit tincidunt "
                + "incorrupte definitionem, vis mutat affert percipit cu, eirmod "
                + "consectetuer signiferumque eu per. In usu latine equidem dolores. "
                + "Quo no falli viris intellegam, ut fugit veritus placerat per. Ius "
                + "id vidit volumus mandamus, vide veritus democritum te nec, ei eos "
                + "debet libris consulatu. No mei ferri graeco dicunt, ad cum veri "
                + "accommodare. Sed at malis omnesque delicata, usu et iusto zzril "
                + "meliore. Dicunt maiorum eloquentiam cum cu, sit summo dolor "
                + "essent te.", Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        longAboutCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(longAboutCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a length About higher than 250!", e.getMessage());
        }
        
        Customer nullAboutCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, null,
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        nullAboutCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullAboutCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a null About!", e.getMessage());
        }
        
        Customer emptyAboutCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        emptyAboutCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullAboutCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getAbout(), emptyAboutCustomer.getAbout());
        } catch (Exception e) {
            System.out.println("¡Unexpected error!");
        }
        
        //CARD
        
        Customer cardCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.AMEX, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        cardCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(cardCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getCard(), cardCustomer.getCard());
        } catch (Exception e) {
            System.out.println("¡Unexpected error!");
        }
        
        Customer nullCardCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                null, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        nullCardCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullCardCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a null Card!", e.getMessage());
        }
        
        //MAILING LIST
        
        Customer trueMailingListCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                null, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE);
        
        trueMailingListCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(trueMailingListCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getMailingList(),
                    trueMailingListCustomer.getMailingList());
        } catch (Exception e) {
            System.out.println("¡Unexpected error!");
        }
        
        Customer falseMailingListCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                null, Integer.valueOf(1), Boolean.FALSE, Boolean.TRUE);
        
        falseMailingListCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(falseMailingListCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getMailingList(),
                    falseMailingListCustomer.getMailingList());
        } catch (Exception e) {
            System.out.println("¡Unexpected error!");
        }
        
        Customer nullMailingListCustomer = new Customer("Customer de Validación",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                null, Integer.valueOf(1), null, Boolean.TRUE);
        
        nullMailingListCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullMailingListCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("¡Trying to put a null Mailing List!", e.getMessage());
        }
        
    }
}
