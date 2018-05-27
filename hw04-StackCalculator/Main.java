package cs206b;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main {
  public static void main(String args[]) {

    /*
      
    You can do your own test in this file.
    When you press 'run' button, then this main function runs.

    This main function is just an example.
    Feel free to modify this function for your own test.

    */
    
//         Pattern OPERATOR = Pattern.compile("[*/+-]"); // \\[\\]\\{\\}\\(\\)
//         Pattern DOUBLE = Pattern.compile("[+-]?\\d+(.\\d+)*");
        
//         // String str = "( ( ( 1 + 2 ) * ( 3 / 4 ) ) - ( 5 * 6 ) )";
//         String str = "3.25 * 12 - 8 + -7";
//         Scanner scanner = new Scanner(str);
        
//         while (scanner.hasNext()) {
//             if (scanner.hasNext(OPERATOR)) {
//                 System.out.println(scanner.findInLine(OPERATOR));
//             }
//             else if (scanner.hasNext(UINT)) {
//                 System.out.println(scanner.findInLine(UINT));
//             }
//         }
    
    StackCalculator calc = new StackCalculator();
    String postfix;
        
    // String infix = "3.25 * 12 - 8 + -7";
        // String infix = "( ( ( 1 + 2 ) * ( 3 / 4 ) ) - ( 5 * 6 ) )";
        // String infix = "120 / 5 / 4 / 3 / 2 / 1";
        // String infix = "1 - 2 + 3";
        // String infix = "1 + 3 * 4";
        String infix = "1 * 3 + 4";
        ArrayList<String> tests = new ArrayList<String>();
        tests.add("1 * 3 + 4");
        tests.add("2 - 1 * 3 + 4");
        tests.add("( 2 - 1 ) * 3 + 4");
        tests.add("[ 2 - 1 ] * ( 3 + 4 )");
        tests.add("2 - ( 1 * 3 ) + 4");
        tests.add("( 2 - ( 1 * 3 ) + 4 )");
        tests.add("2 - ( 1 - 3 * 6 ) * 4 - 5");
        tests.add("{ 3 + ( 7 - [ 2 ) } * 3 ]");
        
    try {
            for (String test: tests) {
                postfix = calc.changeToPostfix(test);
          System.out.println("INFIX   --> " + test);
                System.out.println("POSTFIX --> " + postfix);
                System.out.println();
            }
            
      // System.out.println(calc.evaluate(postfix));
    }
    catch (UnbalancedParenthesisException e) {
      System.out.println(e.getMessage() + "UnbalancedParenthesisException!!!");
    }
//    catch (DividedByZeroException e) {
//      System.out.println(e.getMessage());
//    }
  }
}