package com.lingkarinovasimuda.consumerapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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