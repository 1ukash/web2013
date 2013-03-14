package ru.spbstu.telematics.web;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.http.HttpTester.Request;

import ru.spbstu.telematics.dto.BusinessObject;
import ru.spbstu.telematics.logic.ApplicationManager;

public class FooServlet extends HttpServlet {
	private final AtomicInteger counter = new AtomicInteger();
	private final ApplicationManager manager = new ApplicationManager();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Content-Type", "text/html");
		
		BusinessObject obj = manager.getBusinessObject(req.getParameter("p"));
		
		int val = counter.incrementAndGet();
		req.setAttribute("counter", new Integer(val));
		req.setAttribute("thread", Thread.currentThread().getName());
		req.setAttribute("obj", obj);
		
		HttpSession session = req.getSession();
		
		if (session.getAttribute("date") == null) {
			session.setAttribute("date", new Date());
		}
		
		req.setAttribute("date", new Date());
		
		req.getRequestDispatcher("/foo.jsp").forward(req, resp);
	}

}
