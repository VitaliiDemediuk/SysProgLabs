package com.sysprog.lab1;

import java.util.List;
import java.util.Set;

public interface WordStorage {
    void addWord(String word);
    Set<String> getWords();
}
