/**
 * Copyright @ Sandeep Dange
 */
package com.sandeep.emailreader.kafka;

import com.sandeep.emailreader.reader.message.EmailMessage;
import com.sandeep.emailreader.xmlconfig.EmailXmlPojo;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A Simple Kafka producer which enqueue(s) Messages to a topic
 *
 * Created by sandeep on 21/2/17.
 */
public class SimpleKafkaProducer {

  Logger logger = LoggerFactory.getLogger(SimpleKafkaProducer.class);

  public SimpleKafkaProducer() {}

  Properties kafkProperties = new Properties();

  {
    InputStream inputStream  = null;
    Properties applicationProperties = new Properties();
    try {
      inputStream = new FileInputStream("src/main/resources/application.properties");
      applicationProperties.load(inputStream);
    } catch (IOException ioException) {
      logger.error("Error loading config properties due to ", ioException);
    }

    initializeKafkaProperties(applicationProperties);
  }

  /**
   * Initializes Kafka Properties
   *
   * @param applicationProperties
   */
  private void initializeKafkaProperties(Properties applicationProperties) {
    kafkProperties.put("emailKafkaTopic", applicationProperties.getProperty("kafka_email_publish_topic"));
    kafkProperties.put("bootstrap.servers", applicationProperties.getProperty("kafka_brokers"));
    kafkProperties.put("acks", "all");
    kafkProperties.put("retries", 0);
    kafkProperties.put("batch.size", 16384);
    kafkProperties.put("linger.ms", 1);
    kafkProperties.put("buffer.memory", 33554432);
    kafkProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    kafkProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
  }

  /**
   * Produce(s) Message to Kafka Queue
   *
   * @param email
   * @param message
   */
  public void produceMessage(EmailXmlPojo.Email email, Message message) {
    logger.info("Producing Kafka Email Message {}, of User:{}", message, email.getHost());
    logger.info("With Properties: {}", kafkProperties);
    Producer<String, String> producer = new KafkaProducer<String, String>(kafkProperties);

    EmailMessage emailMessage = getEmailMessage(message);

    ProducerRecord producerRecord = new ProducerRecord(kafkProperties.getProperty("emailKafkaTopic"), email.getId(), emailMessage.toString());
    producer.send(producerRecord);
  }

  /**
   * Get(s) {@link EmailMessage} from {@link Message}
   *
   * @param message
   * @return
   */
  private EmailMessage getEmailMessage(Message message) {
    EmailMessage emailMessage = new EmailMessage();
    try {
      emailMessage.initialize(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return emailMessage;
  }

}
