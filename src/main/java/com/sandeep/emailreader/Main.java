package com.sandeep.emailreader;

import com.sandeep.emailreader.reader.MailReader;
import com.sandeep.emailreader.xmlconfig.EmailXmlPojo;
import com.sandeep.emailreader.xmlconfig.MailReaderFactory;

import java.net.URL;
import java.util.List;

/**
 * Created by sandeep on 21/2/17.
 */
public class Main {

  public static void main(String[] args) {
    //Read XML Config
    List<EmailXmlPojo.Email> emails = EmailXmlPojo.readConfig();

    //Start fetching every email account in seperate thread
    for (EmailXmlPojo.Email email: emails) {
      MailReader mailReader = MailReaderFactory.getMailReader(email);
      Thread mailReaderThread = new Thread(mailReader);
      mailReaderThread.start();
    }
  }
}
