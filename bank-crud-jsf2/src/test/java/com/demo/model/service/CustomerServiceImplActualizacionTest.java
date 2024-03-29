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
import com.demo.util.RequiredAttributeException;
import java.util.Calendar;
import java.util.Date;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import junit.framework.TestCase;

/**
 *
 * @author juanmanuelmartinezromero
 */
public class CustomerServiceImplActualizacionTest extends TestCase {

    CustomerServiceImpl customerServiceImpl;
    Customer customer;
    Calendar fecha1;

    public CustomerServiceImplActualizacionTest(String testName) {
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
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");
        customerServiceImpl.save(customer);
    }

    /**
     * Test the action ocurred when the genre of a customer is gone to be
     * changed
     */
    public void testSexoNoModificable() {
        Customer customer2 = new Customer("Customer 2",
                new Date(fecha1.getTimeInMillis()), Gender.FEMALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        customer2.setId(customer.getId());
        try {
            customerServiceImpl.update(customer2);
            fail("A Exception was expected");
        } catch (Exception ex) {
            assertEquals("Trying to change the gender of a customer", ex.getMessage());
        }
    }

    public void testModificacionCorrecta() {

        fecha1.set(Calendar.YEAR, 1991);
        fecha1.set(Calendar.MONTH, Calendar.FEBRUARY);
        fecha1.set(Calendar.DATE, 5);

        Customer customer2 = new Customer("Customer 2",
                new Date(fecha1.getTimeInMillis()), Gender.MALE, "New Description",
                Card.MASTERCARD, Integer.valueOf(2), Boolean.FALSE, Boolean.TRUE, "customer@teis.com");

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
        } catch (Exception ex) {
            fail("Is supposed that the Genre has not changed");
        }
    }

