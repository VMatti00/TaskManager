package se.kth.saeedvan.taskmanager.model;

import se.kth.saeedvan.taskmanager.model.exceptions.TitleNotUniqueException;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents logic and data for a project manager that contains the list of projects created by the user.
 * <p>
 * Handles the projects and stores them in an internal list for further handling from the user.
 *
 * @author Saeed Kassab
 * @author Van Matti
 * @serial
 */
public class ProjectsManager {
    private List<Project> projects;
    private int nextProjectId;
    public ProjectsManager() {
        projects = new ArrayList<>();
        this.nextProjectId = 1;
    }

    /**
     * Empties out the list {@code projects} and then adds the objects from the given parameter {@code incomingProjects}.
     *
     * @param incomingProjects a project list containing tasks
     */
    public void setProjects(List<Project> incomingProjects) {
        this.projects.clear();
        for (Project p : incomingProjects) {
            this.nextProjectId = p.getId();
            addProject(p.getTitle(), p.getDescription());
        }
        for (int i = 0; i < projects.size(); i++) {
            for (Task t : incomingProjects.get(i).getTasks()) {
                Task createdTask = projects.get(i).addTask(t.getDescription(), t.getPrio());
                createdTask.setTakenBy(t.getTakenBy());
                createdTask.setPrio(t.getPrio());
                createdTask.setState(t.getState());
            }
        }

    }

    /**
     * Checks if the given {@code title} is unique within the {@code projects} list.
     *
     * @param title to be searched if is unique
     * @return {@code true} if the title does not already exist in the list, {@code false} otherwise
     */
    public boolean isTitleUnique(String title) {
        for (Project p : projects) {
            if (p.getTitle().equalsIgnoreCase(title))
                return false;
        }
        return true;
    }



    /**
     * Creates a new project with the given title and description.
     * The project is added to the list {@code projects}, {@code nextProjectId} variable is incremented.
     *
     * @param title of the new project
     * @param description of the new project
     * @throws TitleNotUniqueException if title is already taken
     * @return the newly created project
     */
    public Project addProject(String title, String description) {
        if (!isTitleUnique(title))
            throw new TitleNotUniqueException("Title already taken by another project.");

        Project project = new Project(title, description, nextProjectId);
        this.projects.add(project);
        this.nextProjectId = getHighestId();
        this.nextProjectId++;
        return project;
    }

    /**
     * Removes a project from {@code projects} list.
     *
     * @param project to be removed
     */
    public void removeProject(Project project) {
        this.projects.remove(project);
    }

    /**
     * Finds the project that match the given {@code id}.
     *
     * @param id to search for the project
     * @return the searched project if the id exists in the list, {@code null} otherwise
     */
    public Project getProjectById(int id) {
        for (Project p : projects) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    /**
     * Finds projects that contains the given {@code title}.
     *
     * @param titleStr to search for the projects
     * @return a list of projects with matching title
     */
    public List<Project> findProjects(String titleStr) {
        List<Project> matchingProjects = new ArrayList<>();
        for (Project p : projects) {
            if (p.getTitle().toLowerCase().contains(titleStr.toLowerCase()))
                matchingProjects.add(p);
        }
        return matchingProjects;
    }

    /**
     *
     * @return the highest project id
     */
    private int getHighestId() {
        int highest = 0;
        for (Project p : projects) {
            if (p.getId() > highest)
                highest = p.getId();
        }
        return highest;
    }

    /**
     *
     * @return copy of the list {@code projects}
     */
    public List<Project> getProjects() {
        return List.copyOf(projects);
    }

    /**
     *
     * @return the nextProjectId
     */
    public int getNextProjectId() {
        return nextProjectId;
    }

   @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Project p : projects) {
            builder.append(p.toString()).append("\n");
        }
        return builder.toString();
    }
}
