/**
 * 
 */
package com.pramati.imaginea.Entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**This 
 * @author anandu
 *
 */
@XmlRootElement
public class mail{
	
	private String id;
	private String from;
	private String subject;
	private String date;
	private String contents;
	/**
	 * 
	 */
	public mail() {
		
	}
	public mail(String id,String from,String subject,String date,String contents) {
		this.id = id;
		this.from = from;
		this.subject = subject;
		this.date = date;
		this.contents = contents;
	}
	/**
	 * @return the id
	 */
	@XmlAttribute
	public String getId() {
		return id;
	}
	/**
	 * @return the from
	 */
	@XmlElement
	public String getFrom() {
		return from;
	}
	/**
	 * @return the subject
	 */
	@XmlElement
	public String getSubject() {
		return subject;
	}
	/**
	 * @return the date
	 */
	@XmlElement
	public String getDate() {
		return date;
	}
	/**
	 * @return the contents
	 */
	@XmlElement
	public String getContents() {
		return contents;
	}
	/**
	 * @param id the id to set
	 */
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	
}
