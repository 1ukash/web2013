package ru.spbstu.telematics;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

import ru.spbstu.telematics.dto.Message;

public class XMLparser {
	@XmlRootElement
	static class Messages {
		List<Message> messages = new LinkedList<>();

		public Messages(List<Message> messages) {
			this.messages = messages;
		}

		public Messages() {
		}

		public List<Message> getMessages() {
			return messages;
		}

		public void setMessages(List<Message> messages) {
			this.messages = messages;
		}
		
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			for (Message m : messages) {
				b.append(m).append("\n");
			}
			return b.toString();
		}
	}
	
	public static void main(String[] args) {
		Messages msgs = new Messages();
		Message msg = new Message(1, "petr", "Hello world!");
		msgs.getMessages().add(msg);
		msgs.getMessages().add(new Message(2,"ivan", "Hey!"));
		StringWriter wr = new StringWriter();
		JAXB.marshal(msgs, wr);
		
		String xml = wr.toString();
		System.out.println(xml);
		
		Messages resMsg = (Messages) JAXB.unmarshal(new StringReader(xml), Messages.class);
		System.out.println(resMsg);
	}
}
