package org.priyanka.com.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.priyanka.com.messenger.database.DatabaseClass;
import org.priyanka.com.messenger.model.Profile;


public class ProfileService {
	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("priyanka", new Profile(1, "priyanka", "priyanka","hatwalne"));
		profiles.put("user2", new Profile(2, "user2", "firstname" ,"lastname2"));
	}
	
	 public List<Profile> getAllProfiles(){		 
		 return new ArrayList <Profile>(profiles.values());
	 }

	 public Profile getProfile(String profilename){
		 return profiles.get(profilename);
	 }
	 
	 public Profile addProfile(Profile profile){
		 profile.setId(profiles.size() + 1);
		 profiles.put(profile.getProfileName(), profile);
		 return profile;
	 }
	 
	 public Profile updateProfile(Profile profile){
		 if(profile.getId() <=0) {
			 return null;
		 }
		 
		 profiles.put(profile.getProfileName(), profile);
		 return profile;
	 }
	 
	 public Profile deleteProfile(String profilename){
		 return profiles.remove(profilename);
	 }

}
