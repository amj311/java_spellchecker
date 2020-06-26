package spell;

public class Trie implements ITrie {
    private TrieNode root = new TrieNode();
    private int hashCode = 0;
    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public void add(String word) {
        int wordHash = 7;
        int cnt = root.add(word, 0);
        String wordAndCnt = word + cnt;

        for (int i = 0; i < wordAndCnt.length(); i++) {
            wordHash = wordHash*31 + wordAndCnt.charAt(i);
        }
        hashCode += wordHash;
    }


    @Override
    public INode find(String word) {
        return root.find(word, 0);
    }

    @Override
    public int getWordCount() {
        return root.getWordCount();
    }

    @Override
    public int getNodeCount() {
        return root.getNodeCount();
    }

    @Override
    public String toString() {
        StringBuilder allWords = new StringBuilder();
        root.getCompleteWords("", allWords);
        System.out.println(allWords.toString());
        return allWords.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        else if (obj.getClass() != this.getClass()) return false;

        Trie otherTrie = (Trie)(obj);

        if (otherTrie.hashCode() != this.hashCode()) return false;
        else return root.equals(otherTrie.root);
    }
}
