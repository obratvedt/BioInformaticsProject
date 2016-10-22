import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by oyvin on 20/10/2016.
 */
public class Task1 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/s_3_sequence_1M.txt"))) {

            String line = br.readLine();
            int totalLength = 0;
            String pattern = "TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG";
            while (line != null) {
                if (line.substring(line.length() - 4, line.length() - 1).equals(pattern.substring(0, 3))) totalLength++;
                line = br.readLine();
            }
            System.out.println(totalLength);
        }



    }
}
