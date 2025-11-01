package seedu.sgsafe.utils.exceptions;

public class CaseNotFoundException extends InvalidCommandException{
    public CaseNotFoundException(String caseId) {
        super("No case found with ID: "+ caseId);
    }
}
