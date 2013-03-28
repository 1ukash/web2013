package ru.spbstu.telematics.web.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class HelloService {
	private String msg = "Hello,";

	public HelloService() {
	}

	@WebMethod
	public String sayHello(@WebParam(name="name") String name) {
		return msg + name;
	}

}