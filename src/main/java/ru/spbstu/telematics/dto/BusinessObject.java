package ru.spbstu.telematics.dto;

public class BusinessObject {

	private String threadName;
	private String parameter;

	public BusinessObject(String parameter, String name) {
		this.parameter = parameter;
		this.threadName = name;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
