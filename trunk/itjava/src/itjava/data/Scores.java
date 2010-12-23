/**
 * 
 */
package itjava.data;

/**
 * @author Aniket
 *
 */
public class Scores {
	private int _scoreId;
	private int _deliverableId;
	private int _studentId;
	private int _score;
	private String _date;
	
	/**
	 * @return the scoreId
	 */
	public int getScoreId() {
		return _scoreId;
	}
	/**
	 * @param scoreId the scoreId to set
	 */
	public void setScoreId(int scoreId) {
		_scoreId = scoreId;
	}
	/**
	 * @return the deliverableId
	 */
	public int getDeliverableId() {
		return _deliverableId;
	}
	/**
	 * @param deliverableId the deliverableId to set
	 */
	public void setDeliverableId(int deliverableId) {
		_deliverableId = deliverableId;
	}
	/**
	 * @return the studentId
	 */
	public int getStudentId() {
		return _studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(int studentId) {
		_studentId = studentId;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return _score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		_score = score;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return _date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		_date = date;
	}
}
