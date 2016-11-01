
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Task2 {
    private static void bruteForce() {
        ArrayList<String> sequences = FileParser.getSequences("s_3_sequence_1M.txt");
        String pattern = "TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG";
        Map<Integer, Integer> lengthDist = new HashMap<>();

        long start = System.currentTimeMillis();

        for (String sequence : sequences) {
            String longestSuffix = "";
            int length = sequence.length();
            for (int j = 0; j < sequence.length(); j++) {
                String suffix = sequence.substring(j, sequence.length());
                String prefix = pattern.substring(0, suffix.length());
                double maxMisMatch = Math.floor(suffix.length() * 0.1);
                int mismatches = 0;
                for (int i = 0; i < suffix.length(); i++) {
                    if (suffix.charAt(i) != prefix.charAt(i)) {
                        mismatches++;
                        if (mismatches > maxMisMatch) break;
                    }
                }
                if (mismatches <= maxMisMatch) {
                    longestSuffix = suffix;
                }
            }
            length -= longestSuffix.length();
            Integer n = lengthDist.get(length);
            n = (n == null) ? 1 : ++n;
            lengthDist.put(length, n);


        }
        long totalTime = System.currentTimeMillis() - start;

        System.out.println(lengthDist);
        System.out.printf("Number of sequences with a match: %d", 1000000 - lengthDist.get(50));
        System.out.println();
        System.out.printf("Total time: %d", totalTime);


    }

    public static void main(String[] args) {
        bruteForce();
    }
}
