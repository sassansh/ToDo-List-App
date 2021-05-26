package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {

    private Project project;

    @BeforeEach
    public void setUp() {
        project = new Project("pro");
    }

    @Test
    public void testConstructor() {
        try {
            assertEquals("pro", project.getDescription());
            assertEquals(0, project.getNumberOfTasks());
            assertEquals(0, project.getEstimatedTimeToComplete());
            assertEquals(0, project.getProgress());
        } catch (Exception e) {
            fail("EmptyStringException should not be thrown here");
        }
    }

    @Test
    public void testEmptyConstructor() {
        try {
            project = new Project("");
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
        try {
            String d = null;
            project = new Project(d);
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testAdd() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        try {
            project.add(t1);
            project.add(t2);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertEquals(2,project.getNumberOfTasks());
        try {
            assertTrue(project.contains(t1));
            assertTrue(project.contains(t2));
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        try {
            project.add(t1);
            project.add(t2);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertEquals(2,project.getNumberOfTasks());
        try {
            assertTrue(project.contains(t1));
            assertTrue(project.contains(t2));
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
    }

    @Test
    public void testAddNull() {
        Task t0 = null;
        try {
            project.add(t0);
            fail("NullArgumentException expected");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testContainsNull() {
        Task t0 = null;
        try {
            project.contains(t0);
            fail("NullArgumentException expected");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testRemove() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        try {
            project.add(t1);
            project.add(t2);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        try {
            project.remove(t1);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertEquals(1, project.getNumberOfTasks());
        try {
            assertFalse(project.contains(t1));
            assertTrue(project.contains(t2));
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        project.remove(t1);
        assertEquals(1, project.getNumberOfTasks());
        try {
            assertFalse(project.contains(t1));
            assertTrue(project.contains(t2));
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
    }

    @Test
    public void testRemoveNull() {
        Task t0 = null;
        try {
            project.remove(t0);
            fail("NullArgumentException expected");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testGet0Progress() {
        assertEquals(0, project.getProgress());
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        project.add(t1);
        project.add(t2);
        assertEquals(0, project.getProgress());
    }

    @Test
    public void testGetSomeProgress() {
        assertEquals(0, project.getProgress());
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        t2.setProgress(100);
        project.add(t1);
        project.add(t2);
        assertEquals(50, project.getProgress());
        t1.setProgress(50);
        t2.setProgress(25);
        assertEquals(37, project.getProgress());
    }

    @Test
    public void testGet100Progress() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        t1.setProgress(100);
        t2.setProgress(100);
        project.add(t1);
        project.add(t2);
        assertEquals(100, project.getProgress());
    }

    @Test
    public void testNoTaskIsComplete() {
        assertFalse(project.isCompleted());
    }

    @Test
    public void testPartiallyCompleted() {
        assertFalse(project.isCompleted());
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        t1.setStatus(Status.DONE);
        project.add(t1);
        project.add(t2);
        assertFalse(project.isCompleted());
    }

    @Test
    public void testCompleted() {
        assertFalse(project.isCompleted());
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        t1.setProgress(100);
        t2.setProgress(100);
        project.add(t1);
        project.add(t2);
        assertTrue(project.isCompleted());
    }

    @Test
    public void testGetTasks() {
        assertEquals(0, project.getNumberOfTasks());
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        project.add(t1);
        project.add(t2);
        assertTrue(project.contains(t1));
        assertTrue(project.contains(t2));
        List<Task> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        assertEquals(2, project.getNumberOfTasks());
        try {
            project.getTasks();
            fail("UnsupportedOperationException expected");
        } catch (Exception e) {
            // expected
        }
    }

    @Test
    public void testEquals() {
        assertTrue(project.equals(new Project("pro")));
        assertTrue(project.equals(project));
        assertFalse(project.equals(new Task("what")));
        project.hashCode();
    }

    @Test
    public void testGetETCHours() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        Task t3 = new Task("t3");
        Project p1 = new Project("p1");
        t1.setEstimatedTimeToComplete(10);
        t2.setEstimatedTimeToComplete(20);
        t3.setEstimatedTimeToComplete(5);
        p1.add(t1);
        p1.add(t2);
        project.add(t3);
        project.add(p1);
        assertEquals(35, project.getEstimatedTimeToComplete());
        assertEquals(30, p1.getEstimatedTimeToComplete());
    }

    @Test
    public void testCannotAddItself() {
        project.add(project);
        assertEquals(project.getNumberOfTasks(), 0);
    }

    @Test
    public void testGetPriority() {
        project.setPriority(new Priority(2));
        assertFalse(project.getPriority().isUrgent());
        assertTrue(project.getPriority().isImportant());
    }

    @Test
    public void testNoSubProject() {
        StringBuilder str = new StringBuilder();
        for (Todo t : project) {
            str.append(t.toString());
        }
        assertEquals(str.length(), 0);
    }

    @Test
    public void testTwoSubsDifferentPriority() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        Task t3 = new Task("t3");
        Project p1 = new Project("p1");
        p1.add(t1);
        p1.add(t2);
        t3.setPriority(new Priority(2));
        project.add(p1);
        project.add(t3);
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    public void testSubsSamePriority() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        Task t3 = new Task("t3");
        Project p1 = new Project("p1");
        p1.add(t1);
        p1.add(t2);
        project.add(t3);
        project.add(p1);
        project.add(t1);
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    public void testManySubs() {
        Task t1 = new Task("t1");
        Task t2 = new Task("t2");
        Task t3 = new Task("t3");
        Project p1 = new Project("p1");
        Project p2 = new Project("p2");
        p1.add(t1);
        p1.add(t2);
        p2.add(t3);
        p2.setPriority(new Priority(1));
        t1.setPriority(new Priority(2));
        t2.setPriority(new Priority(3));
        project.add(t3);
        project.add(p1);
        project.add(t1);
        project.add(p2);
        project.add(t2);
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    public void testIteratorException() {
        Iterator<Todo> it = project.iterator();
        assertFalse(it.hasNext());
        try {
            it.next();
            fail("NoSuchElementException expected");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

}