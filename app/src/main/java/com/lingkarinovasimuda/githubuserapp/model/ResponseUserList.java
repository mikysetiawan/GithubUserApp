package com.lingkarinovasimuda.githubuserapp.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseUserList {

	@SerializedName("Response")
	private List<UserItem> response;

	public void setResponse(List<UserItem> response){
		this.response = response;
	}

	public List<UserItem> getResponse(){
		return response;
	}
}