package de.uniba.wiai.dsg.ajp.assignment2;

import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.IssueTrackingException;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.ProjectService;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.logic.IssueTrackerImpl;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.logic.ProjectServiceImpl;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Issue;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Milestone;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Severity;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.model.Type;
import de.uniba.wiai.dsg.ajp.assignment2.issuetracking.view.ConsoleHelper;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Console {
    public static final int EXIT = 0;
    ConsoleHelper consoleInput = ConsoleHelper.build();
    IssueTrackerImpl issueTracker = new IssueTrackerImpl();
    ProjectService projectService = new ProjectServiceImpl();

    public void startReadEvalPrint() throws IOException {

        printMainMenu();
        int option = consoleInput.askIntegerInRange("Please enter a number between 0 and 2", 0, 2);
        try {
            evalOptionMain(option);
        } catch (IssueTrackingException e) {
            System.out.println("error: " + e.getMessage());
        }
    }


    private void printMainMenu() {

        System.out.println("Menü");
        System.out.println(" (1) Validate and Load Project");
        System.out.println(" (2) Create New Project");
        System.out.println(" (0) Exit");

    }

    public void exit() {

        System.out.println("Exiting program...");
    }

    private void retryOptionMain(int option) {

        System.out.println("Invalid input. Retry!");
        try {
            evalOptionMain(option);
        } catch (IssueTrackingException e) {
            System.out.println("Error");
        }


    }

    private void evalOptionMain(int option) throws IssueTrackingException {

        switch (option) {
            case EXIT: // close
                this.exit();
                break;

            case 1: // validate and load project

                String path = null;
                try {
                    path = consoleInput.askNonEmptyString("Enter the path of the JSON file");
                } catch (IOException e) {
                    retryOptionMain(option);
                }

                issueTracker.validate(path);
                issueTracker.load(path);

                break;

            case 2: // create new project

                try {
                    projectService = issueTracker.create();

                    printSubMenu();
                    option = consoleInput.askIntegerInRange("Please enter a number", 0, 9);
                    evalOptionSub(option, projectService);
                } catch (Exception e) {
                    retryOptionMain(option);
                }

                break;

        }
    }

    private void retryOptionSub(int option, ProjectService projectService) {

        System.out.println("Invalid input. Retry!");
        try {
            evalOptionSub(option, projectService);
        } catch (IssueTrackingException e) {
            System.out.println("Error");
        }
    }

    private void evalOptionSub(int option, ProjectService projectService) throws IssueTrackingException {

        String id;
        String name;
        String description;
        Severity severityEnum;
        Type typeEnum;
        String milestoneId;
        Set<String> issueIdsDependent;


        switch (option) {

            case EXIT:
                printMainMenu();
                break;

            case 1: // add issue

                Set<String> issueIds = new HashSet<>();
                try {
                    id = consoleInput.askNonEmptyString("Enter an id for the new milestone");
                    name = consoleInput.askNonEmptyString("Enter a name for the new milestone");
                    String newId = consoleInput.askString("Enter IssueId or press enter to continue");

                    while (!newId.isEmpty()) {
                        issueIds.add(newId);
                        newId = consoleInput.askString("Enter IssueId or press enter to continue");
                    }
                    projectService.createMilestone(id, name, issueIds);
                } catch (IOException e) {
                    retryOptionSub(option, projectService);
                }

                printSubMenu();
                evalOptionSub(option, projectService);
                break;

            case 2: // remove milestone and cleanup
                try {
                    id = consoleInput.askNonEmptyString("Enter the id of the milestone you want to remove:");
                    projectService.removeMilestoneById(id);
                } catch (IOException e) {
                    retryOptionSub(option, projectService);
                }

                printSubMenu();
                evalOptionSub(option, projectService);
                break;

            case 3: // list milestones

                List<Milestone> milestones = projectService.getMilestones();
                for (Milestone m : milestones) {
                    System.out.println(m.toString());
                }
                printSubMenu();
                evalOptionSub(option, projectService);
                break;

            case 4: // add issue

                try {
                    id = consoleInput.askNonEmptyString("Enter an id for the issue:");
                    name = consoleInput.askNonEmptyString("Enter a name for the issue");
                    description = consoleInput.askNonEmptyString("Enter a description for the issue:");
                    String severity = consoleInput.askNonEmptyString("Enter one of the following severities: " +
                            "TRIVIAL, MINOR, MAJOR, CRITICAL");
                    severityEnum = Severity.valueOf(severity.toUpperCase());
                    String type = consoleInput.askNonEmptyString("Enter one of the following issue types: " +
                            "BUG, FEATURE");
                    typeEnum = Type.valueOf(type.toUpperCase());
                    milestoneId = consoleInput.askString("Enter the id of a milestone if needed");
                    issueIdsDependent = new HashSet<>();
                    String newId = consoleInput.askString("Enter IssueId");
                    while (!newId.isEmpty()) {
                        issueIdsDependent.add(newId);
                        newId = consoleInput.askString("Enter IssueId");
                    }
                    projectService.createIssue(id, name, description, severityEnum, typeEnum,
                            milestoneId, issueIdsDependent);
                } catch (IOException e) {
                    retryOptionSub(option, projectService);
                }

                printSubMenu();
                evalOptionSub(option, projectService);
                break;

            case 5: // close issue

                try {
                    id = consoleInput.askNonEmptyString("Enter the id of the issue you want to close");
                    projectService.closeIssueById(id);
                } catch (IOException e) {
                    retryOptionSub(option, projectService);
                }

                printSubMenu();
                evalOptionSub(option, projectService);
                break;

            case 6: // remove issue and cleanup

                try{
                    id = consoleInput.askNonEmptyString("Enter the id of the issue you want to remove");
                    projectService.removeIssueById(id);
                }
                catch (IOException e){
                    retryOptionSub(option, projectService);
                }

            case 7: // list issues

                List<Issue> issues = projectService.getIssues();
                for (Issue issue : issues) {
                    System.out.println(issue.toString());
                }
                printSubMenu();
                evalOptionSub(option, projectService);
                break;

            case 8: // print json to console
                projectService.printJsonToConsole();
                printSubMenu();
                evalOptionSub(option, projectService);
                break;

            case 9: // save json to file

                try {
                    String path = consoleInput.askNonEmptyString("Enter the path of the file:");
                    projectService.saveJsonToFile(path);
                } catch (IOException e) {
                    retryOptionSub(option, projectService);
                } catch (IssueTrackingException e) {
                    System.out.println("error while saving json to file: " + e.getMessage());
                }

                printSubMenu();
                evalOptionSub(option, projectService);
                break;
        }

    }

    private void printSubMenu() {

        System.out.println("Untermenü");
        System.out.println(" (1) Add Milestone");
        System.out.println(" (2) Remove Milestone and Cleanup");
        System.out.println(" (3) List Milestones");
        System.out.println(" (4) Add Issue");
        System.out.println(" (5) Close Issue");
        System.out.println(" (6) Remove Issue and Cleanup");
        System.out.println(" (7) List Issues");
        System.out.println(" (8) Print JSON on Console");
        System.out.println(" (9) Save JSON to File");
        System.out.println(" (0) Back to main menu / close without saving");

    }
}
