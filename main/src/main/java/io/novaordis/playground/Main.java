/*
 * Copyright (c) 2018 Nova Ordis LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.novaordis.playground;

import java.util.Random;

public class Main{
    private static Random r = new Random();
    private static int times = 1000000;
    private static int sums[] = new int[times];
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Mama and Maya! I am going to roll the dice two times, for " + times + " times ...");
        for(int i = 0; i < times; i ++) {
            int diceValue1 = simulateDice();
            int diceValue2 = simulateDice();
            //System.out.println("(" + diceValue1 + ", " + diceValue2 + ")");
            int sum = diceValue1 + diceValue2;
            sums[i] = sum;
        }
        Thread.sleep(1000L);
        System.out.println("Now we are computing the average, hang tight ...");
        double sum = 0;
        for(int i = 0; i < times; i ++) {
            sum += sums[i];
        }
        System.out.println(sum/times);
    }

    public static int simulateDice() {
        return r.nextInt(6) + 1;
    }
}
