package org.priyanka.com.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.priyanka.com.messenger.model.Message;
import org.priyanka.com.messenger.model.Profile;

//This is sample implementation of database as a work around.
// Note: I have used type of implementation as this project aims at learning JAX-RS
// Do not have such database implementation for production code.
public class DatabaseClass {
	private static  Map<Long, Message> messages = new HashMap<>();
	private static  Map<String, Profile> profiles = new HashMap<>();
	
	public static Map<Long, Message> getMessages(){
		return messages;
	}
	
	public static Map<String, Profile> getProfiles(){
		return profiles;
	}
	
}
