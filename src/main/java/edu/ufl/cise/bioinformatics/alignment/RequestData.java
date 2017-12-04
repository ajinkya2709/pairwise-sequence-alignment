package edu.ufl.cise.bioinformatics.alignment;

public class RequestData {

	private String input1;
	private String input2;
	private String matchScore;
	private String mismatchScore;
	private String gapPenalty;
	private Boolean useBlosumMatrix;
	private Boolean useAffineGapPenalty;
	private String gapStartPenalty;
	private String gapExtendPenalty;
	private Boolean forProteins;

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

	public String getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(String matchScore) {
		this.matchScore = matchScore;
	}

	public String getMismatchScore() {
		return mismatchScore;
	}

	public void setMismatchScore(String mismatchScore) {
		this.mismatchScore = mismatchScore;
	}

	public String getGapPenalty() {
		return gapPenalty;
	}

	public void setGapPenalty(String gapPenalty) {
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

	public String getGapStartPenalty() {
		return gapStartPenalty;
	}

	public void setGapStartPenalty(String gapStartPenalty) {
		this.gapStartPenalty = gapStartPenalty;
	}

	public String getGapExtendPenalty() {
		return gapExtendPenalty;
	}

	public void setGapExtendPenalty(String gapExtendPenalty) {
		this.gapExtendPenalty = gapExtendPenalty;
	}

	public Boolean getForProteins() {
		return forProteins;
	}

	public void setForProteins(Boolean forProteins) {
		this.forProteins = forProteins;
	}

	@Override
	public String toString() {
		return "{input1 : " + this.input1 + ", input2 : " + this.input2 + ", Use Blosum Matrix Flag : "
				+ this.useBlosumMatrix + ", Match Score : " + this.matchScore + ", Mismatch Score : "
				+ this.mismatchScore + ", Gap Penalty : " + this.gapPenalty + ", Use Affine Gap Penalty Flag : "
				+ this.useAffineGapPenalty + "}";
	}

}
