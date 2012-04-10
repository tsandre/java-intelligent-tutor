/**
 * 
 */
package itjava.scraper;

/**
 * @author Bharat
 * 
 */
public class UserRatingData {

	private Integer ratingLogId;
	private String user;
	private Integer scrapeId;
	private Integer userRatingYes;
	private Integer userRatingNo;

	private String timeAccessed;

	public Integer getRatingLogId() {
		return ratingLogId;
	}

	public void setRatingLogId(Integer ratingLogId) {
		this.ratingLogId = ratingLogId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getScrapeId() {
		return scrapeId;
	}

	public void setScrapeId(Integer scrapeId) {
		this.scrapeId = scrapeId;
	}

	public Integer getUserRatingYes() {
		return userRatingYes;
	}

	public void setUserRatingYes(Integer userRatingYes) {
		this.userRatingYes = userRatingYes;
	}

	public Integer getUserRatingNo() {
		return userRatingNo;
	}

	public void setUserRatingNo(Integer userRatingNo) {
		this.userRatingNo = userRatingNo;
	}

	public String getTimeAccessed() {
		return timeAccessed;
	}

	public void setTimeAccessed(String timeAccessed) {
		this.timeAccessed = timeAccessed;
	}
	
	
}
