package itjava.tests;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class JSoupTest {

	@Test
	public void SimpleParse() {
		try {
			Document doc = Jsoup.connect("http://www.kodejava.org/examples/241.html")
			  .userAgent("Mozilla")
			  .cookie("auth", "token")
			  .timeout(3000)
			  .post();
			//Document doc = Jsoup.parse(new File("C:/Users/Aniket/Desktop/java.util.Scanner.htm"), "UTF-8");
			System.out.print(doc.text());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
