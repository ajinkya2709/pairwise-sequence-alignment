package edu.ufl.cise.bioinformatics.alignment.scoring;

public class SimpleScoringScheme implements ScoringScheme {

	private int matchScore;
	private int mismatchScore;

	public SimpleScoringScheme() {
		super();
		this.matchScore = 1;
		this.mismatchScore = -1;
	}

	public SimpleScoringScheme(int matchScore, int mismatchScore) {
		super();
		this.matchScore = matchScore;
		this.mismatchScore = mismatchScore;
	}

	@Override
	public int getScore(char c1, char c2) {
		return c1 == c2 ? matchScore : mismatchScore;
	}

}
