package com.sysprog.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class FileWordReader implements WordReader {

    final BufferedReader br;
    final StringBuilder sb = new StringBuilder();
    boolean wordRead = false;

    public FileWordReader(FileReader fileReader)
    {
        br = new BufferedReader(fileReader);
    }

    public boolean haveNext()
    {
        if (!wordRead) {
            readWord();
        }

        return !sb.isEmpty();
    }

    public String next()
    {
        if (!wordRead) {
            readWord();
        }
        String res = sb.toString();
        sb.delete(0, sb.length());
        wordRead = false;
        return res.toLowerCase(Locale.ROOT);
    }

    private void readWord() {
        sb.setLength(0);
        wordRead = true;
        try {
            if (br.ready()) {
                int nextChar = br.read();
                while (!Character.isLetter(nextChar)){
                    if (!br.ready()) {
                        break;
                    }
                    nextChar = br.read();
                }
                while (Character.isLetter(nextChar)) {
                    sb.appendCodePoint(nextChar);
                    if (!br.ready()) {
                        break;
                    }
                    nextChar = br.read();
                }
            }
        } catch(IOException ex) {
            sb.setLength(0);
        }
    }
}