    public void testLogitudMenorNombre() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);

        Customer customerShortName = new Customer("Juan",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        customerShortName.setId(customer.getId());
        try {
            customerServiceImpl.update(customerShortName);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a length Name less than 5!", e.getMessage());
        }
    }

    public void testLongitudCorrectaNombre() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer customerGoodName = new Customer("Luis Gil",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        customerGoodName.setId(customer.getId());
        try {
            customerServiceImpl.update(customerGoodName);
            assertEquals(customerServiceImpl.findById(customer.getId()).getName(), customerGoodName.getName());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void testLongitudMayorNombre() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer customerLongName = new Customer("Carlos Ernesto MartÌnez RodrÌguez",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        customerLongName.setId(customer.getId());
        try {
            customerServiceImpl.update(customerLongName);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a length Name higher than 30!", e.getMessage());
        }
    }

    public void testBithDayCorrecto() {
        fecha1.set(Calendar.YEAR, 1980);
        fecha1.set(Calendar.MONTH, Calendar.JULY);
        fecha1.set(Calendar.DATE, 19);
        Customer adultCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fecha1.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        adultCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(adultCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getBirthday(), adultCustomer.getBirthday());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void testMenorEdad() {
        Calendar fecha2 = Calendar.getInstance();
        fecha2.set(Calendar.YEAR, 2000);
        fecha2.set(Calendar.MONTH, Calendar.SEPTEMBER);
        fecha2.set(Calendar.DATE, 30);
        Customer underageCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fecha2.getTimeInMillis()), Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        underageCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(underageCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a underage customer!", e.getMessage());
        }
    }

    public void testLongitudCorrectaAbout() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);

        Customer goodAboutCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "DescripciÛn de este cliente",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        goodAboutCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(goodAboutCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getAbout(), goodAboutCustomer.getAbout());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void testLogitudMayorAbout() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer longAboutCustomer = new Customer("Customer de ValidaciÛn",
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
                + "essent te.", Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        longAboutCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(longAboutCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a length About higher than 250!", e.getMessage());
        }
    }

    public void testEmptyAbout() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer emptyAboutCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        emptyAboutCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(emptyAboutCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getAbout(), emptyAboutCustomer.getAbout());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void testValidCard() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);

        Customer cardCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.AMEX, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        cardCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(cardCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getCard(), cardCustomer.getCard());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void testTrueMailingList() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);

        Customer trueMailingListCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        trueMailingListCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(trueMailingListCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getMailingList(),
                    trueMailingListCustomer.getMailingList());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void testFalseMailingList() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer falseMailingListCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.VISA, Integer.valueOf(1), Boolean.FALSE, Boolean.TRUE, "customer@teis.com");

        falseMailingListCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(falseMailingListCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).getMailingList(),
                    falseMailingListCustomer.getMailingList());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void testInvalidEmailCase1() {
        //Case xxx@xxx
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);

        Customer invalidEmail1Customer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "abc@def");

        invalidEmail1Customer.setId(customer.getId());
        try {
            customerServiceImpl.update(invalidEmail1Customer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a invalid eMail!", e.getMessage());
        }
    }

    public void testInvalidEmailCase2() {
        //Case xxx.xxx
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer invalidEmail2Customer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "abc.efg");

        invalidEmail2Customer.setId(customer.getId());
        try {
            customerServiceImpl.update(invalidEmail2Customer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a invalid eMail!", e.getMessage());
        }
    }

    public void testInvalidEmailCase3() {
        //Case xxx
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer invalidEmail3Customer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "abc");

        invalidEmail3Customer.setId(customer.getId());
        try {
            customerServiceImpl.update(invalidEmail3Customer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a invalid eMail!", e.getMessage());
        }
    }

    public void testValidEmail() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer validEmailCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "abc@def.ghi");

        validEmailCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(validEmailCustomer);
            assertEquals(customerServiceImpl.findById(customer.getId()).geteMail(), validEmailCustomer.geteMail());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    //Tests Campos Obligatorios
    public void testNullName() {

        Calendar fecha1 = Calendar.getInstance();
        fecha1.setTimeInMillis(0);
        fecha1.set(Calendar.YEAR, 1990);
        fecha1.set(Calendar.MONTH, Calendar.JANUARY);
        fecha1.set(Calendar.DATE, 4);
        customer.setBirthday(fecha1.getTime());
        customer.setGender(Gender.MALE);
        customer.setAbout("descripcion");
        customer.setCard(Card.MASTERCARD);
        customer.seteMail("abc@def.com");
        customer.setMailingList(true);
        customer.setNumberOfCards(2);
        customer.setLicense(true);

        customer.setName(null);
        try {
            customerServiceImpl.update(customer);
            fail("An Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a null name", e.getMessage());
        }
    }

    public void testNullBirthDay() {
        Customer nullBirthdayCustomer = new Customer("Customer de ValidaciÛn",
                null, Gender.MALE, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        nullBirthdayCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullBirthdayCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a null Birthday!", e.getMessage());
        }
    }

    public void testNullGenre() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);

        Customer nullGenreCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), null, "Description",
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        nullGenreCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullGenreCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a null gender", e.getMessage());
        }
    }

    public void testNullCard() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer nullCardCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Description",
                null, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        nullCardCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullCardCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a null Card!", e.getMessage());
        }
    }

    public void testNullMailingList() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer nullMailingListCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, "Decription",
                Card.VISA, Integer.valueOf(1), null, Boolean.TRUE, "customer@teis.com");

        nullMailingListCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullMailingListCustomer);
            fail("A Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a null Mailing List!", e.getMessage());
        }
    }

    public void testNullEmail() {
        customer.seteMail(null);
        try {
            customerServiceImpl.update(customer);
            fail("An Exception was expected");
        } catch (Exception e) {
            assertEquals("Trying to put a null Mail!", e.getMessage());
        }
    }

    //Tests Campos No Obligatorios
    public void testNullAbout() {
        Calendar fechaA = Calendar.getInstance();
        fechaA.setTimeInMillis(0);
        fechaA.set(Calendar.YEAR, 1985);
        fechaA.set(Calendar.MONTH, Calendar.JUNE);
        fechaA.set(Calendar.DATE, 10);
        Customer nullAboutCustomer = new Customer("Customer de ValidaciÛn",
                new Date(fechaA.getTimeInMillis()), Gender.MALE, null,
                Card.VISA, Integer.valueOf(1), Boolean.TRUE, Boolean.TRUE, "customer@teis.com");

        nullAboutCustomer.setId(customer.getId());
        try {
            customerServiceImpl.update(nullAboutCustomer);
            System.out.println(customer.getAbout());
            assertEquals(customerServiceImpl.findById(customer.getId()).getAbout(), nullAboutCustomer.getAbout());
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void testNullLicence() {
        customer.setName("TestCustomer");
        Calendar fecha1 = Calendar.getInstance();
        fecha1.setTimeInMillis(0);
        fecha1.set(Calendar.YEAR, 1990);
        fecha1.set(Calendar.MONTH, Calendar.JANUARY);
        fecha1.set(Calendar.DATE, 4);
        customer.setBirthday(fecha1.getTime());
        customer.setGender(Gender.MALE);
        customer.setAbout("descripcion");
        customer.setCard(Card.MASTERCARD);
        customer.seteMail("abc@def.com");
        customer.setMailingList(true);
        customer.setNumberOfCards(2);

        customer.setLicense(null);
        try {
            customerServiceImpl.update(customer);
        } catch (Exception e) {
            fail("An Exception was not expected" + e.toString());
        }
        customer.setLicense(true);

    }

    public void testNullAll() {
        Customer customer = new Customer();
        try {
            customerServiceImpl.update(customer);
            fail("An Exception was expected");
        } catch (Exception e) {
            // Un objeto vacio y sin campos no puede ser serializado y genera esta excepcion
            assertEquals("id to load is required for loading", e.getMessage());
        }
    }
}