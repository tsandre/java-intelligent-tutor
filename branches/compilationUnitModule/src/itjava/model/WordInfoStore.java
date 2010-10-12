package itjava.model;

import java.util.List;

import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class WordInfoStore {
	private static WordInfo _wordInfo;
	
	public WordInfoStore() {
		_wordInfo = new WordInfo();
	}
	
	public static WordInfo createWordInfo(List<String> linesOfCode, Statement statement) {
		System.out.println(statement.getStartPosition());
		return _wordInfo;
	}
}
