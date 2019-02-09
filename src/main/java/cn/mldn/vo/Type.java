package cn.mldn.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Type implements Serializable {
	private int tid;
	private String title;
	private String flag;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
