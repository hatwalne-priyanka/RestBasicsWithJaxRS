package org.priyanka.com.messenger.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class CommentResource {
  @GET	
  public String getCommentResource1(){
	  return "new sub resource";
  }
  
  @GET
  @Path("/{commentId}")
  public String getCommentId(@PathParam("messageId") long messageId,
		  @PathParam("commentId") long commentId){
	  return "msg id: " + messageId + " cmt id: " + commentId;
  }
  
}
