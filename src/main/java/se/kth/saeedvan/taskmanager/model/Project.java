package se.kth.saeedvan.taskmanager.model;

import se.kth.saeedvan.taskmanager.model.matcher.ITaskMatcher;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * This class represents logic and data for a project that contains the list of tasks created by the user.
 * <p>
 * Handles the tasks and stores them in an internal list for further handling from the user.
 *
 * @author Saeed Kassab
 * @author Van Matti
 * @serial
 */
public class Project implements Comparable<Project>, Serializable {
    private List<Task> tasks;
    private String title;
    private final int id;
    private String description;
    private LocalDate created;
    private int nextTaskId;


    protected Project(String title, String description, int id) {
        this.tasks = new ArrayList<>();
        this.title = title;
        this.description = description;
        this.id = id;
        this.created = LocalDate.now();
        this.nextTaskId = 1;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getNextTaskId() {
        return nextTaskId;
    }

    /**
     * Creates a new task with the given description and priority.
     * The task is added to the list {@code tasks}, {@code nextTaskId} variable is incremented.
     *
     * @param description description of the task
     * @param prio priority of the task
     * @return the newly created task
     */
    public Task addTask(String description, TaskPrio prio){
        Task newTask = new Task(description, prio, this.nextTaskId);
        tasks.add(newTask);
        this.nextTaskId++;
        return newTask;
    }

    /**
     * Removes a given task from this project and removes from the list {@code tasks}.
     *
     * @param task to be removed
     * @return true if successfully removed the task, otherwise returns false.
     */
    public boolean removeTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).equals(task)) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the state of the project based on the state of its tasks.
     * If there are no tasks, the stat is {@link ProjectState#EMPTY}.
     * If all tasks is not {@link TaskState#DONE}, the state is {@link ProjectState#ONGOING}.
     * If all tasks are {@link TaskState#DONE}, the state is {@link ProjectState#COMPLETED}.
     *
     * @return the current {@link ProjectState} of this project
     */
    public ProjectState getState() {
        if (tasks.size() == 0)
            return ProjectState.EMPTY;
        for(Task t : tasks) {
            if (t.getState() != TaskState.DONE)
                return ProjectState.ONGOING;
        }
        return ProjectState.COMPLETED;
    }

    /**
     * Returns the {@link LocalDate} of the latest updated task in the project.
     * If there are no tasks in the project, then the projects creation date is returned.
     *
     * @return the date of the latest update or the creation date if there are no tasks
     */
    public LocalDate getLastUptaded() {
        LocalDate lastUpDated = LocalDate.ofEpochDay(0);
        if (tasks.size() == 0)
            return this.created;

        for (Task t : tasks) {
            if (lastUpDated.isBefore(t.getLastUpdate()))
                lastUpDated = t.getLastUpdate();
        }
        return lastUpDated;
    }

    /**
     * Returns the searched task by the given id.
     * If the id deosnt match any task id then null is returned.
     *
     * @param id
     * @return Returns the searched task by the given id or null
     */
    public Task getTaskById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id)
                return t;
        }
        return null;
    }

    /**
     * Finds all the tasks in this project that matches the given criteria.
     * The matching is made by the given {@link ITaskMatcher}.
     * The matched tasks are returned in descending order of their id.
     *
     * @param matcher criteria used to find the tasks
     * @return a list of tasks that matches the criteria
     */
    public List<Task> findTasks(ITaskMatcher matcher) {
        List<Task> matchedTasks = new ArrayList<>();
        for (Task t : this.tasks) {
            if (matcher.match(t))
                matchedTasks.add(t);
        }
        Comparator<Task> c = (Task t1, Task t2) -> {
            if (t1.getId() > t2.getId())
                return -1;
            else if (t1.getId() < t2.getId())
                return 1;
            else return 0;
        };
        Collections.sort(matchedTasks, c);
        return matchedTasks;
    }

    /**
     * Compares title of this project to the given parameter.
     *
     * @param project the object to be compared.
     * @return value {@code 0} if the title match, value not {@code 0} otherwise.
     */
    public int compareTo(Project project) {
        return this.title.compareTo(project.getTitle());
    }

    /**
     * Compares if this project title and the other project title has the same title.
     *
     * @param obj object to comapre with
     * @return true if the given object is equal to this task, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj instanceof Project other)
        {
            return (this.title.equals(other.title));
        }
        return false;
    }

    public List<Task> getTasks() {
        return List.copyOf(this.tasks);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String str = "*Project:  | description:  | id:  | created: *" + "0000-00-00";
        int strSize = title.length() + description.length() + Integer.toString(id).length() + str.length();
        builder.append("\n");
        for (int i = 0; i < strSize; i++) {
            builder.append('*');
        }
        builder.append ("\n*Project: " + title + " | " + "description: " + description + " | " + "id: " + id + " | " +
                "created: " + created + "*\n");
        builder.append("*".repeat(Math.max(0, strSize)));
        builder.append("\n");
        for (Task t : tasks) {
            builder.append(t.toString()).append("\n");
        }
        return builder.toString();
    }
}
