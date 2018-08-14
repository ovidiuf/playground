package playground.leetcode.maxPointsOnALine;

public class Main {

    public static void main(String[] args) throws Exception {

        Point[] input = Util.loadInput(args);

        //Util.toCsv(input);

        Solution s = new Solution();

        int result = s.maxPoints(input);

        System.out.println(result);
    }
}
