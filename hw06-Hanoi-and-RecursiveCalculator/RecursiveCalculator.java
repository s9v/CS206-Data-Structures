package elice;

import java.util.Arrays;

public class RecursiveCalculator {
    String log = "";

    private class ParseResult {
        String[] parts;
        String separator;
    }

    // DO NOT EDIT calculate METHOD
    public double calculate(String input) {
//        if (input.charAt(0) == '(')
//            throw new RuntimeException(input);
        return parseExpr(input);
    }

    private ParseResult parseParts(String expr, String[] separators, boolean needFirst) {
        String[] symbols = expr.split("\\s");

        int left_brackets = 0;
        int idx = -1;
        String separator = null;
        String[] parts;

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i].equals("(")) {
                left_brackets++;
            } else if (symbols[i].equals(")")) {
                left_brackets--;
            } else if (Arrays.asList(separators).contains(symbols[i]) && left_brackets == 0) {
                idx = i;
                separator = symbols[i];
                
                if (needFirst)
                    break;
            }
        }

        ParseResult pr = new ParseResult();
        pr.separator = separator;

        if (separator == null) {
            parts = new String[]{expr};
        } else {
            parts = new String[2];

            parts[0] = "";
            for (int i = 0; i < idx; i++) {
                if (i != 0)
                    parts[0] += " ";
                parts[0] += symbols[i];
            }

            parts[1] = "";
            for (int i = idx + 1; i < symbols.length; i++) {
                if (i != idx + 1)
                    parts[1] += " ";
                parts[1] += symbols[i];
            }
        }

        pr.parts = parts;

        return pr;
    }

    public double parseExpr(String expr) {
        log += "expr:" + expr + "\n"; //DO NOT MODIFY THIS LINE

        ParseResult pr = parseParts(expr, new String[]{"+", "-"}, false);
        String parts[] = pr.parts;
        String separator = pr.separator;

        if (separator == null)
            return parseTerm(parts[0]);
        else {
            double a = parseExpr(parts[0]);
            double b = parseTerm(parts[1]);

            if (separator.equals("+"))
                return a + b;
            else
                return a - b;
        }
    }

    public double parseTerm(String term) {
        log += "term:" + term + "\n"; //DO NOT MODIFY THIS LINE

        ParseResult pr = parseParts(term, new String[]{"*", "/"}, false);
        String parts[] = pr.parts;
        String separator = pr.separator;

        if (separator == null)
            return parsePower(parts[0]);
        else {
            double a = parseTerm(parts[0]);
            double b = parsePower(parts[1]);

            if (separator.equals("*"))
                return a*b;
            else
                return a/b;
        }
    }

    public double parsePower(String power) {
        log += "power:" + power + "\n"; //DO NOT MODIFY THIS LINE

        ParseResult pr = parseParts(power, new String[]{"^"}, true);
        String parts[] = pr.parts;
        String separator = pr.separator;

        if (separator == null)
            return parseFactor(parts[0]);
        else {
            double a = parseFactor(parts[0]);
            double b = parsePower(parts[1]);

            return Math.pow(a, b);
        }
    }

    public double parseFactor(String factor) {
        log += "factor:" + factor + "\n"; //DO NOT MODIFY THIS LINE

        if (factor.charAt(0) == '(') {
            factor = factor.substring(2, factor.length()-2);
            return parseExpr(factor);
        }
        else
            return Double.parseDouble(factor);
    }
}
