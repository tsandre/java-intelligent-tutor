package itjava.tests;

import itjava.model.Convertor;

import org.junit.Before;
import org.junit.Test;

import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;
import static org.junit.Assert.*;

public class StringComparerTest {
	
	private float similarity;
	private Levenshtein metric;

	@Before
	public void setUp() {
		metric = new Levenshtein();
	}
	
	@Test
	public void TestSimpleStrings() {
		String string1 = "How are you dear Mister?";
		String string2 = "How are you deer Master?";
		similarity = metric.getSimilarity(string1, string2);
		System.out.println("Similarity is : " + similarity);
		assertTrue("simple similarity test", similarity > 0.9);
	}
	
	@Test
	public void TestStringFromFiles() {
		try {
			String string1 = Convertor.FileToString("samples/UseThisForTestingStringComparer_1.java");
			String string2 = Convertor.FileToString("samples/UseThisForTestingStringComparer_2.java");
			similarity = metric.getSimilarity(string1, string2);
			System.out.println("Similarity is : " + similarity);
			assertTrue("simple similarity test", similarity > 0.75);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
