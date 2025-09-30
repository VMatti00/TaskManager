package se.kth.saeedvan.taskmanager.model.matcher;

import se.kth.saeedvan.taskmanager.model.Task;
import se.kth.saeedvan.taskmanager.model.TaskPrio;

public class PrioMatcher implements ITaskMatcher {
    private TaskPrio prio;

    public PrioMatcher(TaskPrio prio) {
        this.prio = prio;
    }
    public boolean match(Task task) {
        return this.prio == task.getPrio();
    }
}
