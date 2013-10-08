package com.gic9;

import com.j256.ormlite.field.DatabaseField;


public class Memo {

	
	@DatabaseField(generatedId =true)
	private int id;
	
	@DatabaseField(canBeNull = true)
	private String contents;
	
	public Memo(){
		
	}
	
	public Memo(String contents){
		this.contents =contents;
		
	}
	public int getId(){
		return id;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
