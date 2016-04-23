package org.priyanka.com.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.priyanka.com.messenger.model.Profile;
import org.priyanka.com.messenger.service.ProfileService;

@Path("/profiles")
public class ProfileResource {
	ProfileService profileService = new ProfileService();
	
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<Profile> getProfiles() {
		  return profileService.getAllProfiles();
	  }
	  
	  @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Profile addProfile(Profile profile) {
		  return profileService.addProfile(profile);
	  }
	  
	  @PUT
	  @Path("/{profilename}")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Profile updateProfile(@PathParam("profilename") String profilename, Profile profile){
		  System.out.println(profilename);
		  profile.setProfileName(profilename);
		  
		  return profileService.updateProfile(profile);
	  }
	  
	  @DELETE
	  @Path("/{profilename}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public void deleteProfile(@PathParam("profilename") String profilename){
		  profileService.deleteProfile(profilename);
	  }
	  
	
	  
	  @GET
	  @Path("/{profilename}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public Profile getMessage(@PathParam("profilename") String profilename){
		  return profileService.getProfile(profilename);
		  
	  }
	  

}
