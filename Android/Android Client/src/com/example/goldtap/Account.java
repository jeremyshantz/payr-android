package com.example.goldtap;

public class Account {
	
	private String _id;
	private String _fname;
	private String _lname;
	
	public Account(){
		
		_id = "";
		_fname = "";
		_lname = "";
	}
	
	public Account(String id, String fname, String lname){
		
		_id = id;
		_fname = fname;
		_lname = lname;
	}

	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
	}

	public String getFname() {
		return _fname;
	}

	public void setFname(String _fname) {
		this._fname = _fname;
	}

	public String getLname() {
		return _lname;
	}

	public void setLname(String _lname) {
		this._lname = _lname;
	}
	

}
