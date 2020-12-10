package MQHelper;

import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.util.ResourceUtils;

import javax.jms.*;
import java.io.File;
import java.nio.file.Files;


public class ActiveMqProducer {

    public String bootStrapServer;
    public String queueName; //autotest1, marktest1


    public ActiveMqProducer(String bootStrapServer, String queueName) {
        this.bootStrapServer = bootStrapServer;
        this.queueName = queueName;
    }

    @SneakyThrows
    public void sendMessageActiveMQ() {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(bootStrapServer);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        File msg = ResourceUtils.getFile("classpath:invoice.xml");
        String msgBody = new String(Files.readAllBytes(msg.toPath()));
        File header = ResourceUtils.getFile("classpath:headerInvoice.xml");
        String headerBody = new String(Files.readAllBytes(header.toPath()));

        TextMessage message = session.createTextMessage(msgBody);
        message.setStringProperty("gal_envelope", headerBody);
        producer.send(message);
        session.close();
        connection.close();
    }

    @SneakyThrows
    public void sendUpdMessageActiveMQ() {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(bootStrapServer);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        File msg = ResourceUtils.getFile("./upd/upd.xml");
        String msgBody = new String(Files.readAllBytes(msg.toPath()));
        File header = ResourceUtils.getFile("classpath:headerUpd.xml");
        String headerBody = new String(Files.readAllBytes(header.toPath()));

        TextMessage message = session.createTextMessage(msgBody);
        message.setStringProperty("gal_envelope", headerBody);
        producer.send(message);
        session.close();
        connection.close();
    }

}






