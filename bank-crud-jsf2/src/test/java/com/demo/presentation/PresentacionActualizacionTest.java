package com.demo.presentation;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class PresentacionActualizacionTest {

    private static final int DELAY = 50;
    private static WebDriver driver;
    private static String baseUrl;
    private static StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUpClass() {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8084/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl + "/bank-crud-jsf2/pages/register.jsf");
        try {
            revertChanges();
        } catch (InterruptedException ex) {
            Logger.getLogger(PresentacionActualizacionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Before
    public void setUp() throws InterruptedException {
        botonEditar();
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    /**
     * Verifica que el sexo no se pueda modificar
     *
     * @throws Exception
     */
    @Test
    public void testSexoNoModificable() throws Exception {
//        Thread.sleep(1000);
//        // Verifica que el campo gender sea editable
//        assertTrue(driver.findElement(By.id("itemForm:gender")).isEnabled());
//        // Detiene el hilo un momento

        // Verifica que el campo gender no sea editable
        assertFalse(driver.findElement(By.id("itemForm:gender")).isEnabled());
        try {
            assertEquals("FEMALE", driver.findElement(By.id("itemForm:gender")).getAttribute("value"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        botonUpdate();
        assertEquals("Female", driver.findElement(By.xpath("//*[@id=\"listForm\"]/table[2]/tbody/tr[1]/td[4]")).getText());
    }

    /**
     * Verifica que la modificacion se ha hecho correctamente
     *
     * @throws Exception
     */
    @Test
    public void testModificacionCorrecta() throws Exception {

        driver.findElement(By.id("itemForm:name")).clear();
        driver.findElement(By.id("itemForm:name")).sendKeys("Customer 1");
        driver.findElement(By.id("itemForm:birthday")).clear();
        driver.findElement(By.id("itemForm:birthday")).sendKeys("05-02-1991");
        driver.findElement(By.id("itemForm:about")).clear();
        driver.findElement(By.id("itemForm:about")).sendKeys("New Description");
        new Select(driver.findElement(By.id("itemForm:card"))).selectByVisibleText("Mastercard");
        driver.findElement(By.id("itemForm:numberOfCards")).clear();
        driver.findElement(By.id("itemForm:numberOfCards")).sendKeys("2");
        driver.findElement(By.id("itemForm:email")).clear();
        driver.findElement(By.id("itemForm:email")).sendKeys("customer@teis.com");
        if (driver.findElement(By.id("itemForm:mailingList")).isSelected()) {
            driver.findElement(By.id("itemForm:mailingList")).click();
        }
        if (!driver.findElement(By.id("itemForm:license")).isSelected()) {
            driver.findElement(By.id("itemForm:license")).click();
        }
        botonUpdate();

        try {
            assertEquals("Customer 1", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[2]")).getText());
            assertEquals("Tuesday, February 5, 1991", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[3]")).getText());
            assertEquals("Female", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[4]")).getText());
            assertEquals("New Description", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[5]")).getText());
            assertEquals("Mastercard", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[6]")).getText());
            assertEquals("2", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[7]")).getText());
            assertEquals("customer@teis.com", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[8]")).getText());
            assertEquals("No", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[9]")).getText());
            assertEquals("Agree", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[10]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        revertChanges();
    }

    /**
     * Verifica que no se pueda editar si la longitud del nombre es menor a 4
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testLongitudMenorNombre() throws InterruptedException {
        longitudMenorNombre("Juan");
        longitudMenorNombre(null);
    }

    /**
     * Verifica que se haga la actualizacion cuando la longitud del nombre se
     * encuentra entre los valores indicados
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testLongitudCorrectaNombre() throws InterruptedException {
        driver.findElement(By.id("itemForm:name")).clear();
        driver.findElement(By.id("itemForm:name")).sendKeys("Luis Gil");
        botonUpdate();
        assertEquals("Luis Gil", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[2]")).getText());
        revertChanges();
    }

    /**
     * Verifica que no se pueda editar si la longitud del nombre es mayor a 30
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testLongitudMayorNombre() throws InterruptedException {
        driver.findElement(By.id("itemForm:name")).clear();
        driver.findElement(By.id("itemForm:name")).sendKeys("Carlos Ernesto Martinez Rodriguez");
        botonUpdate();
        assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[1]/td[3]/span")));
        assertEquals("name 0", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[2]")).getText());
    }

    /**
     * Verifica que se haga la actualizacion cuando la fecha de nacimiento es
     * correcta
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testBithDayCorrecto() throws InterruptedException {
        driver.findElement(By.id("itemForm:birthday")).clear();
        driver.findElement(By.id("itemForm:birthday")).sendKeys("19-07-1980");
        botonUpdate();
        assertEquals("Saturday, July 19, 1980", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[3]")).getText());
        revertChanges();
    }

    /**
     * Verifica que no se haga la actualizacion si la fecha de nacimiento es la
     * de un menor de edad
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testMenorEdad() throws InterruptedException {
        driver.findElement(By.id("itemForm:birthday")).clear();
        driver.findElement(By.id("itemForm:birthday")).sendKeys("30-09-2000");
        botonUpdate();
        assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[2]/td[3]/span")));
        //TODO Descomentar si se puede solucionar el problema
        //assertEquals("Thursday, April 1, 1993", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[3]")).getText());
    }

    /**
     * Verifica que se haga la actualizacion si la descripcion tiene la longitud
     * indicada
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testLongitudCorrectaAbout() throws InterruptedException {
        driver.findElement(By.id("itemForm:about")).clear();
        driver.findElement(By.id("itemForm:about")).sendKeys("Descripcion de este cliente");
        botonUpdate();
        assertEquals("Descripcion de este cliente", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[5]")).getText());
        revertChanges();
    }

    /**
     * Verifica que no se haga la actualizacion si la descripcion tiene una
     * longitud mayor a 250 caracteres
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testLogitudMayorAbout() throws InterruptedException {
        driver.findElement(By.id("itemForm:about")).clear();
        driver.findElement(By.id("itemForm:about")).sendKeys("Lorem ipsum ad his "
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
                + "essent te.");
        botonUpdate();
        assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[4]/td[3]/span")));
        assertEquals("test info 0", driver.findElement(By.xpath("//*[@id=\'listForm\']/table[2]/tbody/tr[1]/td[5]")).getText());

    }

    /**
     * Verifica que se haga la actualizacion si la descripcion es vacia
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testEmptyAbout() throws InterruptedException {
        driver.findElement(By.id("itemForm:about")).clear();
        botonUpdate();
        assertEquals("", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[5]")).getText());
        revertChanges();
    }

    /**
     * Verifica que se haga la actualizacion cuando se le asigne una tarjeta de
     * credito correcta
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testValidCard() throws InterruptedException {
        new Select(driver.findElement(By.id("itemForm:card"))).selectByVisibleText("Amex");
        botonUpdate();
        assertEquals("Amex", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[6]")).getText());
        revertChanges();
    }

    /**
     * Verifica que se pueda hacer la modificacion cuando la lista de correos se
     * pone como verdadera
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testTrueMailingList() throws InterruptedException {
        if (!driver.findElement(By.id("itemForm:mailingList")).isSelected()) {
            driver.findElement(By.id("itemForm:mailingList")).click();
        }
        botonUpdate();
        assertEquals("Yes", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[9]")).getText());
    }

    /**
     * Verifica que se pueda hacer la modificacion cuando la lista de correos se
     * pone como falsa
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testFalseMailingList() throws InterruptedException {
        if (driver.findElement(By.id("itemForm:mailingList")).isSelected()) {
            driver.findElement(By.id("itemForm:mailingList")).click();
        }
        botonUpdate();
        assertEquals("No", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[9]")).getText());
    }

    /**
     * Prueba que se muestre un mensaje cuando se le pasa un email invalido
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testInvalidEmails() throws InterruptedException {
        invalidEmailCase("abc@def");
        invalidEmailCase("abc.def");
        invalidEmailCase("abc");
    }

    /**
     * Prueba que se realice la modificacion cuando se le pasa un mail valido
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testValidEmail() throws InterruptedException {
        driver.findElement(By.id("itemForm:email")).clear();
        driver.findElement(By.id("itemForm:email")).sendKeys("abc@def.ghi");
        botonUpdate();
        assertEquals("abc@def.ghi", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[8]")).getText());
        revertChanges();
    }

    /**
     * Verifica que no se haga modificaciones cuando el campo birthday esta
     * vacio
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testEmptyBirthDay() throws InterruptedException {
        driver.findElement(By.id("itemForm:birthday")).clear();
        botonUpdate();
        assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[2]/td[3]/span")));
        assertEquals("Thursday, April 1, 1993", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[3]")).getText());
    }

    /**
     * Verifica que no se haga modificaciones cuando el campo mail esta vacio
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testEmptyEmail() throws InterruptedException {
        driver.findElement(By.id("itemForm:email")).clear();
        botonUpdate();
        assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[7]/td[3]/span")));
        assertEquals("random@mail.com", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[8]")).getText());
    }

    /**
     * Verifica que no se hagan modificaciones cuando el campo licencia esta
     * vacio
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testEmptyLicense() throws InterruptedException {
        if (driver.findElement(By.id("itemForm:license")).isSelected()) {
            driver.findElement(By.id("itemForm:license")).click();
        }
        botonUpdate();
        assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[9]/td[3]/span")));
        assertEquals("Agree", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[10]")).getText());
    }

    /**
     * Verifica que no se hagan modificaciones cuando todos los campos posibles
     * se encuentran vacios
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    @Test
    public void testEmptyAll() throws InterruptedException {
        driver.findElement(By.id("itemForm:name")).clear();
        driver.findElement(By.id("itemForm:birthday")).clear();
        driver.findElement(By.id("itemForm:about")).clear();
        driver.findElement(By.id("itemForm:numberOfCards")).clear();
        driver.findElement(By.id("itemForm:email")).clear();
        if (driver.findElement(By.id("itemForm:mailingList")).isSelected()) {
            driver.findElement(By.id("itemForm:mailingList")).click();
        }
        if (driver.findElement(By.id("itemForm:license")).isSelected()) {
            driver.findElement(By.id("itemForm:license")).click();
        }
        botonUpdate();

        try {
            assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[1]/td[3]/span")));
            assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[2]/td[3]/span")));
            assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[7]/td[3]/span")));
            assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[9]/td[3]/span")));

            assertEquals("name 0", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[2]")).getText());
            assertEquals("Thursday, April 1, 1993", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[3]")).getText());
            assertEquals("Female", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[4]")).getText());
            assertEquals("test info 0", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[5]")).getText());
            assertEquals("Enroute", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[6]")).getText());
            assertEquals("134", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[7]")).getText());
            assertEquals("random@mail.com", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[8]")).getText());
            assertEquals("No", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[9]")).getText());
            assertEquals("Agree", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[10]")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    /**
     * Revierte los cambios hechos al primer registro encontrado en la tabla
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    private static void revertChanges() throws InterruptedException {
        botonEditar();
        driver.findElement(By.id("itemForm:name")).clear();
        driver.findElement(By.id("itemForm:name")).sendKeys("name 0");
        driver.findElement(By.id("itemForm:birthday")).clear();
        driver.findElement(By.id("itemForm:birthday")).sendKeys("01-04-1993");


        driver.findElement(By.id("itemForm:about")).clear();
        driver.findElement(By.id("itemForm:about")).sendKeys("test info 0");
        new Select(driver.findElement(By.id("itemForm:card"))).selectByVisibleText("Enroute");
        driver.findElement(By.id("itemForm:numberOfCards")).clear();
        driver.findElement(By.id("itemForm:numberOfCards")).sendKeys("134");
        driver.findElement(By.id("itemForm:email")).clear();
        driver.findElement(By.id("itemForm:email")).sendKeys("random@mail.com");
        if (driver.findElement(By.id("itemForm:mailingList")).isSelected()) {
            driver.findElement(By.id("itemForm:mailingList")).click();
        }
        if (!driver.findElement(By.id("itemForm:license")).isSelected()) {
            driver.findElement(By.id("itemForm:license")).click();
        }
        botonUpdate();
    }

    /**
     * Ejecuta la accion del boton editar con un retraso de tiempo
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    private static void botonEditar() throws InterruptedException {
        driver.findElement(By.id("listForm:j_idt49:0:j_idt83")).click();
        Thread.sleep(DELAY);
    }

    /**
     * Ejecuta la accion del boton update con un retraso de tiempo
     *
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    private static void botonUpdate() throws InterruptedException {
        driver.findElement(By.id("itemForm:j_idt41")).click();
        Thread.sleep(DELAY);
    }

    /**
     * Valida que se muestre un mensaje de advertencia cuando se trata de
     * modificar el campo correo
     *
     * @param mail correo que se dispone a ser validado
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    private void invalidEmailCase(final String mail) throws InterruptedException {
        setUp();
        driver.findElement(By.id("itemForm:email")).clear();
        driver.findElement(By.id("itemForm:email")).sendKeys(mail);
        botonUpdate();
        assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[7]/td[3]/span")));
        //TODO Validar que no haga cambios en la bd
        //assertEquals("random@mail.com", driver.findElement(By.xpath("//*[@id=\'listForm\']/table[2]/tbody/tr[1]/td[8]")).getText());
    }

    /**
     * Valida que no se haga ninguna modificacion cuando se trata de
     * modificar el campo nombre
     *
     * @param nombre nombre que se dispone a ser validado
     * @throws InterruptedException cuando el hilo no puede ser pausado
     */
    private void longitudMenorNombre(final String nombre) throws InterruptedException {
        driver.findElement(By.id("itemForm:name")).clear();
        driver.findElement(By.id("itemForm:name")).sendKeys(nombre);
        botonUpdate();
        assertNotNull(driver.findElement(By.xpath("//*[@id='itemForm']/table/tbody/tr[1]/td[3]/span")));
        assertEquals("name 0", driver.findElement(By.xpath("//*[@id='listForm']/table[2]/tbody/tr[1]/td[2]")).getText());
    }
}