package com.sandeep.emailreader.reader;

import com.sandeep.emailreader.xmlconfig.EmailXmlPojo;

/**
 * Created by sandeep on 22/2/17.
 */
public interface MailReader extends Runnable {

  public void checkMailsAndPost();

}
