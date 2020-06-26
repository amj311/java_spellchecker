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
//                System.out.println(data);
                trie.add(data);
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        generateSuggestionsForWord(inputWord);



        return null;
    }

    private Set[] generateSuggestionsForWord(String word) {
        Vector<String> suggestions = new Vector<>();
        // do the following for every letter position.
        int i = 0;
        do {
            // remove letter
            if (i < word.length()) suggestions.add(word.substring(0, i) + word.substring(i+1));

            // swap with every letter
            for (char c = 'a'; c < 'a' + 26; c++) {
                if (i < word.length()) System.out.println(word.substring(0, i) + c + word.substring(i+1));
            }

            // add every letter
            for (char c = 'a'; c < 'a' + 26; c++) {
                System.out.println(word.substring(0, i) + c + word.substring(i));
            }
        } while (i < word.length() && ++i > 0);
        return null;
    }

    private void generateDist1Suggestions(String inputWord) {

    }

    private Set<String> dist1Suggestions;
    private Set<String> dist2Suggestions;
}
