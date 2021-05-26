package model;

import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo {

//    private String description;
    private List<Todo> tasks;
    
    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        tasks = new ArrayList<>();
    }
    
    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task) && task != this) {
            tasks.add(task);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }

//    public void setPriority(Priority priority) {
//        this.priority = priority;
//    }

//    public Priority getPriority() {
//        return priority;
//    }
    
    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        int toReturn = 0;
        for (Todo t : tasks) {
            toReturn = toReturn + t.getEstimatedTimeToComplete();
        }
        return toReturn;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        double numerator = 0;
        double denominator = 0;
        double result;
        for (Todo t : tasks) {
            numerator = numerator + t.getProgress();
            denominator = denominator + 100;
        }
        result = (numerator / denominator) * 100;
        return (int) java.lang.Math.floor(result); //stub
    }

    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
    //     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }
    
    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }


    private class ProjectIterator implements Iterator<Todo> {
        private Iterator<Todo> tasksIT;
        private Todo toReturn;
        private int condition;
        private int count;

        public ProjectIterator() {
            tasksIT = tasks.iterator();
            condition = 1;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < tasks.size() && condition < 5;
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            toReturn = null;
            while (tasksIT.hasNext() && toReturn == null) {
                Todo it = tasksIT.next();
                toReturn = fitCondition(it.getPriority()) ? it : null;
                if (!tasksIT.hasNext() && hasNext()) {
                    tasksIT = tasks.iterator();
                    condition++;
                }
            }
            count++;
            return toReturn;
        }

        private boolean fitCondition(Priority priority) {
            return priority.equals(new Priority(condition));
        }

    }
}