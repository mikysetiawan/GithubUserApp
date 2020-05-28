package com.lingkarinovasimuda.githubuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserItem implements Parcelable {

	@SerializedName("gists_url")
	private String gistsUrl;

	@SerializedName("repos_url")
	private String reposUrl;

	@SerializedName("following_url")
	private String followingUrl;

	@SerializedName("starred_url")
	private String starredUrl;

	@SerializedName("login")
	private String login;

	@SerializedName("followers_url")
	private String followersUrl;

	@SerializedName("type")
	private String type;

	@SerializedName("url")
	private String url;

	@SerializedName("subscriptions_url")
	private String subscriptionsUrl;

	@SerializedName("score")
	private Double score;

	@SerializedName("received_events_url")
	private String receivedEventsUrl;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("events_url")
	private String eventsUrl;

	@SerializedName("html_url")
	private String htmlUrl;

	@SerializedName("site_admin")
	private Boolean siteAdmin;

	@SerializedName("id")
	private Integer id;

	@SerializedName("gravatar_id")
	private String gravatarId;

	@SerializedName("node_id")
	private String nodeId;

	@SerializedName("organizations_url")
	private String organizationsUrl;

	protected UserItem(Parcel in) {
		gistsUrl = in.readString();
		reposUrl = in.readString();
		followingUrl = in.readString();
		starredUrl = in.readString();
		login = in.readString();
		followersUrl = in.readString();
		type = in.readString();
		url = in.readString();
		subscriptionsUrl = in.readString();
		if (in.readByte() == 0) {
			score = null;
		} else {
			score = in.readDouble();
		}
		receivedEventsUrl = in.readString();
		avatarUrl = in.readString();
		eventsUrl = in.readString();
		htmlUrl = in.readString();
		byte tmpSiteAdmin = in.readByte();
		siteAdmin = tmpSiteAdmin == 0 ? null : tmpSiteAdmin == 1;
		if (in.readByte() == 0) {
			id = null;
		} else {
			id = in.readInt();
		}
		gravatarId = in.readString();
		nodeId = in.readString();
		organizationsUrl = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(gistsUrl);
		dest.writeString(reposUrl);
		dest.writeString(followingUrl);
		dest.writeString(starredUrl);
		dest.writeString(login);
		dest.writeString(followersUrl);
		dest.writeString(type);
		dest.writeString(url);
		dest.writeString(subscriptionsUrl);
		if (score == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeDouble(score);
		}
		dest.writeString(receivedEventsUrl);
		dest.writeString(avatarUrl);
		dest.writeString(eventsUrl);
		dest.writeString(htmlUrl);
		dest.writeByte((byte) (siteAdmin == null ? 0 : siteAdmin ? 1 : 2));
		if (id == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(id);
		}
		dest.writeString(gravatarId);
		dest.writeString(nodeId);
		dest.writeString(organizationsUrl);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
		@Override
		public UserItem createFromParcel(Parcel in) {
			return new UserItem(in);
		}

		@Override
		public UserItem[] newArray(int size) {
			return new UserItem[size];
		}
	};

	public void setGistsUrl(String gistsUrl){
		this.gistsUrl = gistsUrl;
	}

	public String getGistsUrl(){
		return gistsUrl;
	}

	public void setReposUrl(String reposUrl){
		this.reposUrl = reposUrl;
	}

	public String getReposUrl(){
		return reposUrl;
	}

	public void setFollowingUrl(String followingUrl){
		this.followingUrl = followingUrl;
	}

	public String getFollowingUrl(){
		return followingUrl;
	}

	public void setStarredUrl(String starredUrl){
		this.starredUrl = starredUrl;
	}

	public String getStarredUrl(){
		return starredUrl;
	}

	public void setLogin(String login){
		this.login = login;
	}

	public String getLogin(){
		return login;
	}

	public void setFollowersUrl(String followersUrl){
		this.followersUrl = followersUrl;
	}

	public String getFollowersUrl(){
		return followersUrl;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setSubscriptionsUrl(String subscriptionsUrl){
		this.subscriptionsUrl = subscriptionsUrl;
	}

	public String getSubscriptionsUrl(){
		return subscriptionsUrl;
	}

	public void setScore(Double score){
		this.score = score;
	}

	public Double getScore(){
		return score;
	}

	public void setReceivedEventsUrl(String receivedEventsUrl){
		this.receivedEventsUrl = receivedEventsUrl;
	}

	public String getReceivedEventsUrl(){
		return receivedEventsUrl;
	}

	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public void setEventsUrl(String eventsUrl){
		this.eventsUrl = eventsUrl;
	}

	public String getEventsUrl(){
		return eventsUrl;
	}

	public void setHtmlUrl(String htmlUrl){
		this.htmlUrl = htmlUrl;
	}

	public String getHtmlUrl(){
		return htmlUrl;
	}

	public void setSiteAdmin(Boolean siteAdmin){
		this.siteAdmin = siteAdmin;
	}

	public boolean isSiteAdmin(){
		return siteAdmin;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setGravatarId(String gravatarId){
		this.gravatarId = gravatarId;
	}

	public String getGravatarId(){
		return gravatarId;
	}

	public void setNodeId(String nodeId){
		this.nodeId = nodeId;
	}

	public String getNodeId(){
		return nodeId;
	}

	public void setOrganizationsUrl(String organizationsUrl){
		this.organizationsUrl = organizationsUrl;
	}

	public String getOrganizationsUrl(){
		return organizationsUrl;
	}
}