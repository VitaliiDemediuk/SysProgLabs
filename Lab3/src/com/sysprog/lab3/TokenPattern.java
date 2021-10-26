package com.sysprog.lab3;

import java.util.regex.Pattern;

public class TokenPattern {
    final Pattern pattern;
    final ConsoleColor color;

    TokenPattern(String aPatternString, ConsoleColor aColor)
    {
        pattern = Pattern.compile(aPatternString);
        color = aColor;
    }
}
