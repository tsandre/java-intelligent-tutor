import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    String data = "<root><row><column name='username' length='6'>admin</column>"
        + "<column name='password' length='1'>p</column></row><row>"
        + "<column name='username' length='6'>j</column>"
        + "<column name='password' length='8'>q</column></row></root>";

    SAXBuilder builder = new SAXBuilder();
    Document document = builder.build(new ByteArrayInputStream(data.getBytes()));

    Element root = document.getRootElement();

    List rows = root.getChildren("row");
    for (int i = 0; i < rows.size(); i++) {
      Element row = (Element) rows.get(i);
      List columns = row.getChildren("column");
      for (int j = 0; j < columns.size(); j++) {
        Element column = (Element) columns.get(j);
        String name = column.getAttribute("name").getValue();
        String value = column.getText();
        int length = column.getAttribute("length").getIntValue();

        System.out.println("name = " + name);
        System.out.println("value = " + value);
        System.out.println("length = " + length);
      }
    }
  }
}