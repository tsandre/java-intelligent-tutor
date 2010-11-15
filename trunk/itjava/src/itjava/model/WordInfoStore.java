package itjava.model;

import itjava.data.BlankType;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class WordInfoStore {
	private List<String> _linesOfCode;
	private WordInfo _wordInfo;
	
	private WordInfoStore(List<String> linesOfCode) {
		_linesOfCode = linesOfCode;
		_wordInfo = new WordInfo();
	}

	private void createWordInfo(ASTNode node) throws Exception {
		int index = 0;
		int lineNumber = 0;
		for (String currLine : _linesOfCode) {
			index += currLine.length();
			if (index > node.getStartPosition()) {
				//TODO Bug: when the wordToBeBlanked was "close", for the function .close();
				//It was found that in line closeable.close(), the first 4 letters of closeable were blanked out since that was the indexOf("close").
				_wordInfo.lineNumber = lineNumber + 1;
				_wordInfo.blankType = BlankType.Text;
				_wordInfo.columnNumber = currLine.trim().indexOf(_wordInfo.wordToBeBlanked);
				break;
			}
			lineNumber++;
		}
		if (_wordInfo.columnNumber == -1 || _wordInfo.lineNumber == -1) {
			throw new Exception("Word: " + _wordInfo.wordToBeBlanked + " not accessible");
		}
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode, Statement statement) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = ((VariableDeclarationStatement)statement).getType().toString();
		wordInfoStore.createWordInfo(statement);
		return wordInfoStore._wordInfo;
	}

	public static WordInfo createWordInfo(List<String> linesOfCode,
			ImportDeclaration importDeclaration) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = importDeclaration.getName().getFullyQualifiedName();
		wordInfoStore.createWordInfo(importDeclaration);
		return wordInfoStore._wordInfo;
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode,
			Type classInstanceType) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = classInstanceType.toString();
		wordInfoStore.createWordInfo(classInstanceType);
		return wordInfoStore._wordInfo;
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode,
			SimpleName methodInvocation) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = methodInvocation.getFullyQualifiedName();
		wordInfoStore.createWordInfo(methodInvocation);
		return wordInfoStore._wordInfo;
	}

	public static WordInfo createWordInfo(ArrayList<String> linesOfCode,
			FieldDeclaration fieldDeclaration) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = ((FieldDeclaration)fieldDeclaration).getType().toString();
		wordInfoStore.createWordInfo(fieldDeclaration);
		return wordInfoStore._wordInfo;
	}

	public static WordInfo createWordInfo(ArrayList<String> linesOfCode,
			QualifiedName currPropertyAssignment) throws Exception {
		WordInfoStore wordInfoStore = new WordInfoStore(linesOfCode);
		wordInfoStore._wordInfo.wordToBeBlanked = currPropertyAssignment.getName().toString();
		wordInfoStore.createWordInfo(currPropertyAssignment);
		return wordInfoStore._wordInfo;
	}
}
