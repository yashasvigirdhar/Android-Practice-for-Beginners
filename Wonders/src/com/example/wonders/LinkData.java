package com.example.wonders;

public class LinkData {

	
	public String name;
	public String imagename;
	public String desc;

	public LinkData(String name, String imagename, String desc) {
		this.name = name;
		this.imagename = imagename;
		this.desc=desc;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImageName() {
		return imagename;
	}
	public void setImageName(String imagename) {
		this.imagename = imagename;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}


}