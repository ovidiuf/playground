package playground.stanford.twosum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Long> data = load();
        Map<Long, Long> map = toMap(data);
        System.out.println("total: " + data.size() + ", distinct: " + map.size());

        int counter = 0;
        for(long t = -10000; t<= 10000; t ++) {
            if (findNumbers(t, map)) {
                counter ++;
            }
        }

        System.out.println("counter: " + counter);
    }

    private static List<Long> load() throws Exception {
        String fileName = "./data/algo1-programming_prob-2sum.txt";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<Long> result = new ArrayList<>();
        String line;
        while((line = br.readLine()) != null) {
            result.add(Long.parseLong(line.trim()));
        }
        br.close();
        return result;
    }

    private static Map<Long, Long> toMap(List<Long> list) {
        Map<Long, Long> result = new HashMap<>();
        for(Long l: list) {
            result.put(l, l);
        }
        return result;
    }

    private static boolean findNumbers(long t, Map<Long, Long> map) {

        for(Long l: map.keySet()) {
            long l2 = t - l;
            if (l == l2) {
                continue;
            }
            if (map.containsKey(l2)) {
                System.out.println(t + " added as sum of " + l + " and " + l2);
                return true;
            }
        }
        return false;
    }
}
