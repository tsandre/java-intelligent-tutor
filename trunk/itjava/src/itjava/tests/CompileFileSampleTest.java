package itjava.tests;
import javax.tools.*;
public class CompileFileSampleTest {
  public static void main(String[] args) {
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    int result = compiler.run(null, null, null, "generated/testClassName.java");
    System.out.println("Compile result code = " + result);
  }
}

