package com.sandeep.emailreader.reader;

import com.sandeep.emailreader.kafka.SimpleKafkaProducer;
import com.sandeep.emailreader.xmlconfig.EmailXmlPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

/**
 * Created by sandeep on 22/2/17.
 */
public class IMAPMailReader implements MailReader {

  Logger logger = LoggerFactory.getLogger(POPMailReader.class);

  private EmailXmlPojo.Email email;

  private SimpleKafkaProducer simpleKafkaProducer;

  public IMAPMailReader(EmailXmlPojo.Email email) {
    this.email = email;
    this.simpleKafkaProducer = new SimpleKafkaProducer();
  }

  public void checkMailsAndPost() {
    Properties properties = new Properties();
    properties.put("mail.store.protocol", "imaps");

    Session session = Session.getDefaultInstance(properties);
    try {
      logger.info("Fetching Email Store.. for : {}", email);
      Store store = session.getStore("imaps");
      store.connect(email.getHost(), email.getId(), email.getPass());

      logger.info("Fetching Email INBOX Folder : {}", email);
      //create the folder object and open it
      Folder emailFolder = store.getFolder("INBOX");
      emailFolder.open(Folder.READ_ONLY);

      // retrieve the messages from the folder in an array and print it
      Message[] messages = emailFolder.getMessages();
      System.out.println("messages.length---" + messages.length);
      logger.info("Got Messages Length: {}, for User: {}", messages.length, email.getId());

      for (int i = 0, n = messages.length; i < n; i++) {
        Message message = messages[i];
        System.out.println("---------------------------------");
        System.out.println("Email Number " + (i + 1));
        System.out.println("Subject: " + message.getSubject());
        System.out.println("From: " + message.getFrom()[0]);
        simpleKafkaProducer.produceMessage(email, message);
      }

      //close the store and folder objects
      emailFolder.close(false);
      store.close();
    }catch (NoSuchProviderException e) {
      logger.error("No Such Provider Error, Details: ", e);
    } catch (MessagingException e) {
      logger.error("Messaging Error, Details: ", e);
    } catch (Exception e) {
      logger.error("Error Occured: ", e);
    }
  }

  public void run() {
    checkMailsAndPost();
  }
}
