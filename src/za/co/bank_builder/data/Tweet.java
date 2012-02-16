package za.co.bank_builder.data;

public class Tweet {
	
	private String date;
	private String tweet;
	private String avatar;
	private String user; 
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the tweet
	 */
	public String getTweet() {
		return tweet;
	}
	/**
	 * @param tweet the tweet to set
	 */
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	

}
