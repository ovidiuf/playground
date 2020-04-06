package playground.hr.avbs;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        String file = args[0];

        File f = new File(file);

        List<String> lines = Files.readAllLines(f.toPath());

//        System.out.println(lines.get(0));
//        System.out.println(lines.get(1));

        String ns = lines.get(0).trim();
        int n = Integer.parseInt(ns);

        System.out.println(n);

        String data = lines.get(1);

        String[] tokens = data.split(" ");

        long sum = 0;

        for(int i = 0; i < n; i ++) {

            long l = Long.parseLong(tokens[i]);
            sum += l;
        }

        System.out.println(sum);
    }


}
