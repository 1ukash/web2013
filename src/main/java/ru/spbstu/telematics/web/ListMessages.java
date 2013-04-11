package ru.spbstu.telematics.web;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.spbstu.telematics.annotations.Permission;
import ru.spbstu.telematics.dto.Message;
import ru.spbstu.telematics.dto.User;
import ru.spbstu.telematics.logic.ApplicationManager;

@SuppressWarnings("serial")
public class ListMessages extends HttpServlet {

	private ApplicationManager manager = ApplicationManager.getInstance();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User user = (User) req.getSession().getAttribute("user");
		if (user == null) {
			user = new User(Permission.ADMIN, "generic user");
			req.getSession().setAttribute("user", user);
		}
		
		String action = req.getParameter("action");
		if (action == null) {
			action = "list";
		}
		switch (action) {
		case "add":
			addMessage(req,resp, user);
			break;
		default:
			listMessages(req, resp);
			break;
		}
		
	}
	private void addMessage(HttpServletRequest req, HttpServletResponse resp, User authz) {
		String user = req.getParameter("user");
		String msg = req.getParameter("msg");
		
		try {
			manager.createMessage(user,msg, authz);
			resp.getOutputStream().write("OK".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void listMessages(HttpServletRequest req, HttpServletResponse resp) {
		try {
			List<Message> messages = null;
			String id = req.getParameter("id");
			String user = req.getParameter("user");
			if (user != null) {
				messages = manager.getMessagesByUser(user);
			} else if (id != null) {
				messages = Collections.singletonList(manager.getMessage(Integer.valueOf(id)));
			} else {
				messages = manager.getMessages();
			}
			req.setAttribute("messages", messages);
			req.getRequestDispatcher("/messages.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
