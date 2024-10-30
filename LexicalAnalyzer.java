import java.util.*;

public class LexicalAnalyzer {
    private static final Set<String> keywords = new HashSet<>(Arrays.asList(
            "if", "else", "for", "while", "do", "return", "void", "int", "double", "float", "boolean", "char"
    ));

    private static final Set<Character> symbols = new HashSet<>(Arrays.asList(
            '(', ')', '{', '}', ';', '+', '-', '*', '/', '=', '<', '>', '!', ','
    ));

    public List<Token> analyze(String code) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        char[] characters = code.toCharArray();

        for (int i = 0; i < characters.length; i++) {
            char c = characters[i];

            if (Character.isWhitespace(c)) {
                continue;
            }

            if (Character.isLetter(c)) {
                buffer.setLength(0); 
                while (i < characters.length && (Character.isLetterOrDigit(characters[i]) || characters[i] == '_')) {
                    buffer.append(characters[i++]);
                }
                i--; 

                String word = buffer.toString();
                if (keywords.contains(word)) {
                    tokens.add(new Token("KEYWORD", word));
                    if (isMethodDefinition(word, characters, i)) {
                        tokens.add(new Token("METHOD_DEFINITION", "Method Definition"));
                    }
                } else {
                    tokens.add(new Token("IDENTIFIER", word));
                }
            }

            else if (Character.isDigit(c)) {
                buffer.setLength(0); 
                while (i < characters.length && Character.isDigit(characters[i])) {
                    buffer.append(characters[i++]);
                }
                i--; 
                tokens.add(new Token("NUMBER", buffer.toString()));
            }

            else if (symbols.contains(c)) {
                tokens.add(new Token("SYMBOL", String.valueOf(c)));
            }

            else if (c == '"') {
                buffer.setLength(0);
                buffer.append(c);
                i++;
                while (i < characters.length && characters[i] != '"') {
                    buffer.append(characters[i++]);
                }
                buffer.append(characters[i]); 
                tokens.add(new Token("STRING_LITERAL", buffer.toString()));
            }

            else if (c == 'i' && characters.length > i + 1 && characters[i + 1] == 'f') {
                tokens.add(new Token("CONDITIONAL", "if"));
                i++;
            } else if (c == 'e' && characters.length > i + 3 && characters[i + 1] == 'l' && characters[i + 2] == 's' && characters[i + 3] == 'e') {
                tokens.add(new Token("CONDITIONAL", "else"));
                i += 3;
            }

            else if (c == 'f' && characters.length > i + 2 && characters[i + 1] == 'o' && characters[i + 2] == 'r') {
                tokens.add(new Token("LOOP", "for"));
                i += 2;
            } else if (c == 'w' && characters.length > i + 4 && characters[i + 1] == 'h' && characters[i + 2] == 'i' && characters[i + 3] == 'l' && characters[i + 4] == 'e') {
                tokens.add(new Token("LOOP", "while"));
                i += 4;
            } else if (c == 'd' && characters.length > i + 1 && characters[i + 1] == 'o') {
                tokens.add(new Token("LOOP", "do"));
                i++;
            }
        }

        return tokens;
    }

    private boolean isMethodDefinition(String word, char[] characters, int index) {
        int i = index + 1;
        while (i < characters.length && (Character.isWhitespace(characters[i]) || characters[i] == '(')) {
            if (characters[i] == '(') {
                return true; 
            }
            i++;
        }
        return false;
    }
}
