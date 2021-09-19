package com.sysprog.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toString()))){
            // code
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
        }
    }
}
