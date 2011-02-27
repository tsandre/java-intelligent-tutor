package itjava.model;

import itjava.data.BlankType;
import itjava.db.DBConnection;
import itjava.util.Concordance;
import itjava.util.WordInfoComparator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class WordInfoStore {
	private List<String> _linesOfCode;
	private WordInfo _wordInfo;
	private boolean restrictDuplicates;
	
	private WordInfoStore(List<String> linesOfCode, boolean dup) {
		_linesOfCode = linesOfCode;
		_wordInfo = new WordInfo();
		restrictDuplicates = dup;
	}

	private void createWordInfo(ASTNode node, Set<Integer> lineNumbersUsed) throws Exception {
		int index = 0;
		int lineNumber = 0;
		for (String currLine : _linesOfCode) {
			index += currLine.length();
			// Replace lines from here to next comment with the single line that is 
			// commented above if too many exceptions related to "word not accessible"
			// are thrown.
		/*	int currLengthWithXtraSpace = currLine.length();
			int numOfIndentSpaces = 0;
			Pattern pattern = Pattern.compile("\\S");
			Matcher matcher = pattern.matcher(currLine);
			if (matcher.find()) {
				numOfIndentSpaces = matcher.start();
			}
			index += currLengthWithXtraSpace - (numOfIndentSpaces / 2);*/
			//End of change. 
			if (index > node.getStartPosition()) {
				//TODO Bug: when the wordToBeBlanked was "close", for the function .close();
				//It was found that in line closeable.close(), the first 4 letters of closeable were blanked out since that was the indexOf("close").
				_wordInfo.lineNumber = lineNumber + 1;
				if (lineNumbersUsed.contains(_wordInfo.lineNumber) && this.restrictDuplicates) {
					throw new Exception("Line number repeated..");
				}
				else {
					lineNumbersUsed.add(_wordInfo.lineNumber);
				}
				_wordInfo.blankType = BlankType.Text;
//				_wordInfo.columnNumber = currLine.trim().indexOf(_wordInfo.wordToBeBlanked);
				_wordInfo.columnNumber = (node.getStartPosition()) - (index - currLine.length());
				break;
			}
			lineNumber++;
		}
		if (_wordInfo.columnNumber == -1 ) {
			throw new Exception("Word: " + _wordInfo.wordToBeBlanked + " not accessible");
		}
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode, Statement statement, boolean restrictDup) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode, restrictDup);
		wordInfoStore._wordInfo.wordToBeBlanked = ((VariableDeclarationStatement)statement).getType().toString();
		wordInfoStore.createWordInfo(statement, null);
		return wordInfoStore._wordInfo;
	}

	public static WordInfo createWordInfo(List<String> linesOfCode,
			Name importDeclaration, Set<Integer> lineNumbersUsed, boolean restrictDup) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode, restrictDup);
		wordInfoStore._wordInfo.wordToBeBlanked = importDeclaration.getFullyQualifiedName();
		wordInfoStore.createWordInfo(importDeclaration, lineNumbersUsed);
		return wordInfoStore._wordInfo;
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode,
			Type classInstanceType, Set<Integer> lineNumbersUsed, boolean restrictDup) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode, restrictDup);
		wordInfoStore._wordInfo.wordToBeBlanked = classInstanceType.toString();
		wordInfoStore.createWordInfo(classInstanceType, lineNumbersUsed);
		return wordInfoStore._wordInfo;
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode,
			SimpleName methodInvocation, Set<Integer> lineNumbersUsed, boolean restrictDup) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode, restrictDup);
		wordInfoStore._wordInfo.wordToBeBlanked = methodInvocation.getFullyQualifiedName();
		wordInfoStore.createWordInfo(methodInvocation, lineNumbersUsed);
		return wordInfoStore._wordInfo;
	}


	public static Concordance<String> SelectWordInfo(HashMap<String, Integer> whereClause){
		Connection conn = null;
		Concordance<String> retVal = new Concordance<String>();
		try {
			conn = DBConnection.GetConnection();
			String selectSql = "select blankName, numOfHints from WordInfo";
			if (whereClause != null) {
			if (!whereClause.isEmpty()) {
				selectSql += " where ";
				for (Entry<String, Integer> entry: whereClause.entrySet()) {
					selectSql += entry.getKey() + "=" + entry.getValue();
				}
			}
			}
			PreparedStatement selectStmt = conn.prepareStatement(selectSql);
			ResultSet rs = selectStmt.executeQuery();
			while (rs.next()) {
				retVal.put(rs.getString(1), rs.getInt(2));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBConnection.CloseConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return retVal;
	}

	public static int addWordInfoToList(ArrayList<WordInfo> wordInfoList,
			WordInfo newWordInfo, LinkedHashSet<String> selectedWordInfoIndices) {
		int index = 0;
		boolean found = false;
		Iterator<WordInfo> itWIList = wordInfoList.iterator();
		int newLineNumber = newWordInfo.lineNumber;
		while (itWIList.hasNext()) {
			WordInfo currWordInfo = itWIList.next();
			if (currWordInfo.lineNumber < newLineNumber) {
				//do nothing
			}
			else if (currWordInfo.lineNumber == newLineNumber) {
				itWIList.remove();
				found = true;
				break;
			}
			else if (currWordInfo.lineNumber > newLineNumber) {
				found = true;
				break;
			}
			index++;
		}
		if (found) {
			Iterator<String> itSelectedWords = selectedWordInfoIndices.iterator();
			ArrayList<String> incrIndices = new ArrayList<String>();
			int selectedWordCount = 0;
			while (itSelectedWords.hasNext()) {
				int currIndex = Integer.parseInt(itSelectedWords.next());
				if(currIndex >= index) {
					incrIndices.add(Integer.toString(currIndex + 1));
					itSelectedWords.remove();
				}
				selectedWordCount++;
			}
			selectedWordInfoIndices.add(Integer.toString(index));
			selectedWordInfoIndices.addAll(incrIndices);
			wordInfoList.add(index, newWordInfo);
		}
		else {
			wordInfoList.add(newWordInfo);
			selectedWordInfoIndices.add(Integer.toString(index));
		}		
		return index;
	}
}
