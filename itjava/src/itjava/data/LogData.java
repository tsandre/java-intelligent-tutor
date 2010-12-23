/**
 * 
 */
package itjava.data;

import itjava.util.Concordance;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores information related to the interaction of the student with a particular quiz 
 * as stored in the log captured by CTAT.
 * @author Aniket, Vasanth
 *
 */
public class LogData {
	private int _correctAnswers;
	private int _incorrectAttemptList[];
	private int _totalIncorrectAttempts;
	private int _hintList[];
	private int _score;
	private int _totalActions;
	public Concordance<String> incorrectAttemptsConcordance;
	public Concordance<String> hintsUsed;
	public Concordance<String> hintsAvailable;
	
	public LogData(int numOfBlanks) {
		if (numOfBlanks != 0) {
		_incorrectAttemptList = new int[numOfBlanks];
		_hintList = new int[numOfBlanks];
		incorrectAttemptsConcordance = new Concordance<String>();
		hintsUsed = new Concordance<String>();
		}
		else if(numOfBlanks == 0) {
			_score = 100;
		}
		
	}

	/**
	 * @return the correctAnswers
	 */
	public int getCorrectAnswers() {
		return _correctAnswers;
	}

	/**
	 * @param correctAnswers the correctAnswers to set
	 */
	public void setCorrectAnswers(int correctAnswers) {
		_correctAnswers = correctAnswers;
	}

	/**
	 * @return the incorrectAttemptList
	 */
	public int[] getIncorrectAttemptList() {
		return _incorrectAttemptList;
	}
	
	/**
	 * @param index
	 * @return the value of incorrect attempts at the given index
	 */
	public int getIncorrectAttemptAtIndex(int index) {
		int retVal;
		try {
			retVal = _incorrectAttemptList[index];
		}
		catch (ArrayIndexOutOfBoundsException e) {
			retVal = -1;
		}
		catch (NullPointerException e) {
			e.printStackTrace();
			retVal = -1;
		}
		return retVal;
	}

	/**
	 * @param incorrectAttemptList the incorrectAttemptList to set
	 */
	public void setIncorrectAttemptList(int[] incorrectAttemptList) {
		_incorrectAttemptList = incorrectAttemptList;
	}

	/**
	 * @return the totalIncorrectAttempts
	 */
	public int getTotalIncorrectAttempts() {
		return _totalIncorrectAttempts;
	}

	/**
	 * @param totalIncorrectAttempts the totalIncorrectAttempts to set
	 */
	public void setTotalIncorrectAttempts(int totalIncorrectAttempts) {
		_totalIncorrectAttempts = totalIncorrectAttempts;
	}

	/**
	 * @return the hintList
	 */
	public int[] getHintList() {
		return _hintList;
	}

	/**
	 * @param hintList the hintList to set
	 */
	public void setHintList(int[] hintList) {
		_hintList = hintList;
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
	 * @param totalActions the totalActions to set
	 */
	public void setTotalActions(int totalActions) {
		_totalActions = totalActions;
	}

	/**
	 * @return the totalActions
	 */
	public int getTotalActions() {
		return _totalActions;
	}

}
