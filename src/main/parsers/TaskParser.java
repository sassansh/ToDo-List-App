package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        List<Task> finalList = new ArrayList<>();
        JSONArray tasksArray = new JSONArray(input);
        for (Object o : tasksArray) {
            try {
                JSONObject taskJson = (JSONObject) o;
                Task task = new Task(taskJson.getString("description"));
                parseTags(taskJson, task);
                if (taskJson.get("due-date") != JSONObject.NULL) {
                    parseDueDate(taskJson, task);
                }
                parsePriority(taskJson, task);
                parseStatus(taskJson, task);
                finalList.add(task);
            } catch (JSONException e) {
                // expected
            }
        }
        return finalList;
    }

    private void parseTags(JSONObject o, Task task) {
        JSONArray tagsArray = o.getJSONArray("tags");
        for (Object ob : tagsArray) {
            JSONObject jo = (JSONObject) ob;
            task.addTag(new Tag(jo.getString("name")));
        }
    }


    private void parseDueDate(JSONObject o, Task task) {
        JSONObject dueJson = o.getJSONObject("due-date");
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, dueJson.getInt("year"));
        cal.set(Calendar.MONTH, dueJson.getInt("month"));
        cal.set(Calendar.DAY_OF_MONTH, dueJson.getInt("day"));
        cal.set(Calendar.HOUR_OF_DAY, dueJson.getInt("hour"));
        cal.set(Calendar.MINUTE, dueJson.getInt("minute"));
        task.setDueDate(new DueDate(cal.getTime()));
    }


    private void parsePriority(JSONObject o, Task task) {
        JSONObject priorityJson = o.getJSONObject("priority");
        boolean im = priorityJson.getBoolean("important");
        boolean ur = priorityJson.getBoolean("urgent");
        Priority pr = new Priority();
        pr.setUrgent(ur);
        pr.setImportant(im);
        task.setPriority(pr);
    }

    private void parseStatus(JSONObject o, Task task) {
        String status = o.getString("status");
        if (status.equals("TODO")) {
            task.setStatus(Status.TODO);
        } else if (status.equals("UP_NEXT")) {
            task.setStatus(Status.UP_NEXT);
        } else if (status.equals("IN_PROGRESS")) {
            task.setStatus(Status.IN_PROGRESS);
        } else if (status.equals("DONE")) {
            task.setStatus(Status.DONE);
        }
    }
    
    
}
