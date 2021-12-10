package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TextFileController {

    public static void appendToFile(int score) throws IOException {
    	BufferedWriter writer = new BufferedWriter(new FileWriter("src/ScoreResult/score.txt", true));
        writer.write(score + "\n");

        writer.close();
    }

	 public static Integer[] readFromFile() throws IOException {
         Stream<String> stream = Files.lines(Paths.get("src/ScoreResult/score.txt"));
         return stream.map(x -> Integer.valueOf(x)).toArray(Integer[]::new);
    }
	 
	 public static void nAppendToFile(String name) throws IOException {
	    	BufferedWriter writer = new BufferedWriter(new FileWriter("src/ScoreResult/name.txt", true));
	        writer.write(name + "\n");

	        writer.close();
	    }

		 /*public static String[] nReadFromFile() throws IOException {
	         Stream<String> stream = Files.lines(Paths.get("src/ScoreResult/name.txt"));
	         return stream.map(x -> String.)
	    }*/

		 public static String[] nReadFromFile() throws IOException {
			 FileReader fileReader = new FileReader("src/ScoreResult/name.txt");
		        BufferedReader bufferedReader = new BufferedReader(fileReader);
		        List<String> lines = new ArrayList<String>();
		        String line = null;
		        while ((line = bufferedReader.readLine()) != null) {
		            lines.add(line);
		        }
		        bufferedReader.close();
		        return lines.toArray(new String[lines.size()]);
	    }
}
