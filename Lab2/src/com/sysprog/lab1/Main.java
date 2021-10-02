package com.sysprog.lab1;

import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Scanner;

public class Main {

    private static Path getFilePath(String[] args) throws IllegalArgumentException {
        String arg;
        if (args.length > 1) {
            throw new IllegalArgumentException("");
        }

        if (args.length == 1) {
            arg = args[0];
        } else {
            Scanner scanner = new Scanner(System.in);
            arg = scanner.nextLine();
        }

        return FileSystems.getDefault().getPath(arg).toAbsolutePath();
    }

    private static <T> void printCollection(Collection<T> collection) {
        for (var word : collection) {
            System.out.println(word);
        }
    }

    private static Automaton readAutomaton(FileReader file)
    {
        Scanner sc = new Scanner(file);
        /* final int nAlphabet = */ sc.nextInt();
        final int nState = sc.nextInt();
        final int startState = sc.nextInt();
        Automaton automaton = new Automaton(nState, startState);
        while (sc.hasNext()) {
            final int curState = sc.nextInt();
            final int input = sc.next().codePointAt(0);
            final int nextState = sc.nextInt();
            automaton.addTransition(curState, input, nextState);
        }
        return automaton;
    }

    public static void main(String[] args)
    {
        final Path filePath;
        try {
            filePath = getFilePath(args);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getLocalizedMessage());
            System.exit(1);
            return;
        }

        try (FileReader fr = new FileReader(filePath.toString())) {
            Automaton automaton = readAutomaton(fr);
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
}
