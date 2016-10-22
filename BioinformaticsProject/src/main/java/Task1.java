import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Task1 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/s_3_sequence_1M.txt"))) {
            int matches = 0;
            String line = br.readLine();
            String pattern = "TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG";
            ArrayList<String> prefixes = new ArrayList<>();
            Map<Integer, Integer> lengthDist = new HashMap<>();
            for (int i = 1; i <= pattern.length(); i++) {
                String prefix = pattern.substring(0, i);
                prefixes.add(prefix);
            }
            while (line != null) {

                for (int j = line.length() - 1; j >= 0; j--) {
                    String suffix = line.substring(j, line.length());
                    int length = line.length();
                    for (String prefix : prefixes){
                        if (suffix.equals(prefix)){
                            length -= suffix.length();
                        }

                    }

                    Integer n = lengthDist.get(length);
                    n = (n == null)? 1 : ++n;
                    lengthDist.put(length,n);

                }
                line = br.readLine();

            }
            System.out.println(lengthDist);

        }
    }
}
