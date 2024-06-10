package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model;

import java.util.LinkedList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(property = "id",generator = ObjectIdGenerators.PropertyGenerator.class)
public class Milestone {
	@JsonProperty(value = "id",required = true)
	private String id;
	@JsonProperty(value = "name",required = true)
	private String name;
	@JsonProperty(value = "issues",required = false)
	private List<Issue> issues = new LinkedList<>();
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
}
