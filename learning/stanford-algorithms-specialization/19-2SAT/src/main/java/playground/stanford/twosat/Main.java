package playground.stanford.twosat;

public class Main {

    public static void main(String[] args) throws Exception {
        //String fileName = "./data/2sat1.txt";
        String fileName = "./data/2sat2.txt";
        //String fileName = "./data/test.txt";
        if (args.length > 0) {
            fileName = args[0];
        }
        System.out.println("using " + fileName);
        TwoSATInstance i = new TwoSATInstance(fileName);
        //System.out.println(i);
        boolean result = i.runRandomizedLocalSearch();
        System.out.println(toName(fileName) + " " + (result ? "satisfiable":"not satisfiable"));
    }

    private static String toName(String fileName) {
        String s = fileName;
        s = fileName.substring(fileName.lastIndexOf("/") + 1);
        s = s.substring(0, s.lastIndexOf("."));
        return s;
    }
}
