package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TextFileController {

    public static void appendToFile(int score) throws IOException {
    	BufferedWriter writer = new BufferedWriter(new FileWriter("src/ScoreResult/score.txt", true));
        writer.write(score + "\n");

        writer.close();
    }

	 public static Integer[] readFromFile() throws IOException {
         Stream<String> stream = Files.lines(Paths.get("src/ScoreResult/score.txt"));
         return stream.map(x -> Integer.valueOf(x)).sorted().toArray(Integer[]::new);
    }

	
}
