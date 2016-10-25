import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Task1 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/s_3_sequence_1M.txt"))) {
            String line = br.readLine();
            String pattern = "TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG";
            String[] prefixes = new String[pattern.length()];
            Map<Integer, Integer> lengthDist = new HashMap<>();
            long start = System.currentTimeMillis();

            for (int i = 1; i <= pattern.length(); i++) {
                String prefix = pattern.substring(0, i);
                prefixes[i-1] = prefix;
            }
            Arrays.sort(prefixes);

            while (line != null) {
                String longestSuffix = "";
                int length = line.length();
                for (int j = line.length() - 1; j >= 0; j--) {
                    String suffix = line.substring(j, line.length());
                    int ip = Arrays.binarySearch(prefixes, suffix);
                    if (ip >= 0) {

                        longestSuffix = suffix;
                    }

                }
                length -= longestSuffix.length();
                Integer n = lengthDist.get(length);
                n = (n == null) ? 1 : ++n;
                lengthDist.put(length, n);
                line = br.readLine();

            }
            long totalTime = System.currentTimeMillis() - start;

            System.out.println(lengthDist);
            System.out.printf("Number of sequences with a match: %d", 1000000 - lengthDist.get(50));
            System.out.println();
            System.out.printf("Total time: %d", totalTime);


        }
    }
}
