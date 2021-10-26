package com.sysprog.lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileCharSource implements CharSource {

    final BufferedReader br;

    public FileCharSource(FileReader fileReader)
    {
        br = new BufferedReader(fileReader);
    }

    @Override
    public boolean hasNext() {
        try {
            return br.ready();
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public int next()
    {
        try {
            return br.read();
        } catch (IOException ex) {
            return '\0';
        }
    }
}