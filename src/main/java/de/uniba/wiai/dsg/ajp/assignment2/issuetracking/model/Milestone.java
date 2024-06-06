package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model;

import java.util.LinkedList;
import java.util.List;

public class Milestone {

	private String id;

	private String name;

	private List<Issue> issues = new LinkedList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	@Override
	public String toString() {
		String issueNames = "";
		for (Issue issue : issues) {
			issueNames += issue.getName() + " ";
		}
		return "Milestone [id=" + id + ", name=" + name + ", issues=( "
				+ issueNames + ")]";
	}

	public Milestone findMilestoneById(String milestoneId) { // neu eingefügt
		for (Milestone milestone : Project.getMilestones()) {
			if (milestone.getId().equals(milestoneId)) {
				return milestone;
			}
		}
		return null;
	}
}
