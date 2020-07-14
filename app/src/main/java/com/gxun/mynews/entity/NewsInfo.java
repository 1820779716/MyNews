package com.gxun.mynews.entity;

public class NewsInfo {

	private String newsId; // 新闻编号
	private String title; // 新闻标题
	private String author; // 作者
	private String time; // 时间
	private String type; // 新闻类型
	private String detail; // 新闻详情
	private String imgUrl; // 图片地址
	private int browseCount; // 浏览量

	public NewsInfo() {
	}

	public NewsInfo(String newsId, String title, String author, String time, String type, String detail, String imgUrl, int browseCount) {
		this.newsId = newsId;
		this.title = title;
		this.author = author;
		this.time = time;
		this.type = type;
		this.detail = detail;
		this.imgUrl = imgUrl;
		this.browseCount = browseCount;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(int browseCount) {
		this.browseCount = browseCount;
	}

	@Override
	public String toString() {
		return "NewsInfo{" +
				"newsId='" + newsId + '\'' +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", time=" + time +
				", type='" + type + '\'' +
				", detail='" + detail + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", browseCount=" + browseCount +
				'}';
	}
}
