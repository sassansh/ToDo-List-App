package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.json.JSONObject.NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static persistence.Jsonifier.*;

public class TestJsonifier {
    // TODO: design tests for Jsonifier class
    private List<Task> testTasks;
    private JSONObject json;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;

    @BeforeEach
    public void setUp() {
        testTasks = new ArrayList<>();
        task1 = new Task("first task");
        task1.setPriority(new Priority(2));
        task1.setStatus(Status.UP_NEXT);
        task1.addTag("tag1");
        task2 = new Task("second task");
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DAY_OF_MONTH, 10);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 59);
        task2.setDueDate(new DueDate(cal.getTime()));
        task2.setPriority(new Priority(1));
        task2.setStatus(Status.IN_PROGRESS);
        task2.addTag("tag2");
        task2.addTag("tag3");
        task3 = new Task("third task");
        cal = new GregorianCalendar();
        cal.set(Calendar.DAY_OF_MONTH, 5);
        cal.set(Calendar.HOUR_OF_DAY, 19);
        cal.set(Calendar.MINUTE, 0);
        task3.setDueDate(new DueDate(cal.getTime()));
        task3.setPriority(new Priority(3));
        task3.setStatus(Status.DONE);
        task3.addTag("tag3");
        task3.addTag("tag4");
        task4 = new Task("fourth task");
        cal = new GregorianCalendar();
        cal.set(Calendar.DAY_OF_MONTH, 20);
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 30);
        task4.setDueDate(new DueDate(cal.getTime()));
        testTasks.add(task1);
        testTasks.add(task2);
        testTasks.add(task3);
        testTasks.add(task4);
        json = new JSONObject();
    }

    @Test
    public void testConstructor() {
        Jsonifier j = new Jsonifier();
    }

    @Test
    public void testTagJson() {
        Tag tag = new Tag("tag1");
        json.put("name","tag1");
        assertEquals(json.get("name"), tagToJson(tag).get("name"));
    }

    @Test
    public void testPriorityJson() {
        Priority p = new Priority(2);
        json.put("important",true);
        json.put("urgent",false);
        assertEquals(json.get("important"), priorityToJson(p).get("important"));
        assertEquals(json.get("urgent"), priorityToJson(p).get("urgent"));
    }

    @Test
    public void testDueDateJson() {
        DueDate due = new DueDate();
        Calendar cal = new GregorianCalendar();
        cal.setTime(due.getDate());
        json.put("year",cal.get(Calendar.YEAR));
        json.put("month",cal.get(Calendar.MONTH));
        json.put("day",cal.get(Calendar.DAY_OF_MONTH));
        json.put("hour",cal.get(Calendar.HOUR_OF_DAY));
        json.put("minute",cal.get(Calendar.MINUTE));
        assertEquals(json.get("year"),dueDateToJson(due).get("year"));
        assertEquals(json.get("month"),dueDateToJson(due).get("month"));
        assertEquals(json.get("day"),dueDateToJson(due).get("day"));
        assertEquals(json.get("hour"),dueDateToJson(due).get("hour"));
        assertEquals(json.get("minute"),dueDateToJson(due).get("minute"));
    }

    @Test
    public void testTaskJson() {
        Tag tag1 = new Tag("tag1");
        json.put("description",task1.getDescription());
        JSONArray jArray = new JSONArray();
        jArray.put(tagToJson(tag1));
        json.put("tags",jArray);
        json.put("due-date",NULL);
        json.put("priority",priorityToJson(task1.getPriority()));
        json.put("status",task1.getStatus());
        assertEquals(json.get("description"), taskToJson(task1).get("description"));
        assertEquals(json.get("tags").toString(), taskToJson(task1).get("tags").toString());
        assertEquals(json.get("due-date"), taskToJson(task1).get("due-date"));
        assertEquals(json.get("priority").toString(), taskToJson(task1).get("priority").toString());
        assertEquals(json.get("status"), taskToJson(task1).get("status"));
    }

    @Test
    public void testTaskListJson() {
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        Tag tag4 = new Tag("tag4");
        json.put("description",task1.getDescription());
        JSONArray jArray = new JSONArray();
        jArray.put(tagToJson(tag1));
        json.put("tags",jArray);
        json.put("due-date",NULL);
        json.put("priority",priorityToJson(task1.getPriority()));
        json.put("status",task1.getStatus());
        JSONObject json2 = new JSONObject();
        json2.put("description",task2.getDescription());
        jArray = new JSONArray();
        jArray.put(tagToJson(tag2));
        jArray.put(tagToJson(tag3));
        json2.put("tags",jArray);
        json2.put("due-date",dueDateToJson(task2.getDueDate()));
        json2.put("priority",priorityToJson(task2.getPriority()));
        json2.put("status",task2.getStatus());
        JSONObject json3 = new JSONObject();
        json3.put("description",task3.getDescription());
        jArray = new JSONArray();
        jArray.put(tagToJson(tag3));
        jArray.put(tagToJson(tag4));
        json3.put("tags",jArray);
        json3.put("due-date",dueDateToJson(task3.getDueDate()));
        json3.put("priority",priorityToJson(task3.getPriority()));
        json3.put("status",task3.getStatus());
        JSONObject json4 = new JSONObject();
        json4.put("description",task4.getDescription());
        json4.put("tags",new JSONArray());
        json4.put("due-date",dueDateToJson(task4.getDueDate()));
        json4.put("priority",priorityToJson(task4.getPriority()));
        json4.put("status",task4.getStatus());
        JSONArray taskArray = new JSONArray();
        taskArray.put(json);
        taskArray.put(json2);
        taskArray.put(json3);
        taskArray.put(json4);
        assertEquals(taskArray.length(),taskListToJson(testTasks).length());
        assertEquals(taskArray.toString(),taskListToJson(testTasks).toString());
    }

    @Test
    public void print() {
        String jsonString = testTasks.toString();
        List<String> list = Arrays.asList(jsonString);
        System.out.println(list.get(0));
        String st = "apple_\n_peach_\n_me and you";
        List<String> myList = Arrays.asList(st.split("\n"));
        System.out.println(myList);
    }
}
