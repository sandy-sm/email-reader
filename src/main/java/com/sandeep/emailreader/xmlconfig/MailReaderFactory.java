/**
 * Copyright @ Sandeep Dange
 */
package com.sandeep.emailreader.xmlconfig;

import com.sandeep.emailreader.reader.IMAPMailReader;
import com.sandeep.emailreader.reader.MailReader;
import com.sandeep.emailreader.reader.POPMailReader;

import java.util.concurrent.ThreadFactory;

/**
 * Mail Reader Factory to get instances of email reader {@link MailReader}
 *
 * Created by sandeep on 22/2/17.
 */
public class MailReaderFactory {

  /**
   * Returns Instance of {@link MailReader} based on host of email
   *
   * If host is not found, throws {@link UnsupportedOperationException}
   * The caller must handle the exception for error handling
   *
   * @param email
   * @return
   */
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
