package playground.stanford.tsp;

import java.util.Iterator;

@SuppressWarnings("DuplicatedCode")
public class Main {

    public static void main(String[] args) throws Exception {
        //tests();
        //String fileName = "./data/three.txt";
        //String fileName = "./data/four.txt";
        //String fileName = "./data/eight.txt";
        //String fileName = "./data/nine.txt";
        //String fileName = "./data/tsp-10.txt";
        //String fileName = "./data/tsp-18.txt";
        String fileName = "./data/tsp.txt";
        TravelingSalesmanProblem tsp = new TravelingSalesmanProblem(fileName);
        System.out.println(tsp.g);
        double result = tsp.run();
        System.out.println(result);
    }

    public static void tests() {
        SlowSubset s = new SlowSubset(new int[] {2, 4, 6}, 10);
        SlowSubset s2 = s.add(1);
        assert s2.elements[0] == 1;
        assert s2.elements[1] == 2;
        assert s2.elements[2] == 4;
        assert s2.elements[3] == 6;

        s = new SlowSubset(new int[] {2, 4, 6}, 10);
        s2 = s.add(3);
        assert s2.elements[0] == 2;
        assert s2.elements[1] == 3;
        assert s2.elements[2] == 4;
        assert s2.elements[3] == 6;

        s = new SlowSubset(new int[] {2, 4, 6},10);
        s2 = s.add(4);
        assert s2 == null;

        s = new SlowSubset(new int[] {2, 4, 6}, 10);
        s2 = s.add(5);
        assert s2.elements[0] == 2;
        assert s2.elements[1] == 4;
        assert s2.elements[2] == 5;
        assert s2.elements[3] == 6;

        s = new SlowSubset(new int[] {2, 4, 6}, 10);
        s2 = s.add(8);
        assert s2.elements[0] == 2;
        assert s2.elements[1] == 4;
        assert s2.elements[2] == 6;
        assert s2.elements[3] == 8;

        s = new SlowSubset(new int[] {2, 4, 6}, 10);
        s2 = s.remove(1);
        assert s2.elements.length == 3;
        assert s2.elements[0] == 2;
        assert s2.elements[1] == 4;
        assert s2.elements[2] == 6;

        s = new SlowSubset(new int[] {2, 4, 6}, 10);
        s2 = s.remove(2);
        assert s2.elements.length == 2;
        assert s2.elements[0] == 4;
        assert s2.elements[1] == 6;

        s = new SlowSubset(new int[] {2, 4, 6}, 10);
        s2 = s.remove(4);
        assert s2.elements.length == 2;
        assert s2.elements[0] == 2;
        assert s2.elements[1] == 6;

        s = new SlowSubset(new int[] {2, 4, 6}, 10);
        s2 = s.remove(6);
        assert s2.elements.length == 2;
        assert s2.elements[0] == 2;
        assert s2.elements[1] == 4;

        s = new SlowSubset(new int[] {2, 4, 6}, 10);
        s2 = s.remove(8);
        assert s2.elements.length == 3;
        assert s2.elements[0] == 2;
        assert s2.elements[1] == 4;
        assert s2.elements[2] == 6;

        Subsets subsets = new Subsets(3);
        int i = subsets.getIndexOfSubsetWithoutSpecifiedElement(new SlowSubset(new int[] {1, 2}, 3), 2);
        assert i == 0;
        i = subsets.getIndexOfSubsetWithoutSpecifiedElement(new SlowSubset(new int[] {1, 2, 3}, 3), 3);
        assert i == 1;
        i = subsets.getIndexOfSubsetWithoutSpecifiedElement(new SlowSubset(new int[] {1, 2, 3}, 3), 2);
        assert i == 2;
        i = subsets.getIndexOfSubsetWithoutSpecifiedElement(new SlowSubset(new int[] {1, 2, 3, 4}, 3), 4);
        assert i == 3;
        try {
            subsets.getIndexOfSubsetWithoutSpecifiedElement(new SlowSubset(new int[] {1, 2, 3}, 3), 1);
            throw new RuntimeException("test failed");
        }
        catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        subsets = new Subsets(4);
        i = subsets.getIndexOfSubsetWithoutSpecifiedElement(new SlowSubset(new int[] {1, 2, 3, 4}, 4), 2);
        assert i == 6;

        fastSubsetTests();
    }

