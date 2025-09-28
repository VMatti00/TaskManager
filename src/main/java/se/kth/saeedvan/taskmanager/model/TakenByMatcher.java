package se.kth.saeedvan.taskmanager.model;

import java.util.Objects;

public class TakenByMatcher implements ITaskMatcher{
    private String takenBy;

    public TakenByMatcher(String takenBy) {
        this.takenBy = takenBy;
    }
    public boolean match(Task task) { return this.takenBy.equalsIgnoreCase(task.getTakenBy()); }
}
