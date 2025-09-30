package se.kth.saeedvan.taskmanager.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * This class represents logic and data for a task.
 * <p>
 * Does not handle storing and printing of elements.
 *
 * @author Saeed Kassab
 * @author Van Matti
 * @serial
 */
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

    /**
     * Sets which user has taken the task by the given parameter {@code takenBy}.
     * If the task is already taken, the variable remains unchanged and an exception is thrown.
     *
     * @param takenBy the username of the user who wants to take the task
     * @throws IllegalStateException if the task is already taken
     */
    public void setTakenBy(String takenBy) throws IllegalStateException {
        if(this.takenBy != null)
            throw new IllegalStateException("Already taken!");

        this.takenBy = takenBy;
        this.lastUpdate = LocalDate.now();
    }

    /**
     * Updates the current state.
     * Updates the {@code lastUpdate} timestamp to the current date.
     *
     * @param state the new state to set
     */
    public void setState(TaskState state) {
        this.state = state;
        this.lastUpdate = LocalDate.now();
    }

    /**
     * Updates the current priority.
     * Updates the {@code lastUpdate} timestamp to the current date.
     *
     * @param prio the new priority to set
     */
    public void setPrio(TaskPrio prio) {
        this.prio = prio;
        this.lastUpdate = LocalDate.now();
    }

    /**
     * Compares which task has the higher, lower or equal priority. In case of equal priority description is compared.
     *
     * @param other the task to be compared
     * @return a negative number if this task's priority is less than the other tasks priority,
     *          a positive number if this task's priority is greater than the other tasks priority,
     *          zero if they have the same priority and then compares the description.
     */
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

    /**
     * Compares if this task and the other task has the same priority and description.
     *
     * @param obj the object to compare with
     * @return {@code true} if the given object is equal to this task, {@code false} otherwise.
     */
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
