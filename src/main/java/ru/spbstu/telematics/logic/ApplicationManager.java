package ru.spbstu.telematics.logic;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ru.spbstu.telematics.annotations.PermisionRequired;
import ru.spbstu.telematics.annotations.Permission;
import ru.spbstu.telematics.dto.BusinessObject;
import ru.spbstu.telematics.dto.Message;
import ru.spbstu.telematics.dto.User;

public class ApplicationManager {
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static ApplicationManager instance = new ApplicationManager();
	
	private ApplicationManager() {}
	
	public static ApplicationManager getInstance() {
		return instance;
	}
	
	public BusinessObject getBusinessObject(String parameter) {
		return new BusinessObject(parameter, Thread.currentThread().getName());
	}
	
	public List<Message> getMessages() throws Exception {
		List<Message> result = new LinkedList<>();
		
		Connection con = getConnection();
		
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from messages");
		
		while(rs.next()) {
			String user = rs.getString("user");
			System.out.println(user);
			int id = rs.getInt("id");
			System.out.println(id);
			String text = rs.getString("text");
			System.out.println(text);
			result.add(new Message(id,user,text));
		}
		
		con.close();
		return result ;
	}
	
	public Message getMessage(int id) throws Exception {
		Connection con = getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from messages where id=" + id);
		
		if (rs.next()) {
			String user = rs.getString("user");
			String text = rs.getString("text");
			return new Message(id,user,text);
		}
		
		return null;
	}
	
	public List<Message> getMessagesByUser(String user) throws Exception {
		List<Message> result = new LinkedList<>();
		
		Connection con = getConnection();
		
//		Statement st = con.createStatement();
//		ResultSet rs = st.executeQuery("select * from messages where user='" + user + "'");// ';drop table 'users
		PreparedStatement ps = con.prepareStatement("select * from messages where user=?");
		ps.setString(1, user);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println(user);
			int id = rs.getInt("id");
			System.out.println(id);
			String text = rs.getString("text");
			System.out.println(text);
			result.add(new Message(id,user,text));
		}
		
		con.close();
		return result ;
	}

	/**
	 * 
	 * @return
	 * @deprecated
	 * @author lukash
	 */
	@Deprecated
	private Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/foo", "root", "root");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@PermisionRequired(Permission.ADMIN)
	public void createMessage(String user, String msg, User authz) throws Exception {
		Method m = getClass().getMethod("createMessage", String.class, String.class, authz.getClass());
		if (!checkPermissions(m, authz)) {
			throw new RuntimeException("not authorized to do that");
		}
		
		Connection con = getConnection();
		con.setAutoCommit(false);
		
		// query 1;
		
		// query 2;
		
		boolean cond = false;
		if (cond) {
			con.rollback();
		}
		
		PreparedStatement ps = con.prepareStatement("insert into messages (id,user,text ) select max(id)+1, ?, ? from messages");
		ps.setString(1, user);
		ps.setString(2, msg);
		
		ps.executeUpdate();
		
		Savepoint p = con.setSavepoint();
		
		
		//query 3
//		con.rollback(p);
		
		con.commit();
		con.close();		
	}
	
	boolean checkPermissions(Method m, User u) {
		PermisionRequired p = m.getAnnotation(PermisionRequired.class);
		return p.value() == u.getRole();
	}

}
