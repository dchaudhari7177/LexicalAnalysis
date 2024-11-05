from lexer import lexer, validate_syntax

def main():
    print("Enter Java code (press Ctrl+D or Ctrl+Z on a new line to finish):")
    code = []
    while True:
        try:
            line = input()
            code.append(line)
        except EOFError:
            break
    code = "\n".join(code)

    lexer.input(code)

    tokens = []
    for token in lexer:
        tokens.append(token)

    print("\nTokens:")
    for token in tokens:
        print(f"Type: {token.type}, Value: '{token.value}', Line: {token.lineno}, Position: {token.lexpos}")

    # Validate syntax
    if validate_syntax(tokens):
        print("Syntax is valid.")
    else:
        print("Syntax is invalid.")

if __name__ == "__main__":
    main()
