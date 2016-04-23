package org.priyanka.com.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.priyanka.com.messenger.model.ErrorMessage;

//Maps and handles datanotfound exceptions and returns a JSON response. 
//This prevents the tomcat exception page to show up in case this exception occur
// do not forget to use @Provider annotation as it registers the mapper with jax-rs.
@Provider
public class DataNotFoundExceptionMapper
		implements ExceptionMapper<DataNotFoundException> {
	@Override
	public Response toResponse(DataNotFoundException arg0) {
		//content of response
		ErrorMessage errorMessage = new ErrorMessage(arg0.getMessage(), 404,
				"link to doc");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}
}
