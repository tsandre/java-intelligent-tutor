/**
 * 
 */
package itjava.model;

import itjava.data.Scores;
import itjava.db.DBConnection;
import itjava.util.KeyValue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Aniket
 *
 */
public class DeliverableLauncher {
	
	private int _studentId;
	private int _tutorialInfoId;
	private HashMap<Integer, Integer> _deliveredHistory;
	
	public DeliverableLauncher() {
		_deliveredHistory = new HashMap<Integer, Integer>();
	}
	
	/**
	 * Finds the easiest of the deliverables available corresponding to the tutorialId passes as parameter
	 * @param tutorialId Primary key of the {@link TutorialInfo}
	 * @return < id, Name> of the deliverable resource.
	 */
	public KeyValue<Integer,String> GetFirstDeliverableName() {
		Connection conn = null;
		String deliverableName = null;
		int deliverableId = -1;
		
		try {
			 conn = DBConnection.GetConnection();
			 String getFirstSql = "select deliverableName, deliverableId from DeliverableInfo " + 
			 	" where tutorialInfoId = ? " +
			 	" and deliverableType = ?";
			 PreparedStatement selectStmt = conn.prepareStatement(getFirstSql);
			 selectStmt.setInt(1, this._tutorialInfoId);
			 selectStmt.setString(2, "Example");
			 ResultSet rs = selectStmt.executeQuery();
			 if (rs.next()) {
				 deliverableName = rs.getString(1);
				 deliverableId = rs.getInt(2);
			 }
			 else {
				 getFirstSql += " and difficultyLevel = " +
				 		"(select min(difficultyLevel) from DeliverableInfo where tutorialInfoId = ?" +
				 		" and deliverableType = ?)";
				 selectStmt = conn.prepareStatement(getFirstSql);
				 selectStmt.setInt(1, this._tutorialInfoId);
				 selectStmt.setString(2, "Quiz");
				 selectStmt.setInt(3, this._tutorialInfoId);
				 selectStmt.setString(4, "Quiz");
				 
				 rs = selectStmt.executeQuery();
				 if (rs.next()) {
					 deliverableName = rs.getString(1);
					 deliverableId = rs.getInt(2);
				 }
				 else {
					 System.err.println("0 tuples for TutorialInfoId : " + this._tutorialInfoId);	 
				 }
			 }
			 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				DBConnection.CloseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new KeyValue<Integer, String>(deliverableId, deliverableName);
	}

	/**
	 * Saves the score for the corresponding deliverableId in the database
	 * and in the current object.
	 * @param deliverableId
	 * @param score
	 * @return primary key of the table Scores corresponding to the current entry.
	 */
	public int SetScore(int deliverableId, int score) {
		_deliveredHistory.put(deliverableId, score);
		Scores scores = new Scores();
		scores.setScore(score);
		scores.setDeliverableId(deliverableId);
		scores.setStudentId(_studentId);
		return ScoresStore.InsertScores(scores);
	}

	/**
	 * Depending upon the currently delivered resources, and the latest score, 
	 * decide the next deliverable.
	 * @return
	 */
	public KeyValue<Integer, String> GetNextDeliverableName(int deliverableId, int scoreId) {
		Connection conn = null;
		String deliverableName = null;
		KeyValue<Integer, String> keyVal = null;
		int score;
		if (!_deliveredHistory.containsKey(deliverableId)) {
			HashMap<String, String> whereClause = new HashMap<String, String>();
			whereClause.put("scoreId", Integer.toString(scoreId));
			score = (Integer) ScoresStore.Select("score", whereClause).get(0);
		}
		else {
			//TODO: Add code to delete log file of example.
			score = _deliveredHistory.get(deliverableId);
		}
		
		// if score is less than 50, then show an example that is not present in _delivered
		if (score <= 50) {
			try {
				 conn = DBConnection.GetConnection();
				 String getSql = "select deliverableName, deliverableId from DeliverableInfo " + 
				 	" where tutorialInfoId = ? " +
				 	" and deliverableType = ? ";
				 String lessThanSql = getSql + "and deliverableId <> ?";
				 PreparedStatement selectStmt = conn.prepareStatement(lessThanSql);
				 selectStmt.setInt(1, this._tutorialInfoId);
				 selectStmt.setString(2, "Example");
				 selectStmt.setInt(3, deliverableId);
				 ResultSet rs = selectStmt.executeQuery();
				 ArrayList<KeyValue<Integer, String>> doneExamples = new ArrayList<KeyValue<Integer,String>>();
				 while (rs.next()) {
					 deliverableName = rs.getString(1);
					 deliverableId = rs.getInt(2);
					 KeyValue<Integer, String> tempKeyVal = new KeyValue<Integer, String>(deliverableId, deliverableName);
					 if (!_deliveredHistory.containsKey(deliverableId)) {
						 keyVal = tempKeyVal;
						 break;
					 }
					 doneExamples.add(tempKeyVal);					 
				 }
				 if (keyVal == null) {
					 double rand = Math.random();
					 int goTo = (int) (doneExamples.size() * rand) % doneExamples.size();
					 keyVal = doneExamples.get(goTo);
				 }
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					DBConnection.CloseConnection(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// TODO: Direct to End page
			//if(deliverableName != "null"){
			return keyVal;
			//}
		}
		// if score is 100 (that means last deliverable was either example or successful quiz)
		//		then deliver quiz with difficulty level higher than current one. 
		else if (score >= 100) {
			try {
				conn = DBConnection.GetConnection();
				String getSql = "select deliverableName, deliverableId from DeliverableInfo "
						+ " where tutorialInfoId = ? "
						+ " and deliverableType = ? ";
				
				//TODO : order by difficultyLevel
				String nextSql = getSql
						+ "and difficultyLevel > "
						+ "(select difficultyLevel from DeliverableInfo where deliverableId = ?) order by difficultyLevel";
				PreparedStatement selectStmt = conn.prepareStatement(nextSql);
				selectStmt.setInt(1, this._tutorialInfoId);
				selectStmt.setString(2, "Quiz");
				selectStmt.setInt(3, deliverableId);

				ResultSet rs = selectStmt.executeQuery();
				while (rs.next()) {
					deliverableName = rs.getString(1);
					deliverableId = rs.getInt(2);
					if (!_deliveredHistory.containsKey(deliverableId)) {
						keyVal = new KeyValue<Integer, String>(deliverableId, deliverableName);
						break;
					}
				} 

			}
				 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					DBConnection.CloseConnection(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return keyVal;
		}
		// if 50 < score < 100 , 
		//		then deliver quiz with difficulty level same as current one, if not available then next level.
		else {
			try {
				 conn = DBConnection.GetConnection();
				 String getSql = "select deliverableName, deliverableId from DeliverableInfo " + 
				 	" where tutorialInfoId = ? " +
				 	" and deliverableType = ? ";
				 
				 String nextSql = getSql + "and deliverableId <> ?" + "and difficultyLevel >= " +
				 		"(select difficultyLevel from DeliverableInfo where deliverableId = ?) order by difficultyLevel";
				 PreparedStatement selectStmt = conn.prepareStatement(nextSql);
				 selectStmt.setInt(1, this._tutorialInfoId);
				 selectStmt.setString(2, "Quiz");
				 selectStmt.setInt(3, deliverableId);
				 selectStmt.setInt(4, deliverableId);
				 			 
				 ResultSet rs = selectStmt.executeQuery();
				 while (rs.next()) {
						deliverableName = rs.getString(1);
						deliverableId = rs.getInt(2);
						if (!_deliveredHistory.containsKey(deliverableId)) {
							keyVal = new KeyValue<Integer, String>(deliverableId, deliverableName);
							break;
						}
					} 
				 }
				 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					DBConnection.CloseConnection(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return keyVal;
		}
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
	 * @return the tutorialInfoId
	 */
	public int getTutorialInfoId() {
		return _tutorialInfoId;
	}

	/**
	 * @param tutorialInfoId the tutorialInfoId to set
	 */
	public void setTutorialInfoId(int tutorialInfoId) {
		_tutorialInfoId = tutorialInfoId;
	}
}
