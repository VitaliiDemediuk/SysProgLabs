package com.sysprog.lab4;

public class Console {
    static public class TokenPrinter {
        static void print(Token token)
        {
            System.out.print(TokenClass.unrecognizedLexeme.color);
            System.out.print(token.forwardInput);
            System.out.print(ConsoleColor.RESET);
            System.out.print(token.tokenClass.color);
            System.out.print(token.lexeme);
            System.out.print(ConsoleColor.RESET);
        }
    }
}
