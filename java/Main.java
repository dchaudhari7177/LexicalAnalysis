import java.util.List;

public class Main {
    public static void main(String[] args) {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();

        String code = "void myMethod() { if (a > b) { a = b + 10; } else { a = b * 2; } for (int i = 0; i < 10; i++) { sum += i; } while (sum < 100) { sum += 5; } }";

        List<Token> tokens = lexicalAnalyzer.analyze(code);

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
