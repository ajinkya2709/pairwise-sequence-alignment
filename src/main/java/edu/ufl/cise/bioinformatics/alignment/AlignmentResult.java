package edu.ufl.cise.bioinformatics.alignment;

import java.util.Arrays;

public class AlignmentResult {

	private char[] sequence1, sequence2;
	private int score;
	private int start1, start2;
	private MatrixCell[][] scoreMatrix;
	// For Affine Gap Model
	private boolean affineModelUsed;
	private MatrixCell[][] scoreMatrixM;
	private MatrixCell[][] scoreMatrixX;
	private MatrixCell[][] scoreMatrixY;

	public char[] getSequence1() {
		return sequence1;
	}

	public void setSequence1(char[] sequence1) {
		this.sequence1 = sequence1;
	}

	public char[] getSequence2() {
		return sequence2;
	}

	public void setSequence2(char[] sequence2) {
		this.sequence2 = sequence2;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getStart1() {
		return start1;
	}

	public void setStart1(int start1) {
		this.start1 = start1;
	}

	public int getStart2() {
		return start2;
	}

	public void setStart2(int start2) {
		this.start2 = start2;
	}

	public MatrixCell[][] getScoreMatrix() {
		return scoreMatrix;
	}

	public void setScoreMatrix(MatrixCell[][] scoreMatrix) {
		this.scoreMatrix = scoreMatrix;
	}

	public boolean isAffineModelUsed() {
		return affineModelUsed;
	}

	public void setAffineModelUsed(boolean affineModelUsed) {
		this.affineModelUsed = affineModelUsed;
	}

	public MatrixCell[][] getScoreMatrixM() {
		return scoreMatrixM;
	}

	public void setScoreMatrixM(MatrixCell[][] scoreMatrixM) {
		this.scoreMatrixM = scoreMatrixM;
	}

	public MatrixCell[][] getScoreMatrixX() {
		return scoreMatrixX;
	}

	public void setScoreMatrixX(MatrixCell[][] scoreMatrixX) {
		this.scoreMatrixX = scoreMatrixX;
	}

	public MatrixCell[][] getScoreMatrixY() {
		return scoreMatrixY;
	}

	public void setScoreMatrixY(MatrixCell[][] scoreMatrixY) {
		this.scoreMatrixY = scoreMatrixY;
	}

	@Override
	public String toString() {
		return "AlignmentResult [sequence1=" + Arrays.toString(sequence1) + ", sequence2=" + Arrays.toString(sequence2)
				+ ", score=" + score + ", start1=" + start1 + ", start2=" + start2 + ", scoreMatrix="
				+ Arrays.toString(scoreMatrix) + "]";
	}

}
