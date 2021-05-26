package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static org.json.JSONObject.NULL;


// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name",tag.getName());
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();
        priorityJson.put("important",priority.isImportant());
        priorityJson.put("urgent",priority.isUrgent());
        return priorityJson;
    }

    // EFFECTS: returns JSON representation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();
        if (dueDate == null) {
            dueDateJson = null;
        } else {
            Calendar cal = new GregorianCalendar();
            cal.setTime(dueDate.getDate());
            dueDateJson.put("year", cal.get(Calendar.YEAR));
            dueDateJson.put("month",cal.get(Calendar.MONTH));
            dueDateJson.put("day",cal.get(Calendar.DAY_OF_MONTH));
            dueDateJson.put("hour",cal.get(Calendar.HOUR_OF_DAY));
            dueDateJson.put("minute",cal.get(Calendar.MINUTE));
        }
        return dueDateJson;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();
        taskJson.put("description",task.getDescription());

        JSONArray tagsList = new JSONArray();
        for (Tag t : task.getTags()) {
            tagsList.put(tagToJson(t));
        }
        taskJson.put("tags",tagsList);

        if (dueDateToJson(task.getDueDate()) == null) {
            taskJson.put("due-date",NULL);
        } else {
            taskJson.put("due-date",dueDateToJson(task.getDueDate()));
        }
        taskJson.put("priority",priorityToJson(task.getPriority()));
        taskJson.put("status",task.getStatus());

        return taskJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray listTaskJson = new JSONArray();
        for (Task t : tasks) {
            listTaskJson.put(taskToJson(t));
        }
        return listTaskJson;
    }

}
