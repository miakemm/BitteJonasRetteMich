package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.IssueTrackingException;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.ProjectService;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Issue;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Milestone;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Severity;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Type;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Project;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.State;

import static de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.State.CLOSED;

public class ProjectServiceImpl implements ProjectService {

	@Override
	public void createMilestone(String id, String name, Set<String> issueIds)
			throws IssueTrackingException {
		if (id == null || id.isEmpty() || !ValidationHelper.isId(id)) {
			throw new IssueTrackingException("ERROR with createMilestone: Milestone ID cannot be null or empty");
		}
		if (name == null || name.isEmpty()) {
			throw new IssueTrackingException("ERROR with createMilestone: Milestone name cannot be null or empty");
		}
		if (issueIds == null || issueIds.isEmpty()) {
			throw new IssueTrackingException("ERROR with createMilestone: Milestone IDs cannot be null or empty");
		}

		Milestone milestone = new Milestone();
		milestone.setId(id);
		milestone.setName(name);

		List<Issue> issues = new ArrayList<>();
		for (String issueId : issueIds) {
			issues.add(new Issue(issueId, "Issue " + issueId));
		}
		milestone.setIssues(issues);

		Project.getMilestones().add(milestone);

	}


	@Override
	public List<Milestone> getMilestones() {
		return Project.getMilestones();
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

		boolean removed = Project.getMilestones().removeIf(milestone -> milestone.getId().equals(id));

		if (!removed) {
			throw new IssueTrackingException("Milestone with ID " + id + " not found");
		}

	}

	@Override
    public void createIssue(String id, String name, String description, Severity severity, Type type, String milestoneId, Set<String> dependencies) throws IssueTrackingException {


		if (id == null || id.isEmpty() || ValidationHelper.isId(id)) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone ID cannot be null or empty");
		}
		if (name == null || name.isEmpty()) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone name cannot be null or empty");
		}
		if (description == null || description.isEmpty()) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone description cannot be null or empty");
		}
		if (severity == null) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone severity cannot be null or empty");
		}
		if (type == null) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone type cannot be null or empty");
		}
		if (milestoneId == null || milestoneId.isEmpty()) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone ID cannot be null or empty");
		}
		if (dependencies == null || dependencies.isEmpty()) {
			throw new IssueTrackingException("ERROR with createIssue: Milestone dependencies cannot be null or empty");
		}

		Issue issue = new Issue(id, name);
	
		issue.setDescription(description);
		issue.setSeverity(severity);
		issue.setType(type);



		//TODO dependencies einfügen

		Project.getIssues().add(issue);

	}

	@Override

	public List<Issue> getIssues() {
		return Project.getIssues();
	}

	public void printIssues() {
		for (Issue issue : getIssues()) {
			System.out.println(issue);
		}

	}

	@Override
	public void removeIssueById(String id) throws IssueTrackingException {
		if (id == null || !id.isEmpty()) {
			throw new IssueTrackingException("ERROR with removeMilestoneById: Milestone ID cannot be null or empty");
		}

		boolean removed = Project.getIssues().removeIf(issue -> issue.getId().equals(id));

		if (!removed) {
			throw new IssueTrackingException("Issue with ID " + id + " not found");
		}

	}

	@Override
	public void closeIssueById(String id) throws IssueTrackingException {

		for (Issue issue : getIssues()) {
			if (issue.getId().equals(id)) {
				issue.setState(State.CLOSED);}


	}}

	@Override
	public void printJsonToConsole() throws IssueTrackingException {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveJsonToFile(String path) throws IssueTrackingException {
		// TODO Auto-generated method stub

	}

}
