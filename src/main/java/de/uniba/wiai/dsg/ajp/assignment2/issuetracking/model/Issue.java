package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Issue {

	private String id;

	private String name;

	private String description;

	private Severity severity;

	private Type type;

	private State state;

	private Milestone milestone;

	private Set<String> dependencies = new LinkedList<>();

	public Issue(String issueId, String s) { // NEU EINGEFÜGT

	}



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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public List<Issue> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<String> dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public String toString() {
		String issueNames = "";
		for (Issue dependentIssue : dependencies) {
			issueNames += dependentIssue.getName() + " ";
		}
		String milestone = "";
		if (this.milestone != null) {
			milestone = this.milestone.getName();
		}

		return "Issue [id=" + id + ", name=" + name + ", description="
				+ description + ", severity=" + severity + ", type=" + type
				+ ", state=" + state + ", milestone=" + milestone
				+ ", dependsUponIssues=( " + issueNames + ")]";
	}

	public void setIssue(Issue issue) {
		this.id = issue.getId();
	}
}