    public static void fastSubsetTests() {
        int n = 9;
        FastSubset s = new FastSubset(n, 1);
        int id = s.id();
        assert -1 == id;
        s.setId(5);
        id = s.id();
        assert 5 == id;
        int size = s.size();
        assert 1 == size;
        Iterator<Integer> i = s.elements();
        boolean hasNext = i.hasNext();
        assert hasNext;
        int next = i.next();
        assert 1 == next;
        hasNext = i.hasNext();
        assert !hasNext;
        try {
            new FastSubset(n, 1, 2, 1);
            throw new RuntimeException("failure");
        }
        catch(IllegalArgumentException e) {
            assert e.getMessage().contains("1 is being added twice");
        }

        s = new FastSubset(n, 9);
        size = s.size();
        assert 1 == size;
        i = s.elements();
        hasNext = i.hasNext();
        assert hasNext;
        next = i.next();
        assert 9 == next;
        hasNext = i.hasNext();
        assert !hasNext;

        try {
            new FastSubset(n, 10);
            throw new RuntimeException("failure");
        }
        catch(IllegalArgumentException e) {
            assert e.getMessage().contains("10 is not a valid element");
        }

        s = new FastSubset(n, 9, 8, 4, 3, 2, 1);
        size = s.size();
        assert 6 == size;
        i = s.elements();
        next = i.next();
        assert 1 == next;
        next = i.next();
        assert 2 == next;
        next = i.next();
        assert 3 == next;
        next = i.next();
        assert 4 == next;
        next = i.next();
        assert 8 == next;
        next = i.next();
        assert 9 == next;
        hasNext = i.hasNext();
        assert !hasNext;

        s = new FastSubset(n, 5, 7);
        size = s.size();
        assert 2 == size;
        i = s.elements();
        next = i.next();
        assert 5 == next;
        next = i.next();
        assert 7 == next;
        hasNext = i.hasNext();
        assert !hasNext;

        s = new FastSubset(25, 1, 2, 3, 4, 5, 7, 6, 8, 11, 10, 9, 14, 13, 12, 15, 16, 25, 24, 23, 22, 21, 20, 17, 18, 19);
        size = s.size();
        assert 25 == size;
        i = s.elements();
        next = i.next();
        assert 1 == next;
        next = i.next();
        assert 2 == next;
        next = i.next();
        assert 3 == next;
        next = i.next();
        assert 4 == next;
        next = i.next();
        assert 5 == next;
        next = i.next();
        assert 6 == next;
        next = i.next();
        assert 7 == next;
        next = i.next();
        assert 8 == next;
        next = i.next();
        assert 9 == next;
        next = i.next();
        assert 10 == next;
        next = i.next();
        assert 11 == next;
        next = i.next();
        assert 12 == next;
        next = i.next();
        assert 13 == next;
        next = i.next();
        assert 14 == next;
        next = i.next();
        assert 15 == next;
        next = i.next();
        assert 16 == next;
        next = i.next();
        assert 17 == next;
        next = i.next();
        assert 18 == next;
        next = i.next();
        assert 19 == next;
        next = i.next();
        assert 20 == next;
        next = i.next();
        assert 21 == next;
        next = i.next();
        assert 22 == next;
        next = i.next();
        assert 23 == next;
        next = i.next();
        assert 24 == next;
        next = i.next();
        assert 25 == next;
        hasNext = i.hasNext();
        assert !hasNext;

        s = new FastSubset(9, 1, 2, 3);
        Subset s2 = s.add(1);
        assert s2 == null;
        s = new FastSubset(9, 1, 2, 3);
        try {
            s.add(0);
            throw new RuntimeException("failure");
        }
        catch(IllegalArgumentException e) {
            assert e.getMessage().contains("invalid element: 0");
        }
        try {
            s.add(10);
            throw new RuntimeException("failure");
        }
        catch(IllegalArgumentException e) {
            assert e.getMessage().contains("invalid element: 10");
        }

        s = new FastSubset(9, 1, 2, 3);
        s2 = s.add(9);
        assert s != s2;
        size = s.size();
        assert size == 3;
        i = s.elements();
        next = i.next();
        assert next == 1;
        next = i.next();
        assert next == 2;
        next = i.next();
        assert next == 3;
        hasNext = i.hasNext();
        assert !hasNext;
        size = s2.size();
        assert size == 4;
        Iterator<Integer> i2 = s2.elements();
        next = i2.next();
        assert next == 1;
        next = i2.next();
        assert next == 2;
        next = i2.next();
        assert next == 3;
        next = i2.next();
        assert next == 9;
        hasNext = i2.hasNext();
        assert !hasNext;

        s = new FastSubset(9, 1, 2, 3);
        s2 = s.remove(4);
        assert s == s2;
        size =  s.size();
        assert size == 3;
        size =  s2.size();
        assert size == 3;
        i = s.elements();
        next = i.next();
        assert next == 1;
        next = i.next();
        assert next == 2;
        next = i.next();
        assert next == 3;
        hasNext = i.hasNext();
        assert !hasNext;

        s = new FastSubset(9, 1, 2, 3);
        s2 = s.remove(2);
        assert s != s2;
        size = s.size();
        assert 3 == size;
        i = s.elements();
        next = i.next();
        assert 1 == next;
        next = i.next();
        assert 2 == next;
        next = i.next();
        assert 3 == next;
        hasNext = i.hasNext();
        assert !hasNext;
        size = s2.size();
        assert 2 == size;
        i = s2.elements();
        next = i.next();
        assert 1 == next;
        next = i.next();
        assert 3 == next;
        hasNext = i.hasNext();
        assert !hasNext;

        s = new FastSubset(9, 1, 2, 3);
        s.setId(1);
        s2 = new FastSubset(9, 1, 2, 3);
        s2.setId(2);
        boolean equals;
        equals = s2.equals(s);
        assert equals;
        equals = s.equals(s2);
        assert equals;

        s2 = new FastSubset(9, 1, 2, 4);
        equals = s2.equals(s);
        assert !equals;
        equals = s.equals(s2);
        assert !equals;
    }
}
