import DbHelper.GetElementValueHelper;
import DbHelper.SqlQueryClickHouse;
import MQHelper.ActiveMqConsumer;
import MQHelper.ActiveMqProducer;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import user.NewCard;
import user.UserRegister;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;

public class UserTest {

    private Faker faker = new Faker();


    @BeforeClass
    static void setUp() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testCanRegisterNewUser() {

        UserRegister user = new UserRegister()
                .username(faker.name().username())
                .email("test@mail.com")
                .password("test123");

        RestAssured
                .given().contentType(ContentType.JSON).log().all()
                .body(user)
                .when()
                .post("register")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }


    @Test
    public void newCard() {
        NewCard newCard = new NewCard()
                .ccv("dfgdfg")
                .expires("fdgdfg")
                .longNum("sdfsdf")
                .userID("sdfsdfd");

        RestAssured.registerParser("text/plain", Parser.JSON);

        RestAssured
                .given().contentType(ContentType.JSON).log().all()
                .body(newCard)
                .when()
                .post("cards")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("error", equalTo("Do: Invalid Id Hex"));
    }

    @Test
    public void getCard() {

        RestAssured.registerParser("text/plain", Parser.JSON);

        RestAssured
                .get("cards")
                .then()
                .assertThat()
                .body("_embedded.card.ccv", hasItem("958"));

    }

    @Test
    public void ConsumerActiveMQ() {
        ActiveMqConsumer ActiveMqConsumer = new ActiveMqConsumer();
        ActiveMqConsumer.Consumer();

    }


    @Test
    public void sendMessageActiveMQ() {
        ActiveMqProducer activeMqProducer = new ActiveMqProducer("tcp://localhost", "marktest1");
        activeMqProducer.sendMessageActiveMQ();
    }


    @Test
    public void dbSelect() throws SQLException {
        SqlQueryClickHouse sch = new SqlQueryClickHouse();
        String selectValue = sch.getValue("SELECT * from Marking.RusInvoices where number = 'shi1'", "number");
        Assert.assertEquals(selectValue, "shi1");
    }

    @Test
    public void sendUpdMessageActiveMQ() throws InterruptedException {
        ActiveMqProducer activeMqProducer = new ActiveMqProducer("tcp://localhost", "marktest1");
        activeMqProducer.sendUpdMessageActiveMQ();
        Thread.sleep(1000);
        SqlQueryClickHouse sch = new SqlQueryClickHouse();
        String selectValue = sch.getValue("SELECT * from Marking.UTDUploadedDatas\n" +
                "where invoice_number ='K1-1.81'\n" +
                "order by `timestamp` desc", "invoice_number");
        Assert.assertEquals(selectValue, "K1-1.81");
    }

    @Test(description = "Проверка в БД значения из  файла XML")
    void sendUpdMessageActiveMQValidXML() throws Exception {
        ActiveMqProducer activeMqProducer = new ActiveMqProducer("tcp://localhost", "marktest1");
        activeMqProducer.sendUpdMessageActiveMQ();
        String checkNumber = GetElementValueHelper.getAtributeValue(0,"./upd/upd.xml", "ТекстИнф","Значен");
        Thread.sleep(1000);
        SqlQueryClickHouse sch = new SqlQueryClickHouse();
        String selectValue = sch.getValue("SELECT * from Marking.UTDUploadedDatas\n" +
                "where invoice_number ='K1-1.81'\n" +
                "order by `timestamp` desc", "invoice_number");
        Assert.assertEquals(selectValue, checkNumber);

    }


}
