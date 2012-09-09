package org.hb.bus.api;

public class PollOption {
	private String pollTitle;
	private int count;
	public PollOption(String pollTitle, int count){
		this.pollTitle = pollTitle;
		this.count = count;
	}
	
	public String getPollTitle() {
		return pollTitle;
	}
	public void setPollTitle(String pollTitle) {
		this.pollTitle = pollTitle;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
