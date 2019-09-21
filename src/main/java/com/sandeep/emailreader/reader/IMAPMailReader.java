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
      Store store = getStore(session);

      logger.info("Fetching Email INBOX Folder : {}", email);
      Folder emailFolder = getFolder(store);

      Message[] messages = emailFolder.getMessages();
      logger.info("Got Messages of Size: {}, for User: {}", messages.length, email.getId());

      logger.info("Now posting messages");
      postMessages(messages);
      logger.info("Done posting messages");

      emailFolder.close(false);
      logger.info("Closed Email Folder");

      store.close();
      logger.info("Closed Store");
    } catch (NoSuchProviderException e) {
      logger.error("No Such Provider Error, Details: ", e);
    } catch (MessagingException e) {
      logger.error("Messaging Error, Details: ", e);
    } catch (Exception e) {
      logger.error("Error Occured: ", e);
    }
  }

  private void postMessages(Message[] messages) throws MessagingException {
    for (int index = 0, n = messages.length; index < n; index++) {
      Message message = messages[index];
      logger.debug("---------------------------------");
      logger.debug("Email Number {}", (index + 1));
      logger.debug("Subject: {}", message.getSubject());
      logger.debug("From: {}", message.getFrom()[0]);

      simpleKafkaProducer.produceMessage(email, message);
    }
  }

  private Folder getFolder(Store store) throws MessagingException {
    Folder emailFolder = store.getFolder("INBOX");
    emailFolder.open(Folder.READ_ONLY);
    return emailFolder;
  }

  private Store getStore(Session session) throws MessagingException {
    Store store = session.getStore("imaps");
    store.connect(email.getHost(), email.getId(), email.getPass());
    return store;
  }

  public void run() {
    checkMailsAndPost();
  }
}
