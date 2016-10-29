package com.gwn.xcbl.data.shared;

import java.io.Serializable;

public class AccountDTO implements Serializable, ILongId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2290422561492094652L;
	
	private Long id = -1L;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
