package com.sandeep.emailreader.xmlconfig;

import com.sandeep.emailreader.reader.IMAPMailReader;
import com.sandeep.emailreader.reader.MailReader;
import com.sandeep.emailreader.reader.POPMailReader;

import java.util.concurrent.ThreadFactory;

/**
 * Created by sandeep on 22/2/17.
 */
public class MailReaderFactory {

  public static MailReader getMailReader(EmailXmlPojo.Email email) {
    if (email.host.contains("pop")) {
      return new POPMailReader(email);
    }
    if (email.host.contains("imap")) {
      return new IMAPMailReader(email);
    }

    throw new UnsupportedOperationException("Not a valid host");
  }
}
