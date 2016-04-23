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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.priyanka.com.messenger.bean.MessageFilterBean;
import org.priyanka.com.messenger.model.Message;
import org.priyanka.com.messenger.service.MessageService;

//All http requests starting with "/messages" will be addressed by this Resource class.

@Path("/messages")
public class MessageResource {
	MessageService messageService = new MessageService();

	// This method gets called when a get request is made on message resource.
	// It returns a JSON Response and optionally take input in form of query
	// parameters to filter data by year or for paging. Query parameters are
	// parameters given to URL after a question mark ("?")
	// eg http://example.com/messages?year=2015
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@QueryParam("year") int year, @QueryParam("start") int start,
			@QueryParam("size") int size) {
		System.out.println(messageService.getAllMessages().size());
		if (year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		// System.out.println(size + " " + start );
		if (start >= 0 && size > 0) {
			return messageService.getAllMessagesPage(start, size);
		}
		// System.out.println(messageService.getAllMessages().size());
		return messageService.getAllMessages();
	}

	// This method gets called when a get request is made whith "/bean" added to
	// "/messages" in the request url.It returns a JSON Response and optionally
	// take input in form of query parameters to filter data by year or for
	// paging. Query parameters are translated into a bean param by
	// MessageFilterBean 
	@GET
	@Path("/bean")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessagesBean(@BeanParam MessageFilterBean msb) {
		System.out.println(messageService.getAllMessages().size());
		if (msb.getYear() > 0) {
			return messageService.getAllMessagesForYear(msb.getYear());
		}
		System.out.println(msb.getSize() + " " + msb.getStart());
		if (msb.getStart() >= 0 && msb.getSize() > 0) {
			return messageService.getAllMessagesPage(msb.getStart(), msb.getSize());
		}
		System.out.println(messageService.getAllMessages().size());
		return messageService.getAllMessages();
	}

	// Post request to message resource is addressed by this class to add data
	// It takes a json input and returns a json response. 
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		Message newMessage = messageService.addMessage(message);
		
		//change status to 201 which is a code for created
		// return Response.status(Status.CREATED)
		// .entity(newMessage)
		// .build(); /
		
		
		
		String newId = String.valueOf(newMessage.getId());
		//build new url from uriInfo
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		// return Response.status(Status.CREATED).location(uri).entity(newMessage).build()
		//alternative of using Response.status(Status.CREATED).location(uri)
		return Response.created(uri).entity(newMessage).build();

	}

	// Put request to message resource is addressed by this class to update data
	// It takes a json input and returns a json response. 
	@PUT
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}

	// delete request to message resource is addressed by this class to delete data
	// It takes input as message id from path parameter and returns a json response. 
	// Path parameter is a parameter appended to message resource url
	// Eg: http://example.com/messages/1
	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteMessage(@PathParam("messageId") long messageId) {
		messageService.deleteMessage(messageId);
	}

	// test method. remove later .  
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "test";
	}

	// get request to message resource is addressed by this class to get message by id 
	// It takes input as message id from path parameter and returns a json response. 
	// Path parameter is a parameter appended to message resource url
	// Eg: http://example.com/messages/1
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
		// return messageService.getMessage(messageId);

		Message msg = messageService.getMessage(messageId);
        //urls for HATEOAS implementation
		msg.addLinks(getUriForSelf(uriInfo, msg), "self");
		msg.addLinks(getUriForProfile(uriInfo, msg), "profile");
		msg.addLinks(getUriForComments(uriInfo, msg), "profile");
		return msg;
	}
    // create url for message resource for HATEOAS implementation
	private String getUriForSelf(UriInfo uriInfo, Message msg) {
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(Long.toString(msg.getId())).build()
				.toString();
		return uri;
	}

	// create url for comment resource for HATEOAS implementation
	private String getUriForComments(UriInfo uriInfo, Message msg) {
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource").path(CommentResource.class)
				.resolveTemplate("messageId", msg.getId()).build().toString();
		return uri;
	}

	// create url for profile resource for HATEOAS implementation
	private String getUriForProfile(UriInfo uriInfo, Message msg) {
		String uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(msg.getAuthor()).build().toString();
		return uri;
	}

	// nested resource. Returns all the comments  for a given message id.
	@Path("/{messageId}/comments")
	@Produces(MediaType.TEXT_PLAIN)
	public CommentResource getCommentResource() {
		return new CommentResource();
		// return "hi";
	}

}
