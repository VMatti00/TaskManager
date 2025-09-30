package se.kth.saeedvan.taskmanager.model.exceptions;

public class TitleNotUniqueException extends RuntimeException{
    public TitleNotUniqueException (String msg) {
        super(msg);
    }
    public TitleNotUniqueException() {
        super();
    }
}
