package edu.ufl.cise.bioinformatics.alignment.local;

import org.springframework.stereotype.Service;

import edu.ufl.cise.bioinformatics.alignment.AlignmentResult;
import edu.ufl.cise.bioinformatics.alignment.MatrixCell;
import edu.ufl.cise.bioinformatics.alignment.scoring.ScoringScheme;

@Service
public class LocalAlignmentService {

	public AlignmentResult computeLocalAlignment(String s1, String s2, ScoringScheme scoringScheme, int gapPenalty) {
		char[] A = s1.toCharArray();
		char[] B = s2.toCharArray();

		MatrixCell[][] dp = new MatrixCell[A.length + 1][B.length + 1];
		int[][] dpDir = new int[A.length + 1][B.length + 1];

		int max = Integer.MIN_VALUE;
		int maxI = 0;
		int maxJ = 0;

		dp[0][0] = new MatrixCell(0, false);
		for (int p = 1; p <= A.length; p++) {
			dp[p][0] = new MatrixCell(0, false);
			dpDir[p][0] = 0;
		}

		for (int p = 1; p <= B.length; p++) {
			dp[0][p] = new MatrixCell(0, false);
			dpDir[0][p] = 0;
		}

		for (int p = 1; p <= A.length; p++) {
			for (int q = 1; q <= B.length; q++) {
				int a = dp[p - 1][q].getValue() + gapPenalty;
				int b = dp[p][q - 1].getValue() + gapPenalty;
				int c = dp[p - 1][q - 1].getValue() + scoringScheme.getScore(A[p - 1], B[q - 1]);
				dp[p][q] = new MatrixCell(
						Math.max(0, Math.max(dp[p - 1][q].getValue() + gapPenalty,
								Math.max(dp[p][q - 1].getValue() + gapPenalty,
										dp[p - 1][q - 1].getValue() + scoringScheme.getScore(A[p - 1], B[q - 1])))),
						false);
				if (a > b && a > c && a > 0) {
					dpDir[p][q] = 1;
				} else if (b > c && b > 0) {
					dpDir[p][q] = -1;
				} else if (c > 0) {
					dpDir[p][q] = 2;
				} else {
					dpDir[p][q] = 0;
				}
				if (dp[p][q].getValue() > max) {
					max = dp[p][q].getValue();
					maxI = p;
					maxJ = q;
				}

			}

		}

		AlignmentResult s = localBacktrackTable(dpDir, A, B, max, dp, maxI, maxJ);
		s.setScoreMatrix(dp);
		return s;
	}

	public static AlignmentResult localBacktrackTable(int[][] dir, char[] A, char[] B, int score,
			MatrixCell[][] scoreMat, int maxI, int maxJ) {
		AlignmentResult s = new AlignmentResult();

		int s1 = maxI;
		int s2 = maxJ;

		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		while (scoreMat[s1][s2].getValue() != 0) {
			scoreMat[s1][s2].setShouldHighlight(true);
			if (dir[s1][s2] == 2) {
				sb1.insert(0, A[s1 - 1]);
				sb2.insert(0, B[s2 - 1]);
				s1--;
				s2--;
			} else if (dir[s1][s2] == 1) {
				// Coming from vertical
				sb1.insert(0, A[s1 - 1]);
				sb2.insert(0, '-');
				s1--;
			} else if (dir[s1][s2] == -1) {
				// Coming from horizontal
				sb1.insert(0, '-');
				sb2.insert(0, B[s2 - 1]);
				s2--;
			}

		}

		s.setSequence1(sb1.toString().toCharArray());
		s.setSequence2(sb2.toString().toCharArray());

		s.setStart1(s1 + 1);
		s.setStart2(s2 + 1);

		s.setScore(score);

		return s;

	}

