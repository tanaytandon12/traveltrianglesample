package com.traveltriangle.traveltriangleblog.model;

public class Item {

	private String description;
	private String title;
	private String time;
	private String link;

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDate(String time) {
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
