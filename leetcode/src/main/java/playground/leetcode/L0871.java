package playground.leetcode;

public class L0871 {

    //
    // TODO: BROKEN SOLUTION!
    //
    public void run() {
        //int target = 1; int startFuel = 1; int[][] stations = {};
        //int target = 100; int startFuel = 1; int[][] stations = {{10,100}};
        //int target = 100; int startFuel = 10; int[][] stations = {{10,60},{20,30},{30,30},{60,40}};
        int target = 1000; int startFuel = 299; int[][] stations = {{13,21}, {26,115}, {100,47}, {225,99}, {299,141},{444,198}, {608,190}, {636,157},{647,255},{841,123}};
        int result = new Solution().minRefuelStops(target, startFuel,  stations);
        System.out.println(result);
    }

    class Solution {
        public int minRefuelStops(int target, int startFuel, int[][] stations) {
            int c = 0; // counter of stations visited so far
            int d = 0; // distance traveled
            int f = startFuel; // current fuel
            int next = 0; // index of the next station
            while(true) {
                if (d + f >= target) {
                    return c;
                }
                // what is the further station I can reach and what is the max fuel along the way?
                int maxFuel = -1;
                int fsi = -1;
                for(int i = next; i < stations.length; i ++) {
                    int distToStation = stations[i][0] - d;
                    if (f >= distToStation) {
                        // I can reach it
                        if (stations[i][1] > maxFuel) {
                            maxFuel = stations[i][1];
                            fsi = i;
                        }
                    }
                }
                if (fsi == -1) {
                    // can't reach the next station
                    return -1;
                }
                // go to station with the max fuel and refuel
                f = f - (stations[fsi][0] -d) + stations[fsi][1];
                d = stations[fsi][0];
                c ++;
                next = fsi + 1;
            }
        }
    }
}
