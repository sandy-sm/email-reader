package com.sandeep.emailreader.reader.message;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by sandeep on 27/2/17.
 */
public class EmailMessage {

  private String fromEmailAddress;
  private String toEmailAddresses;
  private String ccEmailAddresses;
  private String subjectLine;
  private String content;

  public void getEmailMessage(Message message) throws MessagingException, IOException {
    this.setToEmailAddresses(message.getFrom().toString());
    this.setCcEmailAddresses(message.getAllRecipients().toString());
    this.setSubjectLine(message.getSubject());
    //this.setContent((String)message.getContent());
  }

  public String getFromEmailAddress() {
    return fromEmailAddress;
  }

  public void setFromEmailAddress(String fromEmailAddress) {
    this.fromEmailAddress = fromEmailAddress;
  }

  public String getToEmailAddresses() {
    return toEmailAddresses;
  }

  public void setToEmailAddresses(String toEmailAddresses) {
    this.toEmailAddresses = toEmailAddresses;
  }

  public String getCcEmailAddresses() {
    return ccEmailAddresses;
  }

  public void setCcEmailAddresses(String ccEmailAddresses) {
    this.ccEmailAddresses = ccEmailAddresses;
  }

  public String getSubjectLine() {
    return subjectLine;
  }

  public void setSubjectLine(String subjectLine) {
    this.subjectLine = subjectLine;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override public String toString() {
    return "EmailMessage{" +
        "fromEmailAddress='" + fromEmailAddress + '\'' +
        ", toEmailAddresses='" + toEmailAddresses + '\'' +
        ", ccEmailAddresses='" + ccEmailAddresses + '\'' +
        ", subjectLine='" + subjectLine + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
