package com.picgure.api.util;

import com.google.common.base.Optional;

public enum DownloadStatus {

	DOWNLOADED(1),	
	FAILED(2),
	DELETED(3);
	
	private final int id;
	
	private DownloadStatus(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public static DownloadStatus fromId(int id) {
		DownloadStatus type = tryId(id).orNull();
		if (type == null) {
			throw new IllegalArgumentException("Invalid DownloadStatus ID: " + id);
		}
		return type;
	}
	
	public static Optional<DownloadStatus> tryId(int id) {
		for (DownloadStatus t : values()) {
			if (t.id == id) {
				return Optional.of(t);
			}
		}
		return Optional.absent();
	}

}
