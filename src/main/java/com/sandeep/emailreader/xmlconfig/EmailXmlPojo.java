package com.sandeep.emailreader.xmlconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sandeep on 22/2/17.
 */
@XmlRootElement(name = "emails")
public class EmailXmlPojo {

  static Logger logger = LoggerFactory.getLogger(EmailXmlPojo.class);

  @XmlElement(name = "email")
  List<Email> emailList;

  public static List<Email> readConfig() {
    File file = new File("src/main/resources/user-email-settings.xml");
    EmailXmlPojo email = null;

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(EmailXmlPojo.class);
      email = (EmailXmlPojo) jaxbContext.createUnmarshaller().unmarshal(file);
      logger.info("Got XML Config {}", email.emailList);
    } catch (JAXBException e) {
      logger.error("JAXB Parsing failed due to ", e);
    }

    return email.emailList;
  }

  public static class Email {
    String id;
    String host;
    String port;
    String pass;

    public String getId() {
      return id;
    }

    @XmlAttribute
    public void setId(String id) {
      this.id = id;
    }

    public String getHost() {
      return host;
    }

    @XmlAttribute
    public void setHost(String host) {
      this.host = host;
    }

    public String getPort() {
      return port;
    }

    @XmlAttribute
    public void setPort(String port) {
      this.port = port;
    }

    public String getPass() {
      return pass;
    }

    @XmlAttribute
    public void setPass(String pass) {
      this.pass = pass;
    }

    @Override public String toString() {
      return "Email{" +
          "id='" + id + '\'' +
          ", host='" + host + '\'' +
          ", port='" + port + '\'' +
          ", pass='" + pass + '\'' +
          '}';
    }
  }
}
