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
	// @MatrixParam : parameter send to url as semicolon separated key value
	// eg: http://example.com/annotations;param=value
	// @FormParam : Used to get data from html control. Not widely used.
	// @HeaderParam: Custom header values
	// @CookieParam: access cookie
	@GET
	@Path("/annotations")
	public String getParamsUsingAnnotation(@MatrixParam("param") String param,
			@HeaderParam("auth") String auth, @CookieParam("name") String cookie) {
		return "param: " + param + " header: " + auth + " Cookie: " + cookie;
	}

	// @context is used to annotates special types of inputs sent while making a
	// rest request. Mainly used to access all the headers and parameters sent
	// in the request or when you are not sure what parameter you will
	// need.UriInfo contains information about the uri that was
	// requested (eg query param path param absolute path etc)
	// HttpHeaders give all the Header Information.
	@GET
	@Path("/context")
	public String getParamsUsingcontext(@Context UriInfo uriinfo,
			@Context HttpHeaders hdr) {
		String path = uriinfo.getAbsolutePath().toString();
		String cookies = hdr.getCookies().toString();
		return "path: " + path + " cookies: " + cookies;
	}

}
