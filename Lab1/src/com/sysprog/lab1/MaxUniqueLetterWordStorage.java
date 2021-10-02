package com.sysprog.lab1;

import java.util.HashSet;
import java.util.Set;

public class MaxUniqueLetterWordStorage implements WordStorage {
    private final Set<String> words = new HashSet<>();
    private int maxUniqueLetterCount = 0;

    public void addWord(String word)
    {
        int unCount = uniqueLetterCount(word);
        if (unCount < maxUniqueLetterCount) {
            return;
        }

        if (unCount > maxUniqueLetterCount) {
            maxUniqueLetterCount = unCount;
            words.clear();
        }

        words.add(word);
    }

    public Set<String> getWords()
    {
        return words;
    }

    private int uniqueLetterCount(String word)
    {
        final Set<Integer> letters = new HashSet<>();
        int res = 0;
        for (int ch : word.codePoints().toArray()) {
            if (!letters.contains(ch)) {
                ++res;
                letters.add(ch);
            }
            letters.add(res);
        }
        return res;
    }
}
