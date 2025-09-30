package se.kth.saeedvan.taskmanager.model.matcher;

import se.kth.saeedvan.taskmanager.model.Task;

public interface ITaskMatcher {
    public boolean match(Task task);
}
