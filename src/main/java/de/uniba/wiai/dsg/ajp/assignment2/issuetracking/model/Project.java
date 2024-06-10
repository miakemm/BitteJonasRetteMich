package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model;

import java.util.LinkedList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {

	@JsonProperty(value = "issues",required = false)
	private List<Issue> issues = new LinkedList<>();
	@JsonProperty(value = "milestones",required = false)
	private List<Milestone> milestones = new LinkedList<>();

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public List<Milestone> getMilestones() {
		return milestones;
	}

	public void setMilestones(List<Milestone> milestones) {
		this.milestones = milestones;
	}

}
