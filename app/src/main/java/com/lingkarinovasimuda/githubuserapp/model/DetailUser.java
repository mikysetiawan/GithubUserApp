package com.lingkarinovasimuda.githubuserapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DetailUser implements Parcelable {

	@SerializedName("gists_url")
	private String gistsUrl;

	@SerializedName("repos_url")
	private String reposUrl;

	@SerializedName("following_url")
	private String followingUrl;

	@SerializedName("bio")
	private Object bio;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("login")
	private String login;

	@SerializedName("type")
	private String type;

	@SerializedName("blog")
	private String blog;

	@SerializedName("subscriptions_url")
	private String subscriptionsUrl;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("site_admin")
	private Boolean siteAdmin;

	@SerializedName("company")
	private String company;

	@SerializedName("id")
	private Integer id;

	@SerializedName("public_repos")
	private Integer publicRepos;

	@SerializedName("gravatar_id")
	private String gravatarId;

	@SerializedName("email")
	private Object email;

	@SerializedName("organizations_url")
	private String organizationsUrl;

	@SerializedName("hireable")
	private Object hireable;

	@SerializedName("starred_url")
	private String starredUrl;

	@SerializedName("followers_url")
	private String followersUrl;

	@SerializedName("public_gists")
	private Integer publicGists;

	@SerializedName("url")
	private String url;

	@SerializedName("received_events_url")
	private String receivedEventsUrl;

	@SerializedName("followers")
	private Integer followers;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("events_url")
	private String eventsUrl;

	@SerializedName("html_url")
	private String htmlUrl;

	@SerializedName("following")
	private Integer following;

	@SerializedName("name")
	private String name;

	@SerializedName("location")
	private String location;

	@SerializedName("node_id")
	private String nodeId;

	public DetailUser(){

	}

	public DetailUser(int id, String login, String name, String avatarUrl, int publicRepos, int publicGists, int following, int followers){
		this.id = id;
		this.login = login;
		this.name = name;
		this.avatarUrl = avatarUrl;
		this.publicRepos = publicRepos;
		this.publicGists = publicGists;
		this.followers = followers;
		this.following = following;
	}

	protected DetailUser(Parcel in) {
		gistsUrl = in.readString();
		reposUrl = in.readString();
		followingUrl = in.readString();
		createdAt = in.readString();
		login = in.readString();
		type = in.readString();
		blog = in.readString();
		subscriptionsUrl = in.readString();
		updatedAt = in.readString();
		byte tmpSiteAdmin = in.readByte();
		siteAdmin = tmpSiteAdmin == 0 ? null : tmpSiteAdmin == 1;
		company = in.readString();
		if (in.readByte() == 0) {
			id = null;
		} else {
			id = in.readInt();
		}
		if (in.readByte() == 0) {
			publicRepos = null;
		} else {
			publicRepos = in.readInt();
		}
		gravatarId = in.readString();
		organizationsUrl = in.readString();
		starredUrl = in.readString();
		followersUrl = in.readString();
		if (in.readByte() == 0) {
			publicGists = null;
		} else {
			publicGists = in.readInt();
		}
		url = in.readString();
		receivedEventsUrl = in.readString();
		if (in.readByte() == 0) {
			followers = null;
		} else {
			followers = in.readInt();
		}
		avatarUrl = in.readString();
		eventsUrl = in.readString();
		htmlUrl = in.readString();
		if (in.readByte() == 0) {
			following = null;
		} else {
			following = in.readInt();
		}
		name = in.readString();
		location = in.readString();
		nodeId = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(gistsUrl);
		dest.writeString(reposUrl);
		dest.writeString(followingUrl);
		dest.writeString(createdAt);
		dest.writeString(login);
		dest.writeString(type);
		dest.writeString(blog);
		dest.writeString(subscriptionsUrl);
		dest.writeString(updatedAt);
		dest.writeByte((byte) (siteAdmin == null ? 0 : siteAdmin ? 1 : 2));
		dest.writeString(company);
		if (id == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(id);
		}
		if (publicRepos == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(publicRepos);
		}
		dest.writeString(gravatarId);
		dest.writeString(organizationsUrl);
		dest.writeString(starredUrl);
		dest.writeString(followersUrl);
		if (publicGists == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(publicGists);
		}
		dest.writeString(url);
		dest.writeString(receivedEventsUrl);
		if (followers == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(followers);
		}
		dest.writeString(avatarUrl);
		dest.writeString(eventsUrl);
		dest.writeString(htmlUrl);
		if (following == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeInt(following);
		}
		dest.writeString(name);
		dest.writeString(location);
		dest.writeString(nodeId);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<DetailUser> CREATOR = new Creator<DetailUser>() {
		@Override
		public DetailUser createFromParcel(Parcel in) {
			return new DetailUser(in);
		}

		@Override
		public DetailUser[] newArray(int size) {
			return new DetailUser[size];
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

	public void setBio(Object bio){
		this.bio = bio;
	}

	public Object getBio(){
		return bio;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setLogin(String login){
		this.login = login;
	}

	public String getLogin(){
		return login;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setBlog(String blog){
		this.blog = blog;
	}

	public String getBlog(){
		return blog;
	}

	public void setSubscriptionsUrl(String subscriptionsUrl){
		this.subscriptionsUrl = subscriptionsUrl;
	}

	public String getSubscriptionsUrl(){
		return subscriptionsUrl;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setSiteAdmin(Boolean siteAdmin){
		this.siteAdmin = siteAdmin;
	}

	public boolean isSiteAdmin(){
		return siteAdmin;
	}

	public void setCompany(String company){
		this.company = company;
	}

	public String getCompany(){
		return company;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setPublicRepos(Integer publicRepos){
		this.publicRepos = publicRepos;
	}

	public Integer getPublicRepos(){
		return publicRepos;
	}

	public void setGravatarId(String gravatarId){
		this.gravatarId = gravatarId;
	}

	public String getGravatarId(){
		return gravatarId;
	}

	public void setEmail(Object email){
		this.email = email;
	}

	public Object getEmail(){
		return email;
	}

	public void setOrganizationsUrl(String organizationsUrl){
		this.organizationsUrl = organizationsUrl;
	}

	public String getOrganizationsUrl(){
		return organizationsUrl;
	}

	public void setHireable(Object hireable){
		this.hireable = hireable;
	}

	public Object getHireable(){
		return hireable;
	}

	public void setStarredUrl(String starredUrl){
		this.starredUrl = starredUrl;
	}

	public String getStarredUrl(){
		return starredUrl;
	}

	public void setFollowersUrl(String followersUrl){
		this.followersUrl = followersUrl;
	}

	public String getFollowersUrl(){
		return followersUrl;
	}

	public void setPublicGists(Integer publicGists){
		this.publicGists = publicGists;
	}

	public Integer getPublicGists(){
		return publicGists;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setReceivedEventsUrl(String receivedEventsUrl){
		this.receivedEventsUrl = receivedEventsUrl;
	}

	public String getReceivedEventsUrl(){
		return receivedEventsUrl;
	}

	public void setFollowers(Integer followers){
		this.followers = followers;
	}

	public Integer getFollowers(){
		return followers;
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

	public void setFollowing(Integer following){
		this.following = following;
	}

	public Integer getFollowing(){
		return following;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setNodeId(String nodeId){
		this.nodeId = nodeId;
	}

	public String getNodeId(){
		return nodeId;
	}
}