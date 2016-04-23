package org.priyanka.com.messenger.exception;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.priyanka.com.messenger.model.ErrorMessage;

//This class ensures all exceptions are caught Ideally impliment a mapper for each exception.
//@Provider 
@Produces(MediaType.APPLICATION_JSON)
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{
	
	@Override
	public Response toResponse(Throwable arg0){
	  ErrorMessage errorMessage = new ErrorMessage(arg0.getMessage(), 500, "url to doc");
	  return Response.status(Status.INTERNAL_SERVER_ERROR)
			  .entity(errorMessage).type(MediaType.APPLICATION_JSON)
			  .build();	
	}

}
