package org.priyanka.com.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.priyanka.com.messenger.database.DatabaseClass;
import org.priyanka.com.messenger.exception.DataNotFoundException;
import org.priyanka.com.messenger.model.Comment;
import org.priyanka.com.messenger.model.ErrorMessage;
import org.priyanka.com.messenger.model.Message;

public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService(){
		messages.put(1L, new Message(1, "hello", "priyanka"));
		messages.put(2L, new Message(2, "hi", "shashank"));
	}
	
	 public List<Message> getAllMessages(){		 
		 return new ArrayList <Message>(messages.values());
	 }
	 
	 public List<Message> getAllMessagesForYear(int year){		 
		 List <Message> msgforyear = new ArrayList<>();
		 Calendar cal = Calendar.getInstance();
		 for (Message message : messages.values()){
			 cal.setTime(message.getCreated());
			 if(cal.get(Calendar.YEAR) == year){
				 msgforyear.add(message);
			 }
		 }
		 return msgforyear;
	 }
	 
	 public List<Message> getAllMessagesPage(int start, int size){		 
		 List <Message> list = new ArrayList<Message>(messages.values());
		 return list.subList(start, start + size);
	 }
	 

	 public Message getMessage(long id){
	    Message message = messages.get(id);
	    if (message == null){
	    	ErrorMessage errorMessage = new ErrorMessage("Message with Id " + id + " not Found", 404, "link");
	    	Response response = Response.status(Status.NOT_FOUND)
	    			                    .entity(errorMessage)
	    			                    .build();
	    	
	    	throw new NotFoundException(response);
	    	//throw new WebApplicationException(response);
	    	//throw new DataNotFoundException("Message with Id " + id + " not Found");
	    }
	    return message;
	 }
	 
	 public Message addMessage(Message message){
		 message.setId(messages.size() + 1);
		 messages.put(message.getId(), message);
		 return message;
	 }
	 
	 public Message updateMessage(Message message){
		 if(message.getId() <=0) {
			 return null;
		 }
		 
		 messages.put(message.getId(), message);
		 return message;
	 }
	 
	 public Message deleteMessage(long id){
		 return messages.remove(id);
	 }
	 
	 public List<Comment> getAllComments(){
		 Comment c = new Comment();
		 List <Comment> list = new ArrayList<Comment>();
		 list.add(c);
		 return list;
	 }
	 
	 public Comment addComment(Comment comment){
		 return comment;
	 }
	 
	 public Comment updateComment(Comment comment) {
		 return comment;
	 }
	 
	 public void removecomment(Comment comment){
		 
	 }
}
