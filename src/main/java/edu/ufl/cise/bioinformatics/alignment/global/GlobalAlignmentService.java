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
			dp[p][0] = new MatrixCell( dp[p - 1][0].getValue() + gapPenalty, false);
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
						Math.max(dp[p][q - 1].getValue() + gapPenalty, dp[p - 1][q - 1].getValue() + matchScore)), false);
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

	private AlignmentResult backtrackTable(MatrixCell[][] dp,int[][] dir, char[] A, char[] B, int score) {
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

}
