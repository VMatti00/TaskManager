package se.kth.saeedvan.taskmanager.model;

public class PrioMatcher implements ITaskMatcher {
    private TaskPrio prio;

    public PrioMatcher(TaskPrio prio) {
        this.prio = prio;
    }
    public boolean match(Task task) {
        return this.prio == task.getPrio();
    }
}
