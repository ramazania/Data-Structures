/**
 * Name: Ali Ramazani
 * Email: ramazania@carleton.edu
 * Partner's Name: Elise Khutsen
 * Partner's Email: knutsene@carleton.edu
 * Description: Modifying the interpreter class to implement PostScript commands.
 * Error: Null Pointer Excenption for the commandIf method. 
 */
import java.util.ArrayDeque;
public class Interpreter {

    private SymbolTable table; // PostScript symbol table
    private ArrayDeque<Token> stack; // PostScript operand stack

    /**
     * Constructs a new PostScript Interpreter
     */
    public Interpreter() {
        table = new SymbolTable();
        stack = new ArrayDeque<Token>();
    }

    /**
     * Iterates over the Tokens in reader until they have all been processed or a
     * symbol token with the value "quit" is encountered
     */
    public void interpret(Reader reader) {
        // use reader as an iterator over tokens to process
        while (reader.hasNext()) {
            Token t = reader.next();
            // only check for quit if token is a symbol:
            if (t.isSymbol() && t.getSymbol().equals("quit")) {
                break; // break statement exits the current loop
            }
            // process token
            processToken(t);
        }
    }
    /**
     * Process the token and perform the appropriate action
     */
    public void processToken(Token token) {
        switch (token.kind()) {
            case Token.BOOLEAN_KIND:
            case Token.NUMBER_KIND:
            case Token.PROCEDURE_KIND:
                stack.push(token);
                break;
            case Token.SYMBOL_KIND:
                switch (token.getSymbol()) {
                    case "pstack":
                        pstack();
                        break;
                    case "pop":
                        pop();
                        break;
                    case "add":
                        add();
                        break;
                    case "sub":
                        sub();
                        break;
                    case "div":
                        div();
                        break;
                    case "mul":
                        mul();
                        break;
                    case "gt":
                        gt();
                        break;
                    case "lt":
                        lt();
                        break;
                    case "eq":
                        eq();
                        break;
                    case "ne":
                        ne();
                        break;
                    case "dup":
                        dup();
                        break;
                    case "exch":
                        exch();
                        break;
                    case "def":
                        def();
                        break;
                    case "If":
                       commandIf();
                        break;
                    default:
                    process(token);
                    break;
            }
        } 
    }       
    // Pops a boolean and a procedure token and performs actions on them. 
    private void commandIf() {
        Token first = stack.pop();
        Token second = stack.pop();
            if (second.getBoolean()) {
                if (first.isProcedure()) {
                    for (Token x : first.getProcedure()) {
                        processToken(x); 
                    }
                }
                else {
                    processToken(first);
                    
                    }
                }
            }
        
    // will process tokens for def or procedure.
        private void process(Token token) {
            if (token.getSymbol().startsWith("/")) {
                stack.push(token); 
            }
            else {
                Token newToken = table.get(token.getSymbol());
                if (newToken.isProcedure()) {
                    for (Token x : newToken.getProcedure()) {
                        processToken(x); 
                    }
                }
                else  
                {
                    processToken(newToken);
                }
            }
        }

    // Takes the key and the value and use them to define a token and add to the table.
    private void def() {
        Token value = stack.pop();
        Token key = stack.pop();
        table.add(key.getSymbol().substring(1), value);
    }
    // exchanges first and second token.
    private void exch() {
        Token first = stack.pop();
        Token second = stack.pop();
        stack.push(first);
        stack.push(second);

    }

    // duplicates the first token and pushes both back to the stack.
    private void dup() {
        Token first = stack.pop();
        Token second = first;
        stack.push(first);
        stack.push(second);
    }

    // compares two tokens and determines which one is greater.
    private void gt() {
        Token first = stack.pop();
        Token second = stack.pop();
        boolean result = second.getNumber() > first.getNumber();
        stack.push(new Token(result));
    }

    // compares two tokens and determines which one is smaller.
    private void lt() {
        Token first = stack.pop();
        Token second = stack.pop();
        boolean result = second.getNumber() < first.getNumber();
        stack.push(new Token(result));

    }

    // compares two tokens and determines if they are equal.
    private void eq() {
        Token first = stack.pop();
        Token second = stack.pop();
        boolean result = second.getNumber() == first.getNumber();
        stack.push(new Token(result));
    }

    // compares two tokens and determines if they are not equal.
    private void ne() {
        Token first = stack.pop();
        Token second = stack.pop();
        boolean result = second.getNumber() != first.getNumber();
        stack.push(new Token(result));
    }

    // Multiplies the first poped element by the second popped elements
    private void mul() {
        Token first = stack.pop();
        Token second = stack.pop();
        double result = second.getNumber() * first.getNumber();
        stack.push(new Token(result));
    }

    // dividess the first popped element by second popped element
    private void div() {
        Token first = stack.pop();
        Token second = stack.pop();
        double result = second.getNumber() / first.getNumber();
        stack.push(new Token(result));
    }

    // Subtracts first popped element from second popped element
    private void sub() {
        Token first = stack.pop();
        Token second = stack.pop();
        double result = second.getNumber() - first.getNumber();
        stack.push(new Token(result));
    }

    // adds together first 2 popped elements
    private void add() {
        Token first = stack.pop();
        Token second = stack.pop();
        double result = second.getNumber() + first.getNumber();
        stack.push(new Token(result));

    }

    // pops the tokens in order to perfom and operation
    private void pop() {
        stack.pop();
    }

    // print out tokens on the stack.
    private void pstack() {
        for (Token t : stack) {
            System.out.println(t);
        }

    }

    public static void main(String[] args) {
        Reader r = new Reader();
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(r);
    }
}