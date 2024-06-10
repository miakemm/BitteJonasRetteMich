package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(property = "id",generator = ObjectIdGenerators.PropertyGenerator.class)
public class Issue {

	@JsonProperty (value = "id",required = true)
	private String id;
	@JsonProperty (value = "name",required = true)
	private String name;
	@JsonProperty(value = "description",required = false)
	private String description;
	@JsonProperty(value = "severity",required = true)
	private Severity severity;
	@JsonProperty(value = "type",required = true)
	private Type type;
	@JsonProperty(value = "state",required = true)
	private State state;
	@JsonProperty(value = "milestone",required = false)
	private Milestone milestone;

	private List<Issue> dependencies = new LinkedList<>();
	@JsonIdentityReference(alwaysAsId = true)
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

	public void setDependencies(List<Issue> dependencies) {
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
	public Issue(String issueId, String s) {
	}

}
