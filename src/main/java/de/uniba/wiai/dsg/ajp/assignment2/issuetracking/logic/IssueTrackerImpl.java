package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.IssueTracker;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.IssueTrackingException;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.ProjectService;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Project;

import java.io.File;
import java.io.IOException;

public class IssueTrackerImpl implements IssueTracker {

	public IssueTrackerImpl() {

	}

	@Override
	public void validate(String path) throws IssueTrackingException {
		if (path == null || path.isEmpty()) {
			throw new IssueTrackingException("ERROR with validate: Path cannot be null or empty");
		}

		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			throw new IssueTrackingException("ERROR with validate: File does not exist");
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			Project project = mapper.readValue(file, Project.class);
			if (project == null) {
				throw new IssueTrackingException("ERROR with validate: Project is null");
			}
		} catch (IOException e) {
			throw new IssueTrackingException("ERROR with validate: " + e.getMessage(), e);
		}
	}

	@Override
	public ProjectService load(String path) throws IssueTrackingException {
		if (path == null || path.isEmpty()) {
			throw new IssueTrackingException("ERROR with load: Path cannot be null or empty");
		}

		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			throw new IssueTrackingException("ERROR with load: File does not exist");
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			Project project = mapper.readValue(file, Project.class);
			if (project == null) {
				throw new IssueTrackingException("ERROR with load: Project is null");
			}
			ProjectServiceImpl projectService = new ProjectServiceImpl();
			projectService.setProject(project);
			return projectService;
		} catch (IOException e) {
			throw new IssueTrackingException("ERROR with load: " + e.getMessage(), e);
		}
	}

	@Override
	public ProjectService create() {
		Project project = new Project();
		ProjectServiceImpl projectService = new ProjectServiceImpl();
		projectService.setProject(project);
		return projectService;
	}
}