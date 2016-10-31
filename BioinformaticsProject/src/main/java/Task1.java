import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Task1 {
    public static void bruteForce() {
        ArrayList<String> sequences = FileParser.getSequences("s_3_sequence_1M.txt");
        String pattern = "TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG";
        String[] prefixes = new String[pattern.length()];
        Map<Integer, Integer> lengthDist = new HashMap<>();
        long start = System.currentTimeMillis();

        for (int i = 1; i <= pattern.length(); i++) {
            String prefix = pattern.substring(0, i);
            prefixes[i - 1] = prefix;
        }
        Arrays.sort(prefixes);

        for (String sequence : sequences) {
            String longestSuffix = "";
            int length = sequence.length();
            for (int j = 0; j < sequence.length(); j++) {
                String suffix = sequence.substring(j, sequence.length());
                int ip = Arrays.binarySearch(prefixes, suffix);
                if (ip >= 0) {

                    longestSuffix = suffix;
                    break;
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
