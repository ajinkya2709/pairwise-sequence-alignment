package edu.ufl.cise.bioinformatics.alignment;

public class FileInfo {

	private boolean isInvalid;
	private String errorMessage;
	private String parsedSequence;

	public boolean isInvalid() {
		return isInvalid;
	}

	public void setInvalid(boolean isInvalid) {
		this.isInvalid = isInvalid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getParsedSequence() {
		return parsedSequence;
	}

	public void setParsedSequence(String parsedSequence) {
		this.parsedSequence = parsedSequence;
	}

}
