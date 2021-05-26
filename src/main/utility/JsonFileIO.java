package utility;

import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    
    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {

        StringBuilder contentBuilder = new StringBuilder();

        try {
            List<String> list = Files.readAllLines(jsonDataFile.toPath());

            for (String s : list) {
                contentBuilder.append(s);
            }

        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '" + jsonDataFile.getName() + "'");
        }
        TaskParser parser = new TaskParser();
        return parser.parse(contentBuilder.toString());
    }
    
    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {

        try (FileWriter file = new FileWriter(jsonDataFile)) {

            JSONArray array = Jsonifier.taskListToJson(tasks);

            file.write(array.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
