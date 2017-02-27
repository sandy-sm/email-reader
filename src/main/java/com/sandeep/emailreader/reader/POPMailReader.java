package com.sandeep.emailreader.reader;

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
 * Created by sandeep on 21/2/17.
 */
public class POPMailReader implements MailReader, Runnable {

  Logger logger = LoggerFactory.getLogger(POPMailReader.class);

  private EmailXmlPojo.Email email;

  public POPMailReader(EmailXmlPojo.Email email) {
    this.email = email;
  }

  public void checkMailsAndPost(){
  }

  public void run() {

  }

}
