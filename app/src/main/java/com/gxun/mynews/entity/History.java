package com.gxun.mynews.entity;

public class History {

	private String userId; // 用户编号
	private String newId; // 新闻编号
	private String browsCount; // 浏览量

	public History() {
	}

	public History(String userId, String newId, String browsCount) {
		this.userId = userId;
		this.newId = newId;
		this.browsCount = browsCount;
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

	public String getBrowsCount() {
		return browsCount;
	}

	public void setBrowsCount(String browsCount) {
		this.browsCount = browsCount;
	}

	@Override
	public String toString() {
		return "History{" +
				"userId=" + userId +
				", newId='" + newId + '\'' +
				", browsCount='" + browsCount + '\'' +
				'}';
	}
}
