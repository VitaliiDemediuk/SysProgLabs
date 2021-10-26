package com.sysprog.lab3;

import java.util.regex.Pattern;

public class TokenPattern {
    final String id;
    final Pattern pattern;
    final ConsoleColor color;

    TokenPattern(String aId, String aPatternString, ConsoleColor aColor)
    {
        id = aId;
        pattern = Pattern.compile(aPatternString);
        color = aColor;
    }
}
