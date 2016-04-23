package org.priyanka.com.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class IntectDemoResource {
  //@MatrixParam : parameter send to url as semicolon separated key value 	
  //@FormParam : Used to get data from html control. Not widely used.	
  @GET
  @Path("/annotations")
  public String getParamsUsingAnnotation(@MatrixParam("param") String param,
		                                 @HeaderParam("auth") String auth,
		                                 @CookieParam("name") String cookie) {  
	  return "param: " + param + " header: " + auth + " Cookie: " + cookie;
  }
  
  @GET
  @Path("/context")
  public String getParamsUsingcontext(@Context UriInfo uriinfo,
		                              @Context HttpHeaders hdr){ 
	  String path = uriinfo.getAbsolutePath().toString();
	  String cookies = hdr.getCookies().toString();
	  return "path: " + path + " cookies: " + cookies;
  }
  
}
