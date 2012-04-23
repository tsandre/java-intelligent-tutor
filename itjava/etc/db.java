import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
public class Main {
  public static void main(  String[] args) throws Exception {
    Properties prop=new Properties();
    String fileName="app.config";
    InputStream is=new FileInputStream(fileName);
    prop.load(is);
    System.out.println(prop.getProperty("app.name"));
    System.out.println(prop.getProperty("app.version"));
    System.out.println(prop.getProperty("app.vendor","Java"));
  }
}
