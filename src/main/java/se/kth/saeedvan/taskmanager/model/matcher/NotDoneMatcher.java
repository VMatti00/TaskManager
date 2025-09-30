package se.kth.saeedvan.taskmanager.model.matcher;

import se.kth.saeedvan.taskmanager.model.Task;
import se.kth.saeedvan.taskmanager.model.TaskState;

public class NotDoneMatcher implements ITaskMatcher {
    public boolean match(Task task) {
        return !(TaskState.DONE == task.getState());
    }
}
