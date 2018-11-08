package com.picgure.api.util;

/*
 * @author Jeet Prakash
 * */

public enum SaveStatus {

	SAVED(1),
	FAILED(2),
	DELETED(3);
	
	private final int id;
	
	SaveStatus(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
