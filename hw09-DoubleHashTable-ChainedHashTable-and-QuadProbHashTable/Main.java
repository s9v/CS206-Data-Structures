package elice;

import java.io.*;
import java.util.Scanner;

public class Main {
  private static final int M = 11;  // a prime number
  
  public static void main(String args[]) {

    /*
      
    You can do your own test in this file.
    When you press 'run' button, then thisi main function runs.

    */
    System.out.println("Hello World!");
    sampleTest();

  }


  public static void sampleTest()
  {
    //This is example code of how you can test your code.
      String[] list = new String[M];
        Scanner datafile;
        try {
          datafile = new Scanner(new FileReader("student_test.txt"));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
          return;
        }

        // it is assumed that the data file contains at least M items.
        try {
          for(int i = 0; i < M; i++){
            list[i] = datafile.next();
          }
        } catch (Exception e) {
          e.printStackTrace();
          return;
        }

        CHTable<String, String> table = new CHTable<String, String>(M);
        for(int i = 0; i < M; i++){
            table.put(list[i], list[i]);
        }

        table.get(list[M-1]);
        
        int v = table.visits;

        System.out.printf("visits: %d\n", v);

  }

}