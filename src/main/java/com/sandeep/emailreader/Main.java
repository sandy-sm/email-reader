package com.sandeep.emailreader;

import com.sandeep.emailreader.xmlconfig.EmailXmlPojo;
import com.sandeep.emailreader.xmlconfig.MailReaderFactory;

import java.net.URL;
import java.util.List;

/**
 * Created by sandeep on 21/2/17.
 */
public class Main {

  public static void main(String[] args) {
    List<EmailXmlPojo.Email> emails = EmailXmlPojo.readConfig();

    for (EmailXmlPojo.Email email: emails) {
      new Thread(MailReaderFactory.getMailReader(email)).start();
    }
  }
}
