package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.logic;

import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.IssueTracker;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.IssueTrackingException;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.ProjectService;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Project;

public class IssueTrackerImpl implements IssueTracker {

	public IssueTrackerImpl() {
		/*
		 * DO NOT REMOVE - REQUIRED FOR GRADING
		 *
		 * YOU CAN EXTEND IT BELOW THIS COMMENT
		 */
	}

	@Override
	public void validate(String path) throws IssueTrackingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProjectService load(String path) throws IssueTrackingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectService create() {

	Project project = new Project();

		return null;
	}

	

}
