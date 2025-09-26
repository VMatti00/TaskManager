package se.kth.saeedvan.taskmanager.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

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
        created = LocalDate.now();
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

    public Task addTask(String description, TaskPrio prio){
        this.nextTaskId++;
        Task newTask = new Task(description, prio, this.nextTaskId);
        tasks.add(newTask);
        return newTask;
    }

    public boolean removeTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).equals(task)) {
                tasks.remove(i);
                return true;
            }
        }
        return false;
    }

    public ProjectState getState() {
        if (tasks.size() == 0)
            return ProjectState.EMPTY;
        for(Task t : tasks) {
            if (t.getState() != TaskState.DONE)
                return ProjectState.ONGOING;
        }
        return ProjectState.COMPLETED;
    }

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

    public Task getTaskById(int id) {
        for (Task t : tasks) {
            if (t.getId() == id)
                return t;
        }
        return null;
    }

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
        Collections.sort(tasks, c);
        return matchedTasks;
    }

    public int compareTo(Project project) {
        return this.title.compareTo(project.getTitle());
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj instanceof Project other)
        {
            return (this.title.equals(other.title));
        }
        return false;
    }

    @Override
    public String toString() {
        return "Project{" +
                "tasks=" + tasks +
                ", title='" + title + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", nextTaskId=" + nextTaskId +
                '}';
    }
}
