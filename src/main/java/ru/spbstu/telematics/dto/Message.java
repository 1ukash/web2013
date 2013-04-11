package ru.spbstu.telematics.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonRootName;

import ru.spbstu.telematics.annotations.Description;
import ru.spbstu.telematics.annotations.EnumExample;

@Description(ex=EnumExample.A, text = "it is a text", title = "A", version=2)
@XmlRootElement
public class Message {
	private int number;
	private String user;
	private String text;
	
	public Message() {
	}
	public Message(int number, String user, String text) {
		this.number = number;
		this.user = user;
		this.text = text;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUser() {
		return user;
	}
	@XmlAttribute
	public void setUser(String user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String toString() {
		return number + ", " + user + ", " + text;
	}
}