package org.priyanka.com.messenger.model;

public class Comment {
	private String comment;
	private String created;
	private String auther;
	
	public Comment(){
		
	}
	 
	public Comment(String comment, String created, String auther) {
		this.comment = comment;
		this.created = created;
		this.auther = auther;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getAuther() {
		return auther;
	}
	public void setAuther(String auther) {
		this.auther = auther;
	}
	
	

}
