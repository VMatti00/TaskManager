package se.kth.saeedvan.taskmanager.model;

public enum TaskPrio {
    LOW(1), MEDIUM(2), HIGH(3);

    private int value;

    private TaskPrio(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
