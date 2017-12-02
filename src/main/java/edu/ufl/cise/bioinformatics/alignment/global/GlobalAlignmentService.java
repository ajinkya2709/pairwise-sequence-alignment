package edu.ufl.cise.bioinformatics.alignment.global;

import org.springframework.stereotype.Service;

import edu.ufl.cise.bioinformatics.alignment.AlignmentResult;
import edu.ufl.cise.bioinformatics.alignment.MatrixCell;
import edu.ufl.cise.bioinformatics.alignment.scoring.ScoringScheme;

@Service
public class GlobalAlignmentService {

	public AlignmentResult computeGlobalAlignment(String s1, String s2, ScoringScheme scoringScheme, int gapPenalty) {
		char[] A = s1.toCharArray();
		char[] B = s2.toCharArray();

		MatrixCell[][] dp = new MatrixCell[A.length + 1][B.length + 1];
		int[][] dpDir = new int[A.length + 1][B.length + 1];

		dp[0][0] = new MatrixCell(0, false);
		for (int p = 1; p <= A.length; p++) {
			dp[p][0] = new MatrixCell(dp[p - 1][0].getValue() + gapPenalty, false);
			dpDir[p][0] = 1;
		}

		for (int p = 1; p <= B.length; p++) {
			dp[0][p] = new MatrixCell(dp[0][p - 1].getValue() + gapPenalty, false);
			dpDir[0][p] = -1;
		}

		for (int p = 1; p <= A.length; p++) {
			for (int q = 1; q <= B.length; q++) {
				int matchScore = scoringScheme.getScore(A[p - 1], B[q - 1]);
				int a = dp[p - 1][q].getValue() + gapPenalty;
				int b = dp[p][q - 1].getValue() + gapPenalty;
				int c = dp[p - 1][q - 1].getValue() + matchScore;
				dp[p][q] = new MatrixCell(Math.max(dp[p - 1][q].getValue() + gapPenalty,
						Math.max(dp[p][q - 1].getValue() + gapPenalty, dp[p - 1][q - 1].getValue() + matchScore)),
						false);
				if (dp[p][q].getValue() == a) {
					dpDir[p][q] = 1;
				}
				if (dp[p][q].getValue() == b) {
					dpDir[p][q] = -1;
				}
				if (dp[p][q].getValue() == c) {
					dpDir[p][q] = 0;
				}

			}

		}
		AlignmentResult s = backtrackTable(dp, dpDir, A, B, dp[A.length][B.length].getValue());
		s.setScore(dp[A.length][B.length].getValue());
		s.setScoreMatrix(dp);
		return s;
	}

	private AlignmentResult backtrackTable(MatrixCell[][] dp, int[][] dir, char[] A, char[] B, int score) {
		AlignmentResult s = new AlignmentResult();
		int s1 = A.length;
		int s2 = B.length;
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		while (s1 > 0 || s2 > 0) {
			dp[s1][s2].setShouldHighlight(true);
			if (s1 > 0 && s2 > 0 && dir[s1][s2] == 0) {
				// Coming from diagonal
				sb1.insert(0, A[s1 - 1]);
				sb2.insert(0, B[s2 - 1]);
				s1--;
				s2--;
			} else if (s1 > 0 && dir[s1][s2] == 1) {
				// Coming from vertical
				sb1.insert(0, A[s1 - 1]);
				sb2.insert(0, '-');
				s1--;
			} else if (s2 > 0 && dir[s1][s2] == -1) {
				// Coming from horizontal
				sb1.insert(0, '-');
				sb2.insert(0, B[s2 - 1]);
				s2--;
			}

		}
		s.setSequence1(sb1.toString().toCharArray());
		s.setSequence2(sb2.toString().toCharArray());
		s.setStart1(0);
		s.setStart2(0);
		s.setScore(score);
		return s;

	}