	public AlignmentResult computeLocalAlignmentWithAffineGap(String s1, String s2, ScoringScheme scoringScheme,
			int gapStartPenalty, int gapExtendPenalty, int negInfinity) {

		char[] A = s1.toCharArray();
		char[] B = s2.toCharArray();

		MatrixCell[][] M = new MatrixCell[A.length + 1][B.length + 1];
		MatrixCell[][] X = new MatrixCell[A.length + 1][B.length + 1];
		MatrixCell[][] Y = new MatrixCell[A.length + 1][B.length + 1];

		int maxVal = -1, maxI = 0, maxJ = 0;
		MatrixCell[][] matrixWithMax = null;

		for (int j = 0; j < B.length + 1; j++) {
			M[0][j] = new MatrixCell(0);
			X[0][j] = new MatrixCell(0);
			Y[0][j] = new MatrixCell(0);
		}
		for (int i = 0; i < A.length + 1; i++) {
			M[i][0] = new MatrixCell(0);
			X[i][0] = new MatrixCell(0);
			Y[i][0] = new MatrixCell(0);
		}

		int a = 0, b = 0, c = 0;
		for (int i = 1; i < A.length + 1; i++) {
			for (int j = 1; j < B.length + 1; j++) {
				int score = scoringScheme.getScore(A[i - 1], B[j - 1]);
				a = score + M[i - 1][j - 1].getValue();
				b = score + X[i - 1][j - 1].getValue();
				c = score + Y[i - 1][j - 1].getValue();
				M[i][j] = new MatrixCell(Math.max(0, Math.max(a, Math.max(b, c))));
				if (a > b && a > c && a > 0) {
					M[i][j].setPrev(M);
				} else if (b > c && b > 0) {
					M[i][j].setPrev(X);
				} else if (c > 0) {
					M[i][j].setPrev(Y);
				} // else Value is 0. There is no previous pointer

				if (M[i][j].getValue() > maxVal) {
					maxVal = M[i][j].getValue();
					maxI = i;
					maxJ = j;
					matrixWithMax = M;
				}

				a = gapStartPenalty + gapExtendPenalty + M[i][j - 1].getValue();
				b = gapExtendPenalty + X[i][j - 1].getValue();
				c = gapStartPenalty + gapExtendPenalty + Y[i][j - 1].getValue();
				X[i][j] = new MatrixCell(Math.max(0, Math.max(a, Math.max(b, c))));
				if (a > b && a > c && a > 0) {
					X[i][j].setPrev(M);
				} else if (b > c && b > 0) {
					X[i][j].setPrev(X);
				} else if (c > 0) {
					X[i][j].setPrev(Y);
				} // else Value is 0. There is no previous pointer

				if (X[i][j].getValue() > maxVal) {
					maxVal = X[i][j].getValue();
					maxI = i;
					maxJ = j;
					matrixWithMax = X;
				}

				a = gapStartPenalty + gapExtendPenalty + M[i - 1][j].getValue();
				b = gapStartPenalty + gapExtendPenalty + X[i - 1][j].getValue();
				c = gapExtendPenalty + Y[i - 1][j].getValue();
				Y[i][j] = new MatrixCell(Math.max(0, Math.max(a, Math.max(b, c))));
				if (a > b && a > c && a > 0) {
					Y[i][j].setPrev(M);
				} else if (b > c && b > 0) {
					Y[i][j].setPrev(X);
				} else if (c > 0) {
					Y[i][j].setPrev(Y);
				} // else Value is 0. There is no previous pointer

				if (Y[i][j].getValue() > maxVal) {
					maxVal = Y[i][j].getValue();
					maxI = i;
					maxJ = j;
					matrixWithMax = Y;
				}

			}
		}

		AlignmentResult result = localBacktrackForAffineGap(A, B, M, X, Y, matrixWithMax, maxI, maxJ);
		result.setAffineModelUsed(true);
		result.setScoreMatrixM(M);
		result.setScoreMatrixX(X);
		result.setScoreMatrixY(Y);
		return result;

	}

	private AlignmentResult localBacktrackForAffineGap(char[] A, char[] B, MatrixCell[][] M, MatrixCell[][] X,
			MatrixCell[][] Y, MatrixCell[][] matrixWithMax, int maxI, int maxJ) {
		int s1 = maxI;
		int s2 = maxJ;
		MatrixCell[][] pointer = matrixWithMax;
		int maxScore = pointer[s1][s2].getValue();

		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		while (pointer[s1][s2].getValue() != 0) {
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
		result.setScore(maxScore);
		result.setSequence1(sb1.toString().toCharArray());
		result.setSequence2(sb2.toString().toCharArray());
		return result;

	}

}
