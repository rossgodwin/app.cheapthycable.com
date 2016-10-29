package com.gwn.xcbl.data.shared;

import java.io.Serializable;

public class ProviderDTO implements Serializable, ILongId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4175089051049236652L;

	private Long id = -1L;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