	public AlignmentResult computeGlobalAlignmentWithAffineGap(String s1, String s2, ScoringScheme scoringScheme,
			int gapStartPenalty, int gapExtendPenalty, int negInfinity) {

		char[] A = s1.toCharArray();
		char[] B = s2.toCharArray();

		MatrixCell[][] M = new MatrixCell[A.length + 1][B.length + 1];
		MatrixCell[][] X = new MatrixCell[A.length + 1][B.length + 1];
		MatrixCell[][] Y = new MatrixCell[A.length + 1][B.length + 1];

		M[0][0] = new MatrixCell(negInfinity);
		X[0][0] = new MatrixCell(negInfinity);
		Y[0][0] = new MatrixCell(negInfinity);
		for (int j = 1; j < B.length + 1; j++) {
			M[0][j] = new MatrixCell(negInfinity);
			X[0][j] = new MatrixCell(gapStartPenalty + (j * gapExtendPenalty));
			X[0][j].setPrev(X);
			Y[0][j] = new MatrixCell(negInfinity);
		}

		for (int i = 1; i < A.length + 1; i++) {
			M[i][0] = new MatrixCell(negInfinity);
			X[i][0] = new MatrixCell(negInfinity);
			Y[i][0] = new MatrixCell(gapStartPenalty + (i * gapExtendPenalty));
			Y[i][0].setPrev(Y);
		}

		int a = 0, b = 0, c = 0;
		for (int i = 1; i < A.length + 1; i++) {
			for (int j = 1; j < B.length + 1; j++) {
				int score = scoringScheme.getScore(A[i - 1], B[j - 1]);
				a = score + M[i - 1][j - 1].getValue();
				b = score + X[i - 1][j - 1].getValue();
				c = score + Y[i - 1][j - 1].getValue();
				M[i][j] = new MatrixCell(Math.max(a, Math.max(b, c)));
				if (M[i][j].getValue() == a) {
					M[i][j].setPrev(M);
				} else if (M[i][j].getValue() == b) {
					M[i][j].setPrev(X);
				} else {
					M[i][j].setPrev(Y);
				}

				a = gapStartPenalty + gapExtendPenalty + M[i][j - 1].getValue();
				b = gapExtendPenalty + X[i][j - 1].getValue();
				c = gapStartPenalty + gapExtendPenalty + Y[i][j - 1].getValue();
				X[i][j] = new MatrixCell(Math.max(a, Math.max(b, c)));
				if (X[i][j].getValue() == a) {
					X[i][j].setPrev(M);
				} else if (X[i][j].getValue() == b) {
					X[i][j].setPrev(X);
				} else {
					X[i][j].setPrev(Y);
				}

				a = gapStartPenalty + gapExtendPenalty + M[i - 1][j].getValue();
				b = gapStartPenalty + gapExtendPenalty + X[i - 1][j].getValue();
				c = gapExtendPenalty + Y[i - 1][j].getValue();
				Y[i][j] = new MatrixCell(Math.max(a, Math.max(b, c)));
				if (Y[i][j].getValue() == a) {
					Y[i][j].setPrev(M);
				} else if (Y[i][j].getValue() == b) {
					Y[i][j].setPrev(X);
				} else {
					Y[i][j].setPrev(Y);
				}

			}
		}

		AlignmentResult result = backtrackForAffineGap(A, B, M, X, Y);
		result.setAffineModelUsed(true);
		result.setScoreMatrixM(M);
		result.setScoreMatrixX(X);
		result.setScoreMatrixY(Y);
		return result;

	}

	private AlignmentResult backtrackForAffineGap(char[] A, char[] B, MatrixCell[][] M, MatrixCell[][] X,
			MatrixCell[][] Y) {
		int s1 = A.length;
		int s2 = B.length;
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		int max = Math.max(M[s1][s2].getValue(), Math.max(X[s1][s2].getValue(), Y[s1][s2].getValue()));
		MatrixCell[][] pointer = null;
		if (max == M[s1][s2].getValue()) {
			pointer = M;
		} else if (max == X[s1][s2].getValue()) {
			pointer = X;
		} else {
			pointer = Y;
		}

		while (s1 > 0 || s2 > 0) {
			pointer[s1][s2].setShouldHighlight(true);
			if (pointer == M) {
				pointer = M[s1][s2].getPrev();
				sb1.insert(0, A[s1 - 1]);
				sb2.insert(0, B[s2 - 1]);
				s1--;
				s2--;
			} else if (pointer == X) {
				pointer = X[s1][s2].getPrev();
				sb1.insert(0, '-');
				sb2.insert(0, B[s2 - 1]);
				s2--;
			} else {
				pointer = Y[s1][s2].getPrev();
				sb1.insert(0, A[s1 - 1]);
				sb2.insert(0, '-');
				s1--;
			}
		}
		AlignmentResult result = new AlignmentResult();
		result.setScore(max);
		result.setSequence1(sb1.toString().toCharArray());
		result.setSequence2(sb2.toString().toCharArray());
		return result;

	}

}
