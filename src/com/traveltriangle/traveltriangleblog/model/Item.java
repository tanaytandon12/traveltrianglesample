package com.traveltriangle.traveltriangleblog.model;

public class Item {

	private String description;
	private String title;
	private String time;
	private String link;

	public Item() {
		this.description = null;
		this.time = null;
		this.link = null;
		this.title = null;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return this.time;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return this.link;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
}
