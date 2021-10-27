package playground.stanford.huffman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Alphabet {

    private List<Symbol> alphabet;

    public Alphabet(String fileName) throws Exception {
        this.alphabet = new ArrayList<>();
        load(fileName);
    }

    public int size() {
        return alphabet.size();
    }

    public Symbol removeSymbolWithLowestWeight() {
        // we assume the symbols are already sorted in the ascending order of their weight
        return alphabet.remove(0);
    }

    public void insert(Symbol s) {
        // we always sort in the ascending order of the weight, the proper way to implement this would be with a heap
        // which will reduce the running time from n log n to log n.
        alphabet.add(s);
        Collections.sort(alphabet);
    }

    @Override
    public String toString() {
        return "size=" + alphabet.size();
    }

    private void load(String fileName) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine().trim();
            int size = Integer.parseInt(line);
            for(int i = 0; i < size; i++) {
                insert(new Symbol("" + i, Integer.parseInt(br.readLine().trim())));
            }
        }
    }
}
