package com.techblogentities;

public class Category {
	private int cid;
	private String name;
	private String description;
	public Category(int id,String n,String d) {
		cid=id;
		name=n;
		description =d;
	}
	public Category() {
		// TODO Auto-generated constructor stub
	}
	public Category(String n,String d) {
		
		name=n;
		description =d;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
