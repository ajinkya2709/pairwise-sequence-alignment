package edu.ufl.cise.bioinformatics.alignment;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MatrixCell {
	int value;
	Boolean shouldHighlight;
	MatrixCell[][] prev;

	public MatrixCell() {
		super();
	}

	public MatrixCell(int value) {
		this(value, false);
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

	@JsonIgnore
	public MatrixCell[][] getPrev() {
		return prev;
	}

	public void setPrev(MatrixCell[][] prev) {
		this.prev = prev;
	}

}
