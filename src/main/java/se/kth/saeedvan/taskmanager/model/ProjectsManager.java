package se.kth.saeedvan.taskmanager.model;

import java.util.ArrayList;
import java.util.List;

public class ProjectsManager {
    private List<Project> projects;
    private int nextProjectId;
    public ProjectsManager() {
        projects = new ArrayList<>();
        this.nextProjectId = 1;
    }

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

    public boolean isTitleUnique(String title) {
        for (Project p : projects) {
            if (p.getTitle().equalsIgnoreCase(title))
                return false;
        }
        return true;
    }

    public Project addProject(String title, String description) {
        if (!isTitleUnique(title))
            throw new TitleNotUniqueException("Title already taken by another project.");

        Project project = new Project(title, description, nextProjectId);
        this.projects.add(project);
        this.nextProjectId = getHighestId();
        this.nextProjectId++;
        return project;
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
    }

    public Project getProjectById(int id) {
        for (Project p : projects) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    public List<Project> findProjects(String titleStr) {
        List<Project> matchingProjects = new ArrayList<>();
        for (Project p : projects) {
            if (p.getTitle().toLowerCase().contains(titleStr.toLowerCase()))
                matchingProjects.add(p);
        }
        return matchingProjects;
    }

    public int getHighestId() {
        int highest = 0;
        for (Project p : projects) {
            if (p.getId() > highest)
                highest = p.getId();
        }
        return highest;
    }

    public List<Project> getProjects() {
        return List.copyOf(projects);
    }

    public int getNextProjectId() {
        return nextProjectId;
    }

//    @Override
//    public String toString() {
//        return "ProjectManager [projects=" + projects + ", nextProjectId=" + nextProjectId + "]";
//    }
}
