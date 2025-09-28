package se.kth.saeedvan.taskmanager.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Comparable<Task>, Serializable {
    private String description;
    private final int id;
    private String takenBy;
    private TaskState state;
    private LocalDate lastUpdate;
    private TaskPrio prio;

    protected Task(String description, TaskPrio prio, int id) {
        this.description = description;
        this.prio = prio;
        this.id = id;
        this.takenBy = null;
    }

    public void setTakenBy(String takenBy) throws IllegalStateException {
        if(this.takenBy != null)
            throw new IllegalStateException("Already taken!!");

        this.takenBy = takenBy;
        this.lastUpdate = LocalDate.now();
    }
    public void setState(TaskState state) {
        this.state = state;
        this.lastUpdate = LocalDate.now();
    }
    public void setPrio(TaskPrio prio) {
        this.prio = prio;
        this.lastUpdate = LocalDate.now();
    }

    @Override
    public int compareTo(Task other) {
        if (this.prio.getValue() < other.prio.getValue())
            return -1;
        else if (this.prio.getValue() > other.prio.getValue())
            return 1;
        else if (this.prio == other.prio) {
            return this.description.compareTo(other.description);
        }
        return 0;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj instanceof Task otherTask)
        {
            return (otherTask.prio == this.prio && otherTask.description == this.description);
        }
        return false;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getTakenBy() {
        return takenBy;
    }

    public TaskState getState() {
        return state;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public TaskPrio getPrio() {
        return prio;
    }

    @Override
    public String toString() {
        return  "-----------------------\n" +
                "|       TASK          |\n" +
                "-----------------------\n" +
                " description: " + description + "\n" +
                " id: " + id + "\n" +
                " taken by: " + takenBy + "\n" +
                " state: " + state + "\n" +
                " prio: " + prio + "\n" +
                " last update: " + lastUpdate + "\n" +
                "-----------------------\n";
    }

}
