import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by oyvin on 20/10/2016.
 */
public class Task1 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/s_3_sequence_1M.txt"))) {

            String line = br.readLine();
            String pattern = "TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG";
            ArrayList<String> prefixes = new ArrayList<>();
            for (int i = 1; i <= pattern.length(); i++) {
                String prefix = pattern.substring(0, i);
                prefixes.add(prefix);
            }
            while (line != null) {
                ArrayList<String> suffixes = new ArrayList<>();
                for (int j = line.length() - 1; j >= 0; j--) {
                    String suffix = line.substring(j, line.length());
                    suffixes.add(suffix);
                }
                line = br.readLine();
            }

        }
    }
}
