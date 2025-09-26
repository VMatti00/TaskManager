package model;

public enum ProjectState {
    EMPTY("Empty"), ONGOING("Ongoing"), COMPLETED("Completed");

    private final String str;

    private ProjectState(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
