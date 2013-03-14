package ru.spbstu.telematics.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FooServlet extends HttpServlet {
	private final AtomicInteger counter = new AtomicInteger();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Content-Type", "text/html");
		Enumeration headers = req.getHeaderNames();
		while (headers.hasMoreElements()) {
			String h = (String) headers.nextElement();
			String out = h + ": " + req.getHeader(h);
			System.out.println(out);
		}
		int val = counter.incrementAndGet();
		
		req.setAttribute("counter", new Integer(val));
		req.setAttribute("thread", Thread.currentThread().getName());
		
		req.getRequestDispatcher("/foo.jsp").forward(req, resp);
	}

}
