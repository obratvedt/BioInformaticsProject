import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileParser {

    private static void readFromFile(String fileName) {
        Path path = Paths.get("src/main/resources", fileName);
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(s -> System.out.println(s));
            System.out.println(lines);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<String> getSequences(String fileName) {
        ArrayList<String> sequences = new ArrayList<>();
        Path path = Paths.get("src/main/resources", fileName);

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(sequences::add);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return sequences;
    }


    public static void main(String[] args) {
        FileParser.readFromFile("s_3_sequence_1M.txt");
    }

}
