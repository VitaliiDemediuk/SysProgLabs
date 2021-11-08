package com.sysprog.lab3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    private static Path getFilePath(String[] args, int idx) throws IllegalArgumentException
    {
        String arg;
        if (!(args.length == 0 || args.length == 2) || idx >= 2) {
            throw new IllegalArgumentException("");
        }

        if (args.length != 0) {
            arg = args[idx];
        } else {
            Scanner scanner = new Scanner(System.in);
            arg = scanner.nextLine();
        }

        return FileSystems.getDefault().getPath(arg).toAbsolutePath();
    }

    private static RegexTokenizer readRegexTokenizer(Path filePath) throws FileNotFoundException, JSONException
    {
        final RegexTokenizer tokenizer;
        FileReader fr = new FileReader(filePath.toString()); // hard code
        JSONTokener tokener = new JSONTokener(fr);
        JSONObject root = new JSONObject(tokener);

        final var keyWords = new HashSet<String>();
        ((JSONArray)root.get("key_words")).forEach(ob -> keyWords.add((String)ob) );

        tokenizer = new RegexTokenizer(keyWords);

        ((JSONArray)root.get("lexemes")).forEach(ob -> {
            final var lexemeInfo = (JSONObject)ob;
            final String name = lexemeInfo.getString("name");
            final String regex = lexemeInfo.getString("regex");
            final ConsoleColor color = ConsoleColor.fromString(lexemeInfo.getString("color"));
            tokenizer.addPattern(new TokenPattern(name, regex, color));
        });

        return tokenizer;
    }

    public static void main(String[] args)
    {
        final Path lexemesPath;
        final Path filePath;
        try {
//            lexemesPath = FileSystems.getDefault().getPath("lexemes.json").toAbsolutePath();
//            filePath = FileSystems.getDefault().getPath("test.c").toAbsolutePath();
            lexemesPath = getFilePath(args, 0);
            filePath = getFilePath(args, 1);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getLocalizedMessage());
            System.exit(1);
            return;
        }

        try {
            final RegexTokenizer tokenizer = readRegexTokenizer(lexemesPath);
            final String code = Files.readString(Paths.get(filePath.toString()));
            for (var token : tokenizer.getTokens(code)) {
                Console.TokenPrinter.print(token);
            }
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
}
