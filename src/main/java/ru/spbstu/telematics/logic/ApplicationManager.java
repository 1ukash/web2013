package ru.spbstu.telematics.logic;

import ru.spbstu.telematics.dto.BusinessObject;

public class ApplicationManager {

	public BusinessObject getBusinessObject(String parameter) {
		return new BusinessObject(parameter, Thread.currentThread().getName());
	}

}
