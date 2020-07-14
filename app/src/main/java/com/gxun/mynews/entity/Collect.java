package com.gxun.mynews.entity;

public class Collect {

	private String userId; // 用户编号
	private String newId; // 新闻编号

	public Collect() {
	}

	public Collect(String userId, String newId) {
		this.userId = userId;
		this.newId = newId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNewId() {
		return newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}

	@Override
	public String toString() {
		return "Collect{" +
				"userId='" + userId + '\'' +
				", newId='" + newId + '\'' +
				'}';
	}
}
