package se.kth.saeedvan.taskmanager.model;

public class TitleNotUniqueException extends  RuntimeException{
    public TitleNotUniqueException (String str) {
        super(str);
    }
    public TitleNotUniqueException() {
        super();
    }
}
