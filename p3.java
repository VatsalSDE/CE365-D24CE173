import java.io.*;
import java.util.*;

public class p3 {
    
    static HashSet<String> keywords = new HashSet<>(Arrays.asList(
        "auto", "break", "case", "char", "const", "continue", "default", "do", "double", "else", "enum", "extern",
        "float", "for", "goto", "if", "inline", "int", "long", "register", "restrict", "return", "short", "signed",
        "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while", "printf", "main"
    ));
    
    static ArrayList<String> symbolTable = new ArrayList<>();
    static ArrayList<String> lexicalErrors = new ArrayList<>();
    
    static boolean isKeyword(String str) {
        return keywords.contains(str);
    }
    
    static boolean isIdentifierChar(char ch, boolean isStart) {
        return isStart ? (Character.isLetter(ch) || ch == '_') : (Character.isLetterOrDigit(ch) || ch == '_');
    }
    
    static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=' || ch == '%' || ch == '>' || ch == '<';
    }
    
    static boolean isPunctuation(char ch) {
        return ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == ';' || ch == ',' || ch == '"' || ch == '[' || ch == ']';
    }
    
    static void processIdentifier(String word) {
        if (isKeyword(word))
            return; // Don't add keywords to symbol table
        if (!symbolTable.contains(word))
            symbolTable.add(word);
    }
    
    public static void main(String[] args) {
        try {
            BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\Vatsal\\Desktop\\College Sem 6\\Compiler Construction\\input.c"));
            
            String line;
            int tokenCount = 0;
            
            while ((line = file.readLine()) != null) {
                StringBuilder word = new StringBuilder();
                boolean inString = false;
                
                for (int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);
                    
                    if (inString) {
                        word.append(ch);
                        if (ch == '"') {
                            inString = false;
                            System.out.println("String: " + word);
                            tokenCount++;
                            word.setLength(0);
                        }
                        continue;
                    }
                    
                    if (Character.isWhitespace(ch) || isPunctuation(ch) || isOperator(ch)) {
                        if (word.length() > 0) {
                            if (Character.isDigit(word.charAt(0))) {
                                boolean isValidConstant = true;
                                for (int j = 0; j < word.length(); j++) {
                                    char c = word.charAt(j);
                                    if (!Character.isDigit(c) && c != '.' && c != 'e' && c != '-') {
                                        isValidConstant = false;
                                        break;
                                    }
                                }
                                if (!isValidConstant) {
                                    lexicalErrors.add(word.toString());
                                } else {
                                    System.out.println("Constant: " + word);
                                    tokenCount++;
                                }
                            } else if (isIdentifierChar(word.charAt(0), true)) {
                                processIdentifier(word.toString());
                                System.out.println("Identifier: " + word);
                                tokenCount++;
                            }
                            word.setLength(0);
                        }
                        
                        if (ch == '"') {
                            inString = true;
                            word.append(ch);
                        } else if (isOperator(ch)) {
                            System.out.println("Operator: " + ch);
                            tokenCount++;
                        } else if (isPunctuation(ch)) {
                            System.out.println("Punctuation: " + ch);
                            tokenCount++;
                        }
                    } else {
                        word.append(ch);
                    }
                }
                
                if (word.length() > 0 && !inString) {
                    if (Character.isDigit(word.charAt(0))) {
                        boolean isValidConstant = true;
                        for (int j = 0; j < word.length(); j++) {
                            char c = word.charAt(j);
                            if (!Character.isDigit(c) && c != '.' && c != 'e' && c != '-') {
                                isValidConstant = false;
                                break;
                            }
                        }
                        if (!isValidConstant) {
                            lexicalErrors.add(word.toString());
                        } else {
                            System.out.println("Constant: " + word);
                            tokenCount++;
                        }
                    } else if (isIdentifierChar(word.charAt(0), true)) {
                        processIdentifier(word.toString());
                        System.out.println("Identifier: " + word);
                        tokenCount++;
                    }
                }
            }
            
            file.close();
            
            System.out.println("\nTotal Tokens: " + tokenCount);
            
            System.out.println("\nSymbol Table Entries:");
            for (String symbol : symbolTable)
                System.out.println(symbol);
            
            System.out.println("\nLexical Errors:");
            for (String error : lexicalErrors)
                System.out.println(error);
                
        } catch (IOException e) {
            System.out.println("Failed to open input file.");
            e.printStackTrace();
        }
    }
}