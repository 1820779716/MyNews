package com.gxun.mynews.entity;

public class UserInfo {
	private String userId; // 用户编号
	private String userName; // 用户名
	private String password; // 密码
	private String tel; // 手机号
	private String email; // 邮箱

	public UserInfo() {
	}

	public UserInfo(String userId, String userName, String password, String tel, String email) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.tel = tel;
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				"userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", tel='" + tel + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
