package se.kth.saeedvan.taskmanager.model.matcher;

import se.kth.saeedvan.taskmanager.model.Task;

public class TakenByMatcher implements ITaskMatcher{
    private String takenBy;

    public TakenByMatcher(String takenBy) {
        this.takenBy = takenBy;
    }
    public boolean match(Task task) { return this.takenBy.equalsIgnoreCase(task.getTakenBy()); }
}
