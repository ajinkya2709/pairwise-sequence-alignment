package edu.ufl.cise.bioinformatics.alignment;

public class RequestData {

	private String input1;
	private String input2;
	private int matchScore;
	private int mismatchScore;
	private int gapPenalty;
	private Boolean useBlosumMatrix;
	private Boolean useAffineGapPenalty;
	private int gapStartPenalty;
	private int gapExtendPenalty;

	public String getInput1() {
		return input1;
	}

	public void setInput1(String input1) {
		this.input1 = input1;
	}

	public String getInput2() {
		return input2;
	}

	public void setInput2(String input2) {
		this.input2 = input2;
	}

	public int getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(int matchScore) {
		this.matchScore = matchScore;
	}

	public int getMismatchScore() {
		return mismatchScore;
	}

	public void setMismatchScore(int mismatchScore) {
		this.mismatchScore = mismatchScore;
	}

	public int getGapPenalty() {
		return gapPenalty;
	}

	public void setGapPenalty(int gapPenalty) {
		this.gapPenalty = gapPenalty;
	}

	public Boolean getUseBlosumMatrix() {
		return useBlosumMatrix;
	}

	public void setUseBlosumMatrix(Boolean useBlosumMatrix) {
		this.useBlosumMatrix = useBlosumMatrix;
	}

	public Boolean getUseAffineGapPenalty() {
		return useAffineGapPenalty;
	}

	public void setUseAffineGapPenalty(Boolean useAffineGapPenalty) {
		this.useAffineGapPenalty = useAffineGapPenalty;
	}

	public int getGapStartPenalty() {
		return gapStartPenalty;
	}

	public void setGapStartPenalty(int gapStartPenalty) {
		this.gapStartPenalty = gapStartPenalty;
	}

	public int getGapExtendPenalty() {
		return gapExtendPenalty;
	}

	public void setGapExtendPenalty(int gapExtendPenalty) {
		this.gapExtendPenalty = gapExtendPenalty;
	}

}
