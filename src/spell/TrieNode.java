package spell;

import java.util.Arrays;

public class TrieNode implements INode {
    private int count = 0;
    private TrieNode[] nodes = new TrieNode[26];

    private TrieNode nodeForChar(char c) {
        return nodes[c - 'a'];
    }

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public TrieNode[] getChildren() {
        return new TrieNode[0];
    }


    public int add(String word, int i) {

        if (nodeForChar(word.charAt(i)) == null) nodes[word.charAt(i) - 'a'] = new TrieNode();
        TrieNode nextNode = nodeForChar(word.charAt(i));

        if (i == word.length() - 1) return ++nextNode.count;
        return nextNode.add(word, ++i);
    }



    public TrieNode find(String word, int i) {
        if (i >= word.length()) {
            if (count > 0) return this;
            else return null;
        }
        else if (nodeForChar(word.charAt(i)) != null) return nodeForChar(word.charAt(i)).find(word, ++i);
        else return null;
    }


    public int getNodeCount() {
        int nodeCount = 1;
        for (TrieNode node : nodes) {
            if (node != null) nodeCount += node.getNodeCount();
        }
        return nodeCount;
    }

    public int getWordCount() {
        int wordCount = 0;
        if (count > 0) wordCount = 1;
        for (TrieNode node : nodes) {
            if (node != null) wordCount += node.getWordCount();
        }
        return wordCount;
    }

    public void getCompleteWords(String precedingChars, StringBuilder allWords, boolean includeFreq) {
        for (char c = 'a'; c < 'a' + 26; c++) {
            TrieNode nextNode = nodes[c - 'a'];
            if (nextNode != null) {
                String newChars = precedingChars + c;
                if (nextNode.count > 0) {
                    if (includeFreq) newChars += nextNode.count;
                    allWords.append(newChars + '\n');
                }
                nextNode.getCompleteWords(newChars, allWords, includeFreq);
            }
        }
    }
    public void getCompleteWords(String precedingChars, StringBuilder allWords) {
        this.getCompleteWords(precedingChars, allWords, false);
    }

    @Override
    public String toString() {
        StringBuilder notNullNodes = new StringBuilder();
        for (char c = 'a'; c < 'a' + 26; c++) {
            if (nodes[c - 'a'] != null) notNullNodes.append(c+", ");
        }

        return "TrieNode{" +
                "count=" + count +
                ", nodes=[" + notNullNodes.toString() +
                "]}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;

        if ( this.getClass() != obj.getClass() ) return false;
        TrieNode other = (TrieNode) obj;

        if (other.count != this.count) return false;

        for (char c = 'a'; c < 'a' + 26; c++) {
            if (nodes[c - 'a'] != null && !nodes[c - 'a'].equals(other.nodes[c - 'a'])) return false;
        }
        return true;
    }
}
