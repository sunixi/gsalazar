/**
 *
 */
package com.angel.email.dtos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.mail.Address;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;

import com.angel.architecture.exceptions.NonBusinessException;
import com.angel.common.helpers.StringHelper;
import com.angel.common.keyValue.KeyValueResult;
import com.angel.common.keyValue.impl.KeyValueResultImpl;
import com.angel.email.exceptions.EmailAccessException;


/**
 * @author William
 *
 */
public class EmailMessageDTO {

	//TODO change because in flex doesn't exist KeyValueResult object.
	private KeyValueResult headers;
	private String description;
	private String disposition;
	private String contentType;
	private String fileName;
	private String folderName;
	private String subject;
	private String content;
	private Integer lineCount;
	private Integer messageNumber;
	private Date receivedDate;
	private Date sentDate;
	private Integer size;
	private List<RecipientDTO> recipients;

	public EmailMessageDTO(){
		super();
		this.setHeaders(new KeyValueResultImpl());
	}

	@SuppressWarnings("unchecked")
	public EmailMessageDTO(Message message){
		this();
		try {
			this.setDescription(message.getDescription());
			this.setDisposition(message.getDisposition());
			this.setContentType(message.getContentType());
			try {
				this.setContent(message.getContent().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.setFileName(message.getFileName());
			this.setFolderName(message.getFolder().getFullName());
			this.setSubject(message.getSubject());
			this.setLineCount(message.getLineCount());
			this.setMessageNumber(message.getMessageNumber());
			this.setReceivedDate(message.getReceivedDate());
			this.setSentDate(message.getSentDate());
			this.setSize(message.getSize());
			this.setRecipients(new ArrayList<RecipientDTO>());
			Address[] allRecipients = message.getAllRecipients();
			if( allRecipients != null){
				for(Address a: allRecipients){
					this.addRecipientDTO(new RecipientDTO(a));
				}
			}
			Enumeration<Header> headers = message.getAllHeaders();
			for(;headers.hasMoreElements();){
				Header h = headers.nextElement();
				this.getHeaders().addKeyValue(h.getName(), h.getValue());
			}
		} catch (MessagingException e) {
			throw new EmailAccessException("Error building a email message dto.",e);
		}
	}

	public void addRecipientDTO(RecipientDTO recipientDTO){
		this.getRecipients().add(recipientDTO);
	}

	public void addAllRecipientDTO(Collection<RecipientDTO> recipientsDTO){
		this.getRecipients().addAll(recipientsDTO);
	}

	public void removeRecipientDTO(RecipientDTO recipientDTO){
		this.getRecipients().remove(recipientDTO);
	}

	public boolean containsRecipientDTO(RecipientDTO recipientDTO){
		return this.getRecipients().contains(recipientDTO);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the disposition
	 */
	public String getDisposition() {
		return disposition;
	}

	/**
	 * @param disposition the disposition to set
	 */
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the lineCount
	 */
	public Integer getLineCount() {
		return lineCount;
	}

	/**
	 * @param lineCount the lineCount to set
	 */
	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}

	/**
	 * @return the messageNumber
	 */
	public Integer getMessageNumber() {
		return messageNumber;
	}

	/**
	 * @param messageNumber the messageNumber to set
	 */
	public void setMessageNumber(Integer messageNumber) {
		this.messageNumber = messageNumber;
	}

	/**
	 * @return the receivedDate
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate the receivedDate to set
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return the sentDate
	 */
	public Date getSentDate() {
		return sentDate;
	}

	/**
	 * @param sentDate the sentDate to set
	 */
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the recipients
	 */
	public List<RecipientDTO> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(List<RecipientDTO> recipients) {
		this.recipients = recipients;
	}

	public String toString(){
		return this.getFolderName() + "[" + this.getSubject() + "] " + StringHelper.convertToPlainString(this.getRecipients());
	}

	/**
	 * @return the headers
	 */
	public KeyValueResult getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(KeyValueResult headers) {
		this.headers = headers;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
