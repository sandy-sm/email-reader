/**
 * Copyright @ Sandeep Dange
 */
package com.sandeep.emailreader.reader.message;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Representational Object of EmailMessage received
 *
 * Created by sandeep on 27/2/17.
 */
public class EmailMessage {

  private String fromEmailAddress;
  private String toEmailAddresses;
  private String ccEmailAddresses;
  private String subjectLine;
  private String content;

  /**
   * Initializes {@link EmailMessage} from {@link Message}
   *
   * @param message
   * @throws MessagingException
   * @throws IOException
   */
  public void initialize(Message message) throws MessagingException {
    this.setToEmailAddresses(message.getFrom().toString());
    this.setCcEmailAddresses(message.getAllRecipients().toString());
    this.setSubjectLine(message.getSubject());
    //this.setContent((String)message.getContent());
  }

  /**
   * Initializes {@link EmailMessage} from parameters
   *
   * @param fromAddress
   * @param subject
   * @param recipients
   */
  public void initialize(String fromAddress, String subject, String recipients) {
    this.setToEmailAddresses(fromAddress);
    this.setCcEmailAddresses(recipients);
    this.setSubjectLine(subject);
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

  @Override
  public String toString() {
    return "EmailMessage{" +
        "fromEmailAddress='" + fromEmailAddress + '\'' +
        ", toEmailAddresses='" + toEmailAddresses + '\'' +
        ", ccEmailAddresses='" + ccEmailAddresses + '\'' +
        ", subjectLine='" + subjectLine + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
