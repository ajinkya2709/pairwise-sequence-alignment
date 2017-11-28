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

}
