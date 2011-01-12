  public void testThis(  ArrayList<Integer> intArr){
import java.util.*;

public class DemoList {

  public static void main(String[] args) {

    List ls = new LinkedList();

    for(int i=1; i<=5; i++){
      ls.add(new StringBuffer("Object " + i));
    }

    //display how many objects are in the collection
    System.out.println("The collection has " + ls.size() + "objects");

    //Instantiate a ListIterator
    ListIterator li = ls.listIterator();
    System.out.println("Forward Reading");

    //Forward direction
    while(li.hasNext()){
      System.out.println(" " + li.next());
    }

    System.out.println("Backward Reading");

    //backword direction
    while(li.hasPrevious()){
      System.out.println(" " + li.previous());
    }
  }

}