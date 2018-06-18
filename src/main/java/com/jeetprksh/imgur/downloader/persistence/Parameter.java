package com.jeetprksh.imgur.downloader.persistence;

public class Parameter {
	
	public final String name;
	
	public final Object value;

	public Parameter(String name, Object value) {
		this.name = name;
		this.value = value;
	}
}