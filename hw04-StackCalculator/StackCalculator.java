package cs206b;

import java.util.Scanner;
import java.util.regex.Pattern;


public class StackCalculator {
    private static Pattern OPERATOR = Pattern.compile("[*/+-]");
    private static Pattern LOW_OPERATOR = Pattern.compile("[+-]");
    private static Pattern HIGH_OPERATOR = Pattern.compile("[*/]");
    private static Pattern BRACKET = Pattern.compile("[\\[\\{\\(\\]\\}\\)]");
    private static Pattern LEFT_BRACKET = Pattern.compile("[\\[\\{\\(]");
    private static Pattern RIGHT_BRACKET = Pattern.compile("[\\]\\}\\)]");
    private static Pattern DOUBLE = Pattern.compile("[+-]?\\d+(\\.\\d+)*");
    
    public StackCalculator() {
    
  }
    
  public String changeToPostfix(String infixExpression) throws UnbalancedParenthesisException {
    LinkedStack<String> stack = new LinkedStack<String>();
        Scanner scanner = new Scanner(infixExpression);
        String result = "";
        
        while (scanner.hasNext()) {
            if (scanner.hasNext(LEFT_BRACKET)) {
                String next = scanner.findInLine(LEFT_BRACKET);
                stack.push(next);
            }
            else if (scanner.hasNext(DOUBLE)) {
                String next = scanner.findInLine(DOUBLE);
                
                if (result != "")
                    result += " ";
                result += next;
            }
            else if (scanner.hasNext(OPERATOR)) {
                while ( ! stack.isEmpty() && ! stack.peek().matches(LEFT_BRACKET.toString())
                    && ! ( scanner.hasNext(HIGH_OPERATOR) && stack.peek().matches(LOW_OPERATOR.toString()) ) ) {
                    result += " " + stack.pop();
                }
                
                // if ( scanner.hasNext(HIGH_OPERATOR) && stack.peek().matches(LOW_OPERATOR.toString()) ) {
                //     Double d = new Double(infixExpression);
                // }
                
                String next = scanner.findInLine(OPERATOR);
                stack.push(next);
            }
            else if (scanner.hasNext(RIGHT_BRACKET)) {
                String next = scanner.findInLine(RIGHT_BRACKET);
                
                while ( ! stack.isEmpty() && ! stack.peek().matches(LEFT_BRACKET.toString())) {
                    result += " " + stack.pop();
                }
                
                String prev = stack.pop();
                
                if ( ( prev.equals("(") && ! next.equals(")") ) || ( prev.equals("{") && ! next.equals("}") )
                    || ( prev.equals("[") && ! next.equals("]") ) ) {
                    throw new UnbalancedParenthesisException();
                }
            }
        }
        
        while ( ! stack.isEmpty() && ! stack.peek().matches(BRACKET.toString()) ) {
            result += " " + stack.pop();
        }
        
        if (stack.size() > 0) {
            throw new UnbalancedParenthesisException();
        }
        
        return result;
  }
    
  public double evaluate(String postfixExpression) throws DividedByZeroException {
    LinkedStack<Double> numbers = new LinkedStack<Double>();
        Scanner scanner = new Scanner(postfixExpression);
        
        while (scanner.hasNext()) {
            if (scanner.hasNext(DOUBLE)) {
                String next = scanner.findInLine(DOUBLE);
                numbers.push(new Double(next));
            }
            else if (scanner.hasNext(OPERATOR)) {
                String next = scanner.findInLine(OPERATOR);
                Double b = numbers.pop();
                Double a = numbers.pop();
                Double c = null;
                
                switch (next) {
                    case "/":
                        if (b == 0)
                            throw new DividedByZeroException();
                        c = a / b;
                        break;
                    case "*":
                        c = a * b;
                        break;
                    case "+":
                        c = a + b;
                        break;
                    case "-":
                        c = a - b;
                        break;
                }
                
                numbers.push(c);
            }
        }
        
    return numbers.pop();
  }
}
