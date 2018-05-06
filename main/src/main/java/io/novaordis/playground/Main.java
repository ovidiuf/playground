package io.novaordis.playground;

import java.util.Optional;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) throws Exception {


        System.out.println(sumFirstNNumbers(Integer.parseInt(args[0])));

    }




    public static long sumFirstNNumbers(int n) {

        Optional<Long> result =
                Stream.
                        iterate(1L, prev -> prev + 1).
                        limit(n).
                        parallel().
                        reduce(Long::sum);

        return result.get();
    }


}
