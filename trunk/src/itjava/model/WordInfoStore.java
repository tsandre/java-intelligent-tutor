package itjava.model;

import java.util.List;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class WordInfoStore {
	
	public static WordInfo createWordInfo(List<String> linesOfCode, Statement statement) {
		WordInfo wordInfo = new WordInfo();
		wordInfo.wordToBeBlanked = ((VariableDeclarationStatement)statement).getType().toString();
		System.out.println("VariableDeclaration pos: " + statement.getStartPosition());
		return wordInfo;
	}

	public static WordInfo createWordInfo(List<String> linesOfCode,
			ImportDeclaration importDeclaration) {
		WordInfo wordInfo = new WordInfo();
		wordInfo.wordToBeBlanked = importDeclaration.getName().getFullyQualifiedName();
		System.out.println("ImportDeclaration pos: " + importDeclaration.getStartPosition());
		return wordInfo;
	}
	public static WordInfo createWordInfo(List<String> linesOfCode,
			Expression initializer) {
		WordInfo wordInfo = new WordInfo();
		wordInfo.wordToBeBlanked = initializer.toString();
		System.out.println("Initializer pos: " + initializer.getStartPosition());
		return wordInfo;
	}
	public static WordInfo createWordInfo(List<String> linesOfCode,
			SimpleName methodInvocation) {
		WordInfo wordInfo = new WordInfo();
		wordInfo.wordToBeBlanked = methodInvocation.getFullyQualifiedName();
		System.out.println("Initializer pos: " + methodInvocation.getStartPosition());
		return wordInfo;
	}
}
