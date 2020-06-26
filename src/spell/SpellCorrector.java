package spell;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.Vector;

public class SpellCorrector implements ISpellCorrector {
    private Trie trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        try {
            File myObj = new File(dictionaryFileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                String data = myReader.next();
                trie.add(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
//            System.out.println(e);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        inputWord = inputWord.toLowerCase();

        if (trie.find(inputWord) != null) return inputWord;

        Vector<String> dist1Suggestions = generateSuggestionsForWord(inputWord);
        int maxFreq = 0;
        String mostFreqMatch = null;

        for (String word : dist1Suggestions) {
            if (word == null) continue;
            TrieNode matchNode = trie.find(word);
            if (matchNode == null) continue;
            if (matchNode.getValue() > maxFreq) {
                mostFreqMatch = word;
                maxFreq = matchNode.getValue();
            }
//            System.out.println(word + ": " + matchNode.getValue() + ". mostFreqMatch: " + mostFreqMatch);
        }
        if (maxFreq > 0) return mostFreqMatch;

        Vector<String> dist2Suggestions = new  Vector<>();
        for (String word : dist1Suggestions) {
            dist2Suggestions.addAll(generateSuggestionsForWord(word));
        }

        maxFreq = 0;
        for (String word : dist2Suggestions) {
            if (word == null) continue;
            TrieNode matchNode = trie.find(word);
            if (matchNode == null) continue;
            if (matchNode.getValue() > maxFreq) {
                mostFreqMatch = word;
                maxFreq = matchNode.getValue();
            }
//            System.out.println(word + ": " + matchNode.getValue() + ". mostFreqMatch: " + mostFreqMatch);
        }
        return mostFreqMatch;
    }

    private Vector<String> generateSuggestionsForWord(String word) {
        Vector<String> suggestions = new Vector<String>();
        // do the following for every letter position.
        int i = 0;
        do {
            // remove letter
            if (i < word.length()) suggestions.add(word.substring(0, i) + word.substring(i+1));

            // swap next letter
            if (i < word.length() - 1) suggestions.add(word.substring(0, i) + word.charAt(i+1) + word.charAt(i) + word.substring(i+2));

            // swap with every letter
            for (char c = 'a'; c < 'a' + 26; c++) {
                if (i < word.length() && c != word.charAt(i)) suggestions.add(word.substring(0, i) + c + word.substring(i+1));
            }
            // add every letter
            for (char c = 'a'; c < 'a' + 26; c++) {
                suggestions.add(word.substring(0, i) + c + word.substring(i));
            }
        } while (i < word.length() && ++i > 0);
        return suggestions;
    }
}
