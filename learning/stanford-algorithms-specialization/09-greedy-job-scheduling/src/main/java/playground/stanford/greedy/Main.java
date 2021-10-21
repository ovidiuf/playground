package playground.stanford.greedy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("DuplicatedCode")
public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "./data/jobs.txt";
        //String fileName = "./data/test1.txt";
        List<Job> jobs = load(fileName);
//        System.out.println(jobs);

        List<WeightLengthDifference> weightLengthDifferenceScores = new ArrayList<>();
        for(Job j: jobs) {
            weightLengthDifferenceScores.add(new WeightLengthDifference(j));
        }
        Collections.sort(weightLengthDifferenceScores);
//        System.out.println(weightLengthDifferenceScores);

        List<WeightLengthRatio> weightLengthRatioScores = new ArrayList<>();
        for(Job j: jobs) {
            weightLengthRatioScores.add(new WeightLengthRatio(j));
        }
        Collections.sort(weightLengthRatioScores);
//        System.out.println(weightLengthRatioScores);

        // compute the objective function (weighted sum of completion times) by processing the jobs from the
        // largest greedy score to the lowest
        long waitTime = 0;
        long weightedSumOfCompletionTimes = 0;

        for(int i = weightLengthDifferenceScores.size() - 1; i >=0; i --) {
            Job j = weightLengthDifferenceScores.get(i).getJob();
            long completionTime = waitTime + j.length;
            weightedSumOfCompletionTimes += j.weight * completionTime;
            waitTime = completionTime;
        }
        System.out.println("weighted sum of completion times for difference: " + weightedSumOfCompletionTimes);

        waitTime = 0;
        weightedSumOfCompletionTimes = 0;
        for(int i = weightLengthRatioScores.size() - 1; i >=0; i --) {
            Job j = weightLengthRatioScores.get(i).getJob();
            long completionTime = waitTime + j.length;
            weightedSumOfCompletionTimes += j.weight * completionTime;
            waitTime = completionTime;
        }
        System.out.println("weighted sum of completion times for ratio: " + weightedSumOfCompletionTimes);

    }

    private static List<Job> load(String fileName) throws Exception {
        List<Job> jobs = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int count = Integer.parseInt(br.readLine());
            for(int i = 0; i < count; i ++) {
                line = br.readLine().trim();
                String[] tok = line.split(" ");
                int weight = Integer.parseInt(tok[0]);
                int length = Integer.parseInt(tok[1]);
                jobs.add(new Job(weight, length));
            }
        }
        return jobs;
    }
}
