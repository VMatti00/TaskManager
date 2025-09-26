package se.kth.saeedvan.taskmanager.model;

public class NotDoneMatcher implements ITaskMatcher {
    public boolean match(Task task) {
        return !(TaskState.DONE == task.getState());
    }
}
