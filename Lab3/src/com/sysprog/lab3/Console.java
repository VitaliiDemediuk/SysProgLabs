package com.sysprog.lab3;

public class Console {
    static public class TokenPrinter {
        static void print(Token token)
        {
            System.out.print(TokenPattern.unrecognizedLexeme.color);
            System.out.print(token.forwardInput);
            System.out.print(ConsoleColor.RESET);
            System.out.print(token.pattern.color);
            System.out.print(token.lexeme);
            System.out.print(ConsoleColor.RESET);
        }
    }
}
