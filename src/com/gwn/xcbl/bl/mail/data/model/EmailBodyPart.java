package com.gwn.xcbl.bl.mail.data.model;

import javax.ws.rs.core.MediaType;

public class EmailBodyPart {

	private String name;
	
	private String fileRealPath;
	
	private MediaType mediaType;

	public EmailBodyPart() {
	}
	
	public EmailBodyPart(String name, String fileRealPath, MediaType mediaType) {
		this.name = name;
		this.fileRealPath = fileRealPath;
		this.mediaType = mediaType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileRealPath() {
		return fileRealPath;
	}

	public void setFileRealPath(String fileRealPath) {
		this.fileRealPath = fileRealPath;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
}
