package se.kth.saeedvan.taskmanager.ui;



import se.kth.saeedvan.taskmanager.model.*;
import se.kth.saeedvan.taskmanager.model.matcher.ITaskMatcher;
import se.kth.saeedvan.taskmanager.model.matcher.NotDoneMatcher;
import se.kth.saeedvan.taskmanager.model.matcher.PrioMatcher;
import se.kth.saeedvan.taskmanager.model.matcher.TakenByMatcher;

import java.util.List;
import java.util.Scanner;

/**
 * User interactions for a specific project, current project.
 * The user selects actions on current project in the projectLoop method.
 */
class CurrentProjectUI {
    private Project currentProject;
    private final Scanner scan;

    // package private visibility - only visible to other classes in
    // package ui - intended for MainUI.
    CurrentProjectUI(Scanner scan) {
        this.scan = scan;
        this.currentProject = null; // TODO: Ugly!
    }

    void setCurrentProject(Project project) {
        this.currentProject = project;
        projectLoop();
    }

    Project getCurrentProject() {
        return currentProject;
    }

    void projectLoop() {
        char choice;
        do {
            printCurrentProjectMenu();
            choice = InputUtils.scanAndReturnFirstChar(scan);

            switch (choice) {
                case 'V':
                    printTasks(currentProject.getTasks());
                    break;
                case 'T':
                    System.out.print("Name? ");
                    String takenBy = scan.nextLine();
                    viewTasks(new TakenByMatcher(takenBy));
                    break;
                case 'N':
                    viewTasks(new NotDoneMatcher());
                    break;
                case 'H':
                    viewTasks(new PrioMatcher(TaskPrio.HIGH));
                    break;
                case 'L':
                    viewTasks(new PrioMatcher(TaskPrio.LOW));
                    break;
                case 'A':
                    addTask();
                    break;
                case 'U':
                    updateTask();
                    break;
                case 'R':
                    removeTask();
                    break;
                case 'X':
                    break;
                default:
                    System.out.println("Unknown command");
            }

        } while (choice != 'X');
    }

    private void viewTasks(ITaskMatcher matcher) {
        //System.out.println(currentProject.toString());
        List<Task> tasks = currentProject.findTasks(matcher);
        printTasks(tasks);
    }

    private void addTask() {
        System.out.print("Description? ");
        String descr = scan.nextLine();
        System.out.print("Priority (L)ow, (M)edium, (H)igh? ");
        char prioChar = InputUtils.scanAndReturnFirstChar(scan);
        TaskPrio prio = prioChar == 'H' ? TaskPrio.HIGH : prioChar == 'M' ? TaskPrio.MEDIUM : TaskPrio.LOW;
        currentProject.addTask(descr, prio);
    }

    private void removeTask() {
        System.out.println("Task id?");
        String input = scan.nextLine();
        int id = Integer.parseInt(input);
        Task task = currentProject.getTaskById(id);
        if (task != null) {
            System.out.println("Removing...\n" + task.toString());
            currentProject.removeTask(task);
        }
        else {
            System.out.println("Id not found.");
        }
    }

    private void updateTask() {
        System.out.print("Task id? ");
        int id = scan.nextInt();
        scan.nextLine(); //remove "new line" from scanner buffer
        Task task = currentProject.getTaskById(id);
        if (task != null) {
            System.out.println(task);
            System.out.print("New state (T)odo (P)In progress (D)one? ");
            char stateChar = InputUtils.scanAndReturnFirstChar(scan);
            if (stateChar == 'T') {
                System.out.print("Taken by (name or email address)? ");
                String emailStr = scan.nextLine();
                task.setState(TaskState.TO_DO);
                try {
                    task.setTakenBy(emailStr);
                }
                catch(IllegalStateException e) {
                    System.out.println("Task already taken !!!");
                }
            }
            else if (stateChar == ('P')) {
                if (task.getTakenBy() == null) {
                    System.out.println("Task needs to taken first");
                }
                else
                    task.setState(TaskState.IN_PROGRESS);
            }
            else if(stateChar == ('D')) {
                if (task.getState() != TaskState.IN_PROGRESS) {
                    System.out.println("Task needs to be in progress first");
                }
                else
                    task.setState(TaskState.DONE);
            }
            else {
                System.out.println("Unknown command");
            }
        } else {
            System.out.println("Id not found.");
        }
    }

    private void printCurrentProjectMenu() {
        System.out.println("--- Manage " + currentProject.getTitle() + " ---");
        System.out.println("V - list all tasks");
        System.out.println("T - list tasks taken by ...");
        System.out.println("N - list tasks not done");
        System.out.println("H - list high priority tasks");
        System.out.println("L - list low priority tasks");
        System.out.println("A - add task");
        System.out.println("U - update task");
        System.out.println("R - remove task");
        System.out.println("X - exit project menu");
        System.out.println("---------------------");
    }

    private void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks added");
        } else {
            for (Task task : tasks) {
                System.out.println(task.toString());
            }
        }
    }
}
