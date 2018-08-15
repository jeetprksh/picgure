package com.picgure.api.util;

import com.google.common.base.Optional;

public enum SaveStatus {

	SAVED(1),
	FAILED(2),
	DELETED(3);
	
	private final int id;
	
	private SaveStatus(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public static SaveStatus fromId(int id) {
		SaveStatus type = tryId(id).orNull();
		if (type == null) {
			throw new IllegalArgumentException("Invalid SaveStatus ID: " + id);
		}
		return type;
	}
	
	public static Optional<SaveStatus> tryId(int id) {
		for (SaveStatus t : values()) {
			if (t.id == id) {
				return Optional.of(t);
			}
		}
		return Optional.absent();
	}

}
