package ru.spbstu.telematics.web.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import ru.spbstu.telematics.dto.Message;

@WebService
public class HelloService {
	private String msg = "Hello,";

	public HelloService() {
	}

	@WebMethod
	public Message sayHello(@WebParam(name="name") Message name) {
		
		Message m = new Message(1, "service", msg + name.getUser() + "! You id is " + name.getNumber());
		
		return m;
	}

}