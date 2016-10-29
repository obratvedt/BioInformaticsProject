import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileParser {

    public static void readFromFile(String fileName) {
        Path path = Paths.get("src/main/resources", fileName);
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(s -> System.out.println(s));
            System.out.println(lines);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        FileParser.readFromFile("s_3_sequence_1M.txt");
    }

}
