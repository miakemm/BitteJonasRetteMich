package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.IssueTrackingException;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.ProjectService;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Issue;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Milestone;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Severity;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Type;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Project;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.State;


public class ProjectServiceImpl implements ProjectService {

	private Project project = new Project();

	@Override
	public void createMilestone(String id, String name, Set<String> issueIds)
			throws IssueTrackingException {
		if (id == null || id.isEmpty() || !ValidationHelper.isId(id)) {
			throw new IssueTrackingException("ERROR with createMilestone: Milestone ID cannot be null or empty");
		}
		if (name == null || name.isEmpty()) {
			throw new IssueTrackingException("ERROR with createMilestone: Milestone name cannot be null or empty");
		}

		Milestone milestone = new Milestone();
		milestone.setId(id);
		milestone.setName(name);

		List<Issue> issues = new ArrayList<>();
		for (String issueId : issueIds) {
			issues.add(new Issue(issueId, "Issue " + issueId));
		}
		milestone.setIssues(issues);

		project.getMilestones().add(milestone);
	}

	@Override
	public List<Milestone> getMilestones() {
		return project.getMilestones();
	}

	public void printMilestones() {
		for (Milestone milestone : getMilestones()) {
			System.out.println(milestone);
		}
	}

	@Override
	public void removeMilestoneById(String id) throws IssueTrackingException {
		if (id == null || id.isEmpty()) {
			throw new IssueTrackingException("ERROR with removeMilestoneById: Milestone ID cannot be null or empty");
		}

		boolean removed = project.getMilestones().removeIf(milestone -> milestone.getId().equals(id));

		deleteReferences.deleteMilestoneRef(id,this);

		if (!removed) {
			throw new IssueTrackingException("Milestone with ID " + id + " not found");
		}
	}

	@Override
	public void createIssue(String id, String name, String description, Severity severity, Type type, String milestoneId, Set<String> dependencies) throws IssueTrackingException {
		if (id == null || id.isEmpty() || !ValidationHelper.isId(id)) {
			throw new IssueTrackingException("ERROR with createIssue: Issue ID cannot be null or empty");
		}
		if (name == null || name.isEmpty()) {
			throw new IssueTrackingException("ERROR with createIssue: Issue name cannot be null or empty");
		}
		if (description == null || description.isEmpty()) {
			throw new IssueTrackingException("ERROR with createIssue: Issue description cannot be null or empty");
		}
		if (severity == null) {
			throw new IssueTrackingException("ERROR with createIssue: Issue severity cannot be null");
		}
		if (type == null) {
			throw new IssueTrackingException("ERROR with createIssue: Issue type cannot be null");
		}
		if (milestoneId == null || milestoneId.isEmpty()) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone ID cannot be null or empty");
		}
		if (dependencies == null) {
			throw new IssueTrackingException("ERROR with createIssue: Issue dependencies cannot be null");
		}

		Issue issue = new Issue(id, name);
		issue.setDescription(description);
		issue.setSeverity(severity);
		issue.setType(type);
		issue.setState(State.OPEN);

		Milestone milestone = findMilestoneById(milestoneId);
		if (milestone == null) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone with ID " + milestoneId + " not found");
		}
		issue.setMilestone(milestone);

		List<Issue> dependencyIssues = new ArrayList<>();
		for (String dependencyId : dependencies) {
			Issue dependencyIssue = findIssueById(dependencyId);
			if (dependencyIssue == null) {
				throw new IssueTrackingException("ERROR with createIssue: Dependency issue with ID " + dependencyId + " not found");
			}
			dependencyIssues.add(dependencyIssue);
		}
		issue.setDependencies(dependencyIssues);

		project.getIssues().add(issue);
	}

	private Issue findIssueById(String issueId) {
		for (Issue issue : project.getIssues()) {
			if (issue.getId().equals(issueId)) {
				return issue;
			}
		}
		return null;
	}

	private Milestone findMilestoneById(String milestoneId) {
		for (Milestone milestone : project.getMilestones()) {
			if (milestone.getId().equals(milestoneId)) {
				return milestone;
			}
		}
		return null;
	}

	@Override
	public List<Issue> getIssues() {
		return project.getIssues();
	}

	public void printIssues() {
		for (Issue issue : getIssues()) {
			System.out.println(issue);
		}
	}

	@Override
	public void removeIssueById(String id) throws IssueTrackingException {
		if (id == null || id.isEmpty()) {
			throw new IssueTrackingException("ERROR with removeIssueById: Issue ID cannot be null or empty");
		}

		boolean removed = project.getIssues().removeIf(issue -> issue.getId().equals(id));

		deleteReferences.deleteIssueMilestoneRef(id,this);
		deleteReferences.deleteIssueRef(id,this);

		if (!removed) {
			throw new IssueTrackingException("Issue with ID " + id + " not found");
		}
	}

	@Override
	public void closeIssueById(String id) throws IssueTrackingException {
		for (Issue issue : getIssues()) {
			if (issue.getId().equals(id)) {
				issue.setState(State.CLOSED);
				return;
			}
		}
		throw new IssueTrackingException("Issue with ID " + id + " not found");
	}

	@Override
	public void printJsonToConsole() throws IssueTrackingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(project);
			System.out.println(json);
		} catch (IOException e) {
			throw new IssueTrackingException("ERROR with printJsonToConsole: " + e.getMessage(), e);
		}
	}

	@Override
	public void saveJsonToFile(String path) throws IssueTrackingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), project);
		} catch (IOException e) {
			throw new IssueTrackingException("ERROR with saveJsonToFile: " + e.getMessage(), e);
		}
	}

	@Override
	public void setProject(Project project) {
		this.project = project;
	}

}
