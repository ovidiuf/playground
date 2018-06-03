package io.novaordis.playground.algorithms.insertionsort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ovidiu Feodorov <ovidiu@swim.ai>
 * @since 6/3/18
 */
public class Configuration {

    List<Integer> list = new ArrayList<>();

    public Configuration(String[] args) {

        for(String s: args) {

            int i = Integer.parseInt(s);
            list.add(i);
        }
    }

    public int[] getArray() {

        int[] result = new int[list.size()];

        int index = 0;
        for(Integer i: list) {

            result[index] = list.get(index);
            index ++;
        }

        return result;
    }
}
