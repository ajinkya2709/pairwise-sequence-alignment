package edu.ufl.cise.bioinformatics.alignment.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ufl.cise.bioinformatics.alignment.AlignmentResult;
import edu.ufl.cise.bioinformatics.alignment.RequestData;
import edu.ufl.cise.bioinformatics.alignment.scoring.BlosumMatrixScoringScheme;
import edu.ufl.cise.bioinformatics.alignment.scoring.ScoringScheme;
import edu.ufl.cise.bioinformatics.alignment.scoring.SimpleScoringScheme;

@RestController
public class GlobalAlignmentController {

	@Autowired
	private GlobalAlignmentService globalAlignmentService;

	@RequestMapping(value = "/global", method = RequestMethod.POST)
	public AlignmentResult computeGlobalAlignmentPost(@RequestBody RequestData requestData) {
		ScoringScheme scoringScheme = null;
		if (requestData.getUseBlosumMatrix()) {
			scoringScheme = new BlosumMatrixScoringScheme();
		} else {
			scoringScheme = new SimpleScoringScheme(requestData.getMatchScore(), requestData.getMismatchScore());
		}
		return globalAlignmentService.computeGlobalAlignment(requestData.getInput1(), requestData.getInput2(),
				scoringScheme, requestData.getGapPenalty());
	}
}