package playground.stanford.mm;

@SuppressWarnings({"unused", "StatementWithEmptyBody"})
public class MedianMaintenance {
    public static void main(String[] args) throws Exception {
        //String fileName = "./data/test.txt";
        String fileName = "./data/Median.txt";
        IntegerStorageFile f = new IntegerStorageFile(fileName);
        MaxHeap Hlow = new MaxHeap();
        MinHeap Hhigh = new MinHeap();
        Integer x;
        while((x = f.nextInt()) != null) {
            if (Hlow.size() == 0 || x <= Hlow.maximum()) {
                Hlow.insert(x);
            }
            else {
                Hhigh.insert(x);
            }
            rebalance(Hlow, Hhigh);
            int currentMedian = Hlow.maximum();
            processCurrentMedian(currentMedian);
        }
        displayResult();
        f.close();
        //testMinHeap();
        //testMaxHeap();
    }

    private static void rebalance(MaxHeap Hlow, MinHeap Hhigh) {
        int difference = Hlow.size() - Hhigh.size();
        if (difference > 2) {
            throw new IllegalStateException("difference in size between Hlow and Hhigh is " + difference);
        }
        else if (difference == 2) {
            // shift the Hlow max element from Hlow to Hhigh
            Hhigh.insert(Hlow.removeMax());
        }
        else if (difference == 1 || difference == 0) {
            // we're balanced
        }
        else if (difference == -1) {
            // shift the Hhigh min element from Hhigh to Hlow
            Hlow.insert(Hhigh.removeMin());
        }
        else {
            throw new IllegalStateException("difference in size between Hlow and Hhigh is " + difference);
        }
    }

    private static int count = 0;
    private static long sum = 0;
    private static void processCurrentMedian(int m) {
        count ++;
        sum += m;
        System.out.println(count + ": " + m);
    }

    private static void displayResult() {
        System.out.println(sum);
        System.out.println(sum % 10000);

    }

    private static void testMinHeap() {
        MinHeap h = new MinHeap();
        assert 0 == h.size();

        h.insert(10);
        assert 1 == h.size();
        assert 10 == h.minimum();

        h.insert(9);
        assert 2 == h.size();
        assert 9 == h.minimum();

        h.insert(8);
        assert 3 == h.size();
        assert 8 == h.minimum();

        h.insert(1);
        assert 4 == h.size();
        assert 1 == h.minimum();

        h.insert(2);
        assert 5 == h.size();
        assert 1 == h.minimum();

        int i = h.removeMin();
        assert 1 == i;
        assert 4 == h.size();
        assert 2 == h.minimum();

        i = h.removeMin();
        assert 2 == i;
        assert 3 == h.size();
        assert 8 == h.minimum();

        i = h.removeMin();
        assert 8 == i;
        assert 2 == h.size();
        assert 9 == h.minimum();

        i = h.removeMin();
        assert 9 == i;
        assert 1 == h.size();
        assert 10 == h.minimum();

        i = h.removeMin();
        assert 10 == i;
        assert 0 == h.size();
        try {
            h.minimum();
            throw new RuntimeException();
        }
        catch(IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testMaxHeap() {
        MaxHeap h = new MaxHeap();
        assert 0 == h.size();

        h.insert(1);
        assert 1 == h.size();
        assert 1 == h.maximum();

        h.insert(2);
        assert 2 == h.size();
        assert 2 == h.maximum();

        h.insert(3);
        assert 3 == h.size();
        assert 3 == h.maximum();

        h.insert(4);
        assert 4 == h.size();
        assert 4 == h.maximum();

        int i = h.removeMax();
        assert 4 == i;
        assert 3 == h.size();
        assert 3 == h.maximum();

        i = h.removeMax();
        assert 3 == i;
        assert 2 == h.size();
        assert 2 == h.maximum();

        i = h.removeMax();
        assert 2 == i;
        assert 1 == h.size();
        assert 1 == h.maximum();

        i = h.removeMax();
        assert 1 == i;
        assert 0 == h.size();
    }
}
