import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Task3 {

    private static void bruteForce() {
        ArrayList<String> sequences = FileParser.getSequences("seqset3.txt");
        Map<String, Integer> distribution = new HashMap<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < sequences.get(0).length(); i++) {
            for (String sequence : sequences) {
                String suffix = sequence.substring(i, sequence.length());
                Integer n = distribution.get(suffix);
                n = (n == null) ? 1 : ++n;
                distribution.put(suffix, n);

            }
            int maxOccurrence = 0;
            String adapterSequence = "";
            for (Map.Entry<String, Integer> entry : distribution.entrySet()) {
                if (entry.getValue() > maxOccurrence) {
                    adapterSequence = entry.getKey();
                    maxOccurrence = entry.getValue();
                }
            }
            System.out.println(maxOccurrence);
            System.out.println(adapterSequence);
        }

        long time = System.currentTimeMillis() - start;

        System.out.println(time);


    }

    public static void main(String[] args) {
        bruteForce();
    }
}

