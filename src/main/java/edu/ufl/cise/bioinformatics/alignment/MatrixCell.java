package edu.ufl.cise.bioinformatics.alignment;

public class MatrixCell {
	int value;
	Boolean shouldHighlight;

	public MatrixCell() {
		super();
	}

	public MatrixCell(int value, Boolean shouldHighlight) {
		super();
		this.value = value;
		this.shouldHighlight = shouldHighlight;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Boolean getShouldHighlight() {
		return shouldHighlight;
	}

	public void setShouldHighlight(Boolean shouldHighlight) {
		this.shouldHighlight = shouldHighlight;
	}

}
