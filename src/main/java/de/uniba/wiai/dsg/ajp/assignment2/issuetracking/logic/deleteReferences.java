package de.uniba.wiai.dsg.ajp.assignment2.issuetracking.logic;

import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Issue;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Milestone;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Project;

import java.util.List;

public class deleteReferences {

    public void deleteMilestoneRef(String id, Project project) {

        List<Issue> list = project.getIssues();
        Issue issue = list.get(0);
        Milestone milestone = issue.getMilestone();
        Milestone defaultmilestone = null;
        String idm;

        for (int i = 1; i < list.size(); i++) {
            if(milestone != null){
                idm = milestone.getId();
                if(idm.equals(id)){
                    issue.setMilestone(defaultmilestone);
                    list.set(i,issue);
                }

            }
            issue = list.get(i);
            milestone= issue.getMilestone();
        }
        project.setIssues(list);
    }

    public void deleteIssueMilestoneRef(String id,Project project) {

        Issue defaultIssue = null;
        String idm;
        List<Milestone> list1 = project.getMilestones();

        for(int i = 0;i < list1.size();i++){
            Milestone milestone = list1.get(i);
            List<Issue> list2 = milestone.getIssues();
            for(int k = 0; k < list2.size();k++){
                Issue issue = list2.get(k);
                idm = issue.getId();
                if(idm.equals(id)){
                    list2.set(k, defaultIssue);
                }
                milestone.setIssues(list2);
            }

        }
        project.setMilestones(list1);
    }

    public void deleteIssueRef(String id,Project project){

        Issue defaultIssue = null;
        String idm;
        List<Issue> list1 = project.getIssues();

        for(int i = 0;i < list1.size();i++){
            Issue issue1 = list1.get(0);
            List<Issue> list2 = issue1.getDependencies();
            for(int k = 0;k < list2.size();k++){
                Issue issue2 = list2.get(k);
                idm = issue2.getId();
                if(idm.equals(id)){
                    list2.set(k, defaultIssue);
                }

            }
            issue1.setDependencies(list2);
        }
        project.setIssues(list1);


    }


}