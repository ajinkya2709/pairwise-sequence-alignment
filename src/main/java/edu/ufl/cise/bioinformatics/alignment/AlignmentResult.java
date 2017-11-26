package edu.ufl.cise.bioinformatics.alignment;

public class AlignmentResult {

	private char[] sequence1, sequence2;
	private int score;
	private int start1, start2;
	private int[][] scoreMatrix;

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

	public int[][] getScoreMatrix() {
		return scoreMatrix;
	}

	public void setScoreMatrix(int[][] scoreMatrix) {
		this.scoreMatrix = scoreMatrix;
	}

}
