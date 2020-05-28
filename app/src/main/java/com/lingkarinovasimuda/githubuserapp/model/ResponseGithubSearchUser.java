package com.lingkarinovasimuda.githubuserapp.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGithubSearchUser {

	@SerializedName("total_count")
	private Integer totalCount;

	@SerializedName("incomplete_results")
	private Boolean incompleteResults;

	@SerializedName("items")
	private List<UserItem> items;

	public void setTotalCount(Integer totalCount){
		this.totalCount = totalCount;
	}

	public Integer getTotalCount(){
		return totalCount;
	}

	public void setIncompleteResults(Boolean incompleteResults){
		this.incompleteResults = incompleteResults;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public void setItems(List<UserItem> items){
		this.items = items;
	}

	public List<UserItem> getItems(){
		return items;
	}
}