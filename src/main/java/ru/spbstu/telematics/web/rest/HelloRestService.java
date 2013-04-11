package ru.spbstu.telematics.web.rest;

import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import ru.spbstu.telematics.annotations.Permission;
import ru.spbstu.telematics.dto.Message;
import ru.spbstu.telematics.dto.User;
import ru.spbstu.telematics.logic.ApplicationManager;

@Path("/sayhello")
public class HelloRestService {
	ApplicationManager manager = ApplicationManager.getInstance();
	
	@GET
	@Produces("text/xml")
	@Path("/say.xml/{name}")
	public Message sayHello(@PathParam("name") String name, @QueryParam("q") String symbol) {
		
		Message m = new Message(new Random().nextInt(), "service", name + ((symbol==null) ? "" : symbol));
		
		return m;
	}

	@GET
	@Produces("application/json")
	@Path("/say.json/{name}")
	public Message sayHelloJson(@PathParam("name") String name, @QueryParam("q") String symbol) {
		Message m = new Message(new Random().nextInt(), "service", name + ((symbol==null) ? "" : symbol));
		return m;
	}
	
	@PUT
	@Produces("application/json")
	@Path("/msg/{user}/{message}")
	public Message storeMessage(@PathParam("user") String user, @PathParam("message")String message) {
		Message m = new Message(-1, user, message);
		
		try {
			manager.createMessage(user, message, new User(Permission.ADMIN, "Pupkin"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return m;
	}
	
	@GET
	@Produces("application/json")
	@Path("/msg")
	public List<Message> getAllMessages() throws Exception {
		return manager.getMessages();
	}
}
