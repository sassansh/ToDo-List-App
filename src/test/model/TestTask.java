package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestTask {
    // TODO: design tests for new behaviour added to Task class

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task("new task");
    }

    @Test
    public void testConstructor() {
        try {
            assertEquals("new task", task.getDescription());
            assertEquals(0, task.getTags().size());
            assertEquals(Status.TODO, task.getStatus());
            assertNull(task.getDueDate());
            assertEquals("DEFAULT", task.getPriority().toString());
            assertEquals(task.getProgress(), 0);
            assertEquals(task.getEstimatedTimeToComplete(), 0);
        } catch (Exception e) {
            fail("EmptyStringException should not be thrown here");
        }
    }

    @Test
    public void testNullDescription() {
        try {
            task = new Task("");
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
        String d = null;
        try {
            task = new Task(d);
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testTwoTasksEqual() {
        assertEquals(new Task("new task"), task);
        task.setStatus(Status.UP_NEXT);
        assertNotEquals(new Task("new task"), task);

        task = new Task("new task");
        Task t1 = new Task("new task");
        t1.setDueDate(new DueDate(GregorianCalendar.getInstance().getTime()));
        assertNotEquals(t1, task);

        task = new Task("new task");
        Task t2 = new Task("new task");
        t2.setPriority(new Priority(2));
        assertNotEquals(t2, task);

        assertFalse(task.equals(new Task("another task")));
        assertTrue(task.equals(task));
        assertFalse(task.equals(new Tag("what")));
    }

    @Test
    public void testAddTagExceptionExpected() {
        Tag nullTag = null;
        try {
            task.addTag(nullTag);
            fail("NullArgumentException expected");
        } catch (NullArgumentException e) {
            //expected
        }
    }

    @Test
    public void testAddTagNoException() {
        Tag tag = new Tag("tag");
        try {
            task.addTag(tag);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertTrue(task.containsTag(tag));
        assertTrue(tag.containsTask(task));
    }

    @Test
    public void testAddManyTags() {
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        try {
            task.addTag(tag1);
            task.addTag(tag2);
            task.addTag(tag3);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertTrue(task.getTags().size() == 3);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        assertTrue(task.containsTag(tag3));
        assertTrue(tag1.containsTask(task));
        assertTrue(tag2.containsTask(task));
        assertTrue(tag3.containsTask(task));
    }

    @Test
    public void testAddExistingTag() {
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        try {
            task.addTag(tag1);
            task.addTag(tag2);
            task.addTag(tag1);
            task.addTag(tag2);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertTrue(task.getTags().size() == 2);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        assertTrue(tag1.containsTask(task));
        assertTrue(tag2.containsTask(task));
    }

    @Test
    public void testRemoveTagExceptionExpected() {
        Tag nullTag = null;
        try {
            task.removeTag(nullTag);
            fail("NullArgumentException expected");
        } catch (NullArgumentException e) {
            //expected
        }
    }

    @Test
    public void testRemoveTagNoException() {
        Tag tag = new Tag("tag");
        task.addTag(tag);
        assertTrue(tag.containsTask(task));
        assertTrue(task.containsTag(tag));
        try {
            task.removeTag(tag);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertFalse(task.containsTag(tag));
        assertFalse(tag.containsTask(task));
    }

    @Test
    public void testRemoveManyTags() {
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        task.addTag(tag1);
        task.addTag(tag2);
        task.addTag(tag3);
        assertTrue(task.containsTag(tag1));
        assertTrue(task.containsTag(tag2));
        assertTrue(task.containsTag(tag3));
        assertTrue(tag1.containsTask(task));
        assertTrue(tag2.containsTask(task));
        assertTrue(tag3.containsTask(task));
        try {
            task.removeTag(tag1);
            task.removeTag(tag2);
            task.removeTag(tag3);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertTrue(task.getTags().size() == 0);
        assertFalse(task.containsTag(new Tag("tag1")));
        assertFalse(task.containsTag(tag2));
        assertFalse(task.containsTag(tag3));
        assertFalse(tag1.containsTask(task));
        assertFalse(tag2.containsTask(task));
        assertFalse(tag3.containsTask(task));
    }

    @Test
    public void testRemoveNonExistingTag() {
        try {
            task.removeTag(new Tag("aTag"));
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertEquals(0, task.getTags().size());
        assertFalse(task.containsTag(new Tag("aTag")));
    }

    @Test
    public void testAddDiffTags() {
        try {
            task.addTag("tag1");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        try {
            task.addTag("tag2");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        assertEquals(2, task.getTags().size());
    }

    @Test
    public void testAddSameTags() {
        try {
            task.addTag("tag1");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        try {
            task.addTag("tag2");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        try {
            task.addTag("tag2");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        assertEquals(2, task.getTags().size());
    }

    @Test
    public void testAddEmptyTag() {
        try {
            task.addTag("");
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
        String t = null;
        try {
            task.addTag(t);
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testRemoveTag() {
        try {
            task.addTag("tag1");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        try {
            task.addTag("tag2");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        try {
            task.removeTag("tag1");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        assertEquals(1,task.getTags().size());
    }

    @Test
    public void testRemovingNonExistingTag() {
        try {
            task.addTag("tag1");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        try {
            task.addTag("tag2");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        try {
            task.removeTag("tag1");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        try {
            task.removeTag("tag3");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        assertEquals(1,task.getTags().size());
    }

    @Test
    public void testRemovingEmptyTag() {
        try {
            task.removeTag("");
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
        Tag t = null;
        try {
            task.removeTag(t);
            fail("NullArgumentException expected");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testContainsTag() {
        task.addTag("tag1");
        task.addTag("tag2");
        assertTrue(task.containsTag("tag1"));
        assertTrue(task.containsTag("tag2"));
        try {
            task.containsTag("");
            fail("EmptyStringException expected");
        } catch (Exception e) {
            //expected
        }
        String name = null;
        try {
            task.containsTag(name);
            fail("EmptyStringException expected");
        } catch (Exception e) {
            //expected
        }
    }

    @Test
    public void testGetTags() {
        Set<Tag> tags = new HashSet(task.getTags());
        assertEquals(tags, task.getTags());
        assertTrue(task.getTags().equals(Collections.unmodifiableSet(tags)));
    }

    @Test
    public void testSetPriority() {
        Priority p = new Priority(1);
        try {
            task.setPriority(p);
            assertTrue(task.getPriority().isUrgent());
            assertTrue(task.getPriority().isImportant());
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
    }

    @Test
    public void testSetNullPriority() {
        Priority p = null;
        try {
            task.setPriority(p);
            fail("NullArgumentException expected");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testSetStatus() {
        Status s = Status.UP_NEXT;
        try {
            task.setStatus(s);
        } catch (NullArgumentException e) {
            fail("NullArgumentException should not be thrown here");
        }
        assertEquals("UP NEXT", task.getStatus().toString());
    }

    @Test
    public void testSetNullStatus() {
        Status s = null;
        try {
            task.setStatus(s);
            fail("NullArgumentException expected");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    public void testSetDescription() {
        try {
            task.setDescription("CPSC 210 Project Phase 1");
        } catch (EmptyStringException e) {
            fail("EmptyStringException should not be thrown here");
        }
        assertEquals("CPSC 210 Project Phase 1", task.getDescription());
    }

    @Test
    public void testSetNullDescription() {
        try {
            task.setDescription("");
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
        String d = null;
        try {
            task.setDescription(d);
            fail("EmptyStringException expected");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    public void testSetDueDate() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 3);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        Date day = cal.getTime();
        DueDate due = new DueDate(day);
        task.setDueDate(due);
        String today = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.CANADA) + " "
                + cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.CANADA) + " "
                + cal.get(Calendar.DATE) + " "
                + cal.get(Calendar.YEAR) + " "
                + cal.get(Calendar.HOUR) + ":"
                + cal.get(Calendar.MINUTE) + " "
                + cal.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.CANADA);
        assertEquals(today, task.getDueDate().toString());
    }

    @Test
    public void testToString() {
        assertEquals("\n{\n\tDescription: new task" +
                "\n\tDue date: " +
                "\n\tStatus: TODO" +
                "\n\tPriority: DEFAULT" +
                "\n\tTags:  \n}", task.toString());
        task.addTag("tag1");
        task.addTag("tag2");
        DueDate due = new DueDate();
        task.setDueDate(due);
        task.setDescription("hello");
        task.setStatus(Status.IN_PROGRESS);
        task.setPriority(new Priority(2));
        assertEquals("\n{\n\tDescription: hello" +
                "\n\tDue date: " + due.toString() +
                "\n\tStatus: IN PROGRESS" +
                "\n\tPriority: IMPORTANT" +
                "\n\tTags: #tag1, #tag2\n}", task.toString());
//        task.setPriority(new Priority(4));
//        assertEquals("\nDescription: hello" +
//                "\nDue date: " + due.toString() +
//                "\nStatus: IN PROGRESS" +
//                "\nPriority: DEFAULT" +
//                "\nTags: #tag1, #tag2\n", task.toString());
    }

    @Test
    public void testSetProgress() {
        try {
            task.setProgress(20);
            assertEquals(20, task.getProgress());
        } catch (Exception e) {
            fail("InvalidProgressException should not be thrown here");
        }
        try {
            task.setProgress(120);
            fail("InvalidProgressException expected");
        } catch (InvalidProgressException e) {
            // expected
        }
        try {
            task.setProgress(-20);
            fail("InvalidProgressException expected");
        } catch (InvalidProgressException e) {
            // expected
        }
    }

    @Test
    public void testSetEstimatedTimeToComplete() {
        try {
            task.setEstimatedTimeToComplete(5);
            assertEquals(5, task.getEstimatedTimeToComplete());
        } catch (Exception e) {
            fail("NegativeInputException should not be thrown here");
        }
        try {
            task.setEstimatedTimeToComplete((-20));
            fail("NegativeInputException expected");
        } catch (Exception e) {
            // expected
        }
    }
}