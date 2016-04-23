package org.priyanka.com.messenger.resources;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.priyanka.com.messenger.bean.MessageFilterBean;
import org.priyanka.com.messenger.model.Message;
import org.priyanka.com.messenger.service.MessageService;

@Path("/messages")
public class MessageResource {
	MessageService messageService = new MessageService();
	
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Message> getMessages(@QueryParam("year") int year,
		  					  	   @QueryParam("start") int start,
		  						   @QueryParam("size") int size) {
	  System.out.println(messageService.getAllMessages().size());
	  if(year>0){
	    return messageService.getAllMessagesForYear(year);
	  }
	  System.out.println(size + " " + start );
	  if(start >= 0 && size > 0){
		  return messageService.getAllMessagesPage(start, size);
	  }
	  System.out.println(messageService.getAllMessages().size());
	  return messageService.getAllMessages();
  }
  
  //Putting all annotations in a bean
  @GET
  @Path("/bean")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Message> getMessagesBean(@BeanParam MessageFilterBean msb) {
	  System.out.println(messageService.getAllMessages().size());
	  if(msb.getYear()>0){
	    return messageService.getAllMessagesForYear(msb.getYear());
	  }
	  System.out.println(msb.getSize() + " " + msb.getStart() );
	  if(msb.getStart() >= 0 && msb.getSize() > 0){
		  return messageService.getAllMessagesPage(msb.getStart(), msb.getSize());
	  }
	  System.out.println(messageService.getAllMessages().size());
	  return messageService.getAllMessages();
  }
  
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
	  Message newMessage = messageService.addMessage(message);
	  //return Response.status(Status.CREATED)
	  //.entity(newMessage)
	  //.build();
	  String newId = String.valueOf(newMessage.getId());
	  URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
	  return Response.created(uri)
			  .entity(newMessage)
			  .build();
	  
  }
  
  @PUT
  @Path("/{messageId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Message updateMessage(@PathParam("messageId") long messageId, Message message){
	  message.setId(messageId);
	  return messageService.updateMessage(message);
  }
  
  @DELETE
  @Path("/{messageId}")
  @Produces(MediaType.APPLICATION_JSON)
  public void deleteMessage(@PathParam("messageId") long messageId){
	  messageService.deleteMessage(messageId);
  }
  
  @GET
  @Path("/test")
  @Produces(MediaType.TEXT_PLAIN)
  public String test(){
	  return "test";
  }
  
  @GET
  @Path("/{messageId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Message getMessage(@PathParam("messageId") long messageId,
		                    @Context UriInfo uriInfo){
	  //return messageService.getMessage(messageId);	
	  
	  Message msg = messageService.getMessage(messageId);
	 
	  msg.addLinks(getUriForSelf(uriInfo, msg), "self");
	  msg.addLinks(getUriForProfile(uriInfo, msg), "profile");
	  msg.addLinks(getUriForComments(uriInfo, msg), "profile");
	  return msg;
  }

private String getUriForSelf(UriInfo uriInfo, Message msg) {
	String uri = uriInfo.getBaseUriBuilder()
			  .path(MessageResource.class)
			  .path(Long.toString(msg.getId()))
			  .build()
			  .toString();
	return uri;
}

private String getUriForComments(UriInfo uriInfo, Message msg) {
	String uri = uriInfo.getBaseUriBuilder()
			  .path(MessageResource.class)
			  .path(MessageResource.class,"getCommentResource")
			  .path(CommentResource.class)
			  .resolveTemplate("messageId", msg.getId())
			  .build()
			  .toString();
	return uri;
}

private String getUriForProfile(UriInfo uriInfo, Message msg) {
	String uri = uriInfo.getBaseUriBuilder()
			  .path(ProfileResource.class)
			  .path(msg.getAuthor())
			  .build()
			  .toString();
	return uri;
}
  
  
  @Path("/{messageId}/comments")
  @Produces(MediaType.TEXT_PLAIN)
  public CommentResource getCommentResource(){
	  return new CommentResource();
	  //return "hi";
  }
  
}
