package com.sysprog.lab4;

import org.json.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

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

    private static VirtualTokenizer readAutomatonTokenizer(Path automatonPath, Path codePath) throws FileNotFoundException, JSONException
    {
        final FileReader fr = new FileReader(automatonPath.toString());
        final JSONTokener tokener = new JSONTokener(fr);
        final JSONObject root = new JSONObject(tokener);

        final var keyWords = new HashSet<String>();
        ((JSONArray)root.get("key_words")).forEach(ob -> keyWords.add((String)ob) );

        final String startState = (String)root.get("start_sate");
        final FileCharSource codeFileSource = new FileCharSource(new FileReader(codePath.toString()));
        final AutomatonTokenizer tokenizer = new AutomatonTokenizer(codeFileSource, keyWords, startState);

        ((JSONArray)root.get("states")).forEach(ob -> {
            final var transitionInfo = (JSONObject)ob;
            final String stateName = transitionInfo.getString("state");
            final boolean isFinalState = transitionInfo.getBoolean("is_final_state");
            if (isFinalState) {
                final String tokenName = transitionInfo.getString("name");
                final ConsoleColor color = ConsoleColor.fromString(transitionInfo.getString("color"));
                tokenizer.addState(stateName, new TokenClass(tokenName, color));
            } else {
                tokenizer.addState(stateName);
            }

        });

        ((JSONArray)root.get("transitions")).forEach(ob -> {
            final var transitionInfo = (JSONObject)ob;
            final String state = transitionInfo.getString("state");
            final CharChecker checker;
            final var input = transitionInfo.get("input");
            if (input instanceof JSONArray) {
                final Set<Integer> codePointSet = new HashSet<>();
                ((JSONArray) input).forEach(codeOb -> {
                    codePointSet.add(codeOb.toString().codePointAt(0));
                });
                checker = new SetCharChecker(codePointSet);
            }  else {
                checker = new RegexCharChecker(Pattern.compile(input.toString()));
            }
            final String moveTo = transitionInfo.getString("move_to");
            tokenizer.addTransition(state, checker, moveTo);
        });
        return tokenizer;
    }

    public static void main(String[] args)
    {
        final Path automatonPath;
        final Path filePath;
        try {
            automatonPath = FileSystems.getDefault().getPath("automaton.json").toAbsolutePath();
            filePath = FileSystems.getDefault().getPath("test.c").toAbsolutePath();
//            automatonPath = getFilePath(args, 0);
//            filePath = getFilePath(args, 1);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getLocalizedMessage());
            System.exit(1);
            return;
        }
        try {
            final VirtualTokenizer tokenizer = readAutomatonTokenizer(automatonPath, filePath);
            while (tokenizer.hasNext()) {
                Console.TokenPrinter.print(tokenizer.next());
            }
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
}
