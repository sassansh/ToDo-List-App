package parsers;

import model.*;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTaskParser {
    public TaskParser parser;
    // TODO: design tests for TaskParser class

    @Test
    public void testSampleString() {
        String input = "[\n" +
                "{\n" +
                "  \"description\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":16,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":true},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Download the syllabus. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"UP_NEXT\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Read the syllabus! \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":null,\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of assignments deadlines. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"assigns\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":17,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":true},\n" +
                "  \"status\":\"TODO\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Make note of quizzes and midterm dates. \",\n" +
                "  \"tags\":[{\"name\":\"exams\"},{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":22,\"hour\":17,\"minute\":10},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":false},\n" +
                "  \"status\":\"DONE\"\n" +
                "},\n" +
                "{\n" +
                "  \"description\":\"Update my calendar with those dates. \",\n" +
                "  \"tags\":[{\"name\":\"planning\"},{\"name\":\"cpsc210\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":0,\"day\":20,\"hour\":17,\"minute\":10},\n" +
                "  \"priority\":{\"important\":false,\"urgent\":false},\n" +
                "  \"status\":\"TODO\"\n" +
                "}\n" +
                "]";
        parser = new TaskParser();
        List<Task> list = parser.parse(input);
        Tag t1 = new Tag("cpsc210");
        Tag t2 = new Tag("assigns");
        Tag t3 = new Tag("exams");
        Tag t4 = new Tag("planning");

        Task task1 = new Task("Register for the course. ");
        task1.addTag(t1);
        Calendar cal1 = new GregorianCalendar();
        cal1.set(Calendar.MONTH, 0);
        cal1.set(Calendar.DAY_OF_MONTH, 16);
        cal1.set(Calendar.HOUR_OF_DAY, 23);
        cal1.set(Calendar.MINUTE, 59);
        DueDate due1 = new DueDate(cal1.getTime());
        task1.setDueDate(due1);
        task1.setPriority(new Priority(1));
        task1.setStatus(Status.IN_PROGRESS);
        Task task2 = new Task("Download the syllabus. ");
        task2.addTag(t1);
        DueDate due2 = null;
        task2.setDueDate(due2);
        task2.setStatus(Status.UP_NEXT);
        Task task3 = new Task("Read the syllabus! ");
        task3.addTag(t1);
        DueDate due3 = null;
        task3.setDueDate(due3);
        task3.setStatus(Status.TODO);
        Task task4 = new Task("Make note of assignments deadlines. ");
        task4.addTag(t1);
        task4.addTag(t2);
        Calendar cal4 = new GregorianCalendar();
        cal4.set(Calendar.MONTH, 0);
        cal4.set(Calendar.DAY_OF_MONTH, 17);
        cal4.set(Calendar.HOUR_OF_DAY, 23);
        cal4.set(Calendar.MINUTE, 59);
        DueDate due4 = new DueDate(cal4.getTime());
        task4.setDueDate(due4);
        task4.setPriority(new Priority(3));
        task4.setStatus(Status.TODO);
        Task task5 = new Task("Make note of quizzes and midterm dates. ");
        task5.addTag(t3);
        task5.addTag(t1);
        Calendar cal5 = new GregorianCalendar();
        cal5.set(Calendar.MONTH, 0);
        cal5.set(Calendar.DAY_OF_MONTH, 22);
        cal5.set(Calendar.HOUR_OF_DAY, 17);
        cal5.set(Calendar.MINUTE, 10);
        DueDate due5 = new DueDate(cal5.getTime());
        task5.setDueDate(due5);
        task5.setPriority(new Priority(2));
        task5.setStatus(Status.DONE);
        Task task6 = new Task("Update my calendar with those dates. ");
        task6.addTag(t4);
        task6.addTag(t1);
        Calendar cal6 = new GregorianCalendar();
        cal6.set(Calendar.MONTH, 0);
        cal6.set(Calendar.DAY_OF_MONTH, 20);
        cal6.set(Calendar.HOUR_OF_DAY, 17);
        cal6.set(Calendar.MINUTE, 10);
        DueDate due6 = new DueDate(cal6.getTime());
        task6.setDueDate(due6);
        task6.setStatus(Status.TODO);

        assertEquals(task1, list.get(0));
        assertEquals(task2, list.get(1));
        assertEquals(task3, list.get(2));
        assertEquals(task4, list.get(3));
        assertEquals(task5, list.get(4));
        assertEquals(task6, list.get(5));
        assertEquals(task1.getTags(), list.get(0).getTags());
        assertEquals(task2.getTags(), list.get(1).getTags());
        assertEquals(task3.getTags(), list.get(2).getTags());
        assertEquals(task4.getTags(), list.get(3).getTags());
        assertEquals(task5.getTags(), list.get(4).getTags());
        assertEquals(task6.getTags(), list.get(5).getTags());
    }


    @Test
    public void testPrint() {
        String input = "[\n" +
                "{\n" +
                "  \"description\":\"Register for the course. \",\n" +
                "  \"tags\":[{\"name\":\"cpsc210\"},{\"name\":\"cpsc221\"}],\n" +
                "  \"due-date\":{\"year\":2019,\"month\":2,\"day\":1,\"hour\":23,\"minute\":59},\n" +
                "  \"priority\":{\"important\":true,\"urgent\":false},\n" +
                "  \"status\":\"IN_PROGRESS\"\n" +
                "},\n" +
                "]";
        parser = new TaskParser();
        List<Task> list = parser.parse(input);
        System.out.println(list.toString());
    }
}

