package edu.ufl.cise.bioinformatics.alignment.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.ufl.cise.bioinformatics.alignment.AlignmentResult;
import edu.ufl.cise.bioinformatics.alignment.FileInfo;
import edu.ufl.cise.bioinformatics.alignment.RequestData;
import edu.ufl.cise.bioinformatics.alignment.file.FileService;
import edu.ufl.cise.bioinformatics.alignment.scoring.BlosumMatrixScoringScheme;
import edu.ufl.cise.bioinformatics.alignment.scoring.ScoringScheme;
import edu.ufl.cise.bioinformatics.alignment.scoring.SimpleScoringScheme;

@RestController
public class LocalAlignmentController {

	@Autowired
	private LocalAlignmentService localAlignmentService;

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/local", method = RequestMethod.POST)
	public AlignmentResult computeGlobalAlignmentPost(@RequestBody RequestData requestData) {
		ScoringScheme scoringScheme = null;
		if (requestData.getUseBlosumMatrix()) {
			scoringScheme = new BlosumMatrixScoringScheme();
		} else {
			scoringScheme = new SimpleScoringScheme(Integer.parseInt(requestData.getMatchScore()),
					Integer.parseInt(requestData.getMismatchScore()));
		}

		AlignmentResult result = null;
		if (requestData.getUseAffineGapPenalty()) {
			int negInfinity = Math.min(-100, Integer.parseInt(requestData.getGapStartPenalty()))
					* Math.max(requestData.getInput1().length(), requestData.getInput2().length());
			result = localAlignmentService.computeLocalAlignmentWithAffineGap(requestData.getInput1(),
					requestData.getInput2(), scoringScheme, Integer.parseInt(requestData.getGapStartPenalty()),
					Integer.parseInt(requestData.getGapExtendPenalty()), negInfinity);

		} else {
			result = localAlignmentService.computeLocalAlignment(requestData.getInput1(), requestData.getInput2(),
					scoringScheme, Integer.parseInt(requestData.getGapPenalty()));
		}
		return result;

	}

	@RequestMapping(value = "/localWithFile", method = RequestMethod.POST)
	public AlignmentResult computeGlobalAlignmentWithFile(@RequestPart("requestData") RequestData requestData,
			@RequestPart("file1") MultipartFile file1, @RequestPart("file2") MultipartFile file2) {

		FileInfo fileInfo1 = null, fileInfo2 = null;
		if (requestData.getForProteins()) {
			fileInfo1 = fileService.validateAndParseFileForProteins(file1);
			fileInfo2 = fileService.validateAndParseFileForProteins(file2);
		} else {
			fileInfo1 = fileService.validateAndParseFileForNucleotides(file1);
			fileInfo2 = fileService.validateAndParseFileForNucleotides(file2);
		}
		AlignmentResult result = null;
		if (fileInfo1.isInvalid()) {
			result = new AlignmentResult();
			result.setFileInvalid(true);
			result.setErrorMessage(fileInfo1.getErrorMessage());
			return result;
		}

		if (fileInfo2.isInvalid()) {
			result = new AlignmentResult();
			result.setFileInvalid(true);
			result.setErrorMessage(fileInfo2.getErrorMessage());
			return result;
		}

		ScoringScheme scoringScheme = null;
		if (requestData.getUseBlosumMatrix()) {
			scoringScheme = new BlosumMatrixScoringScheme();
		} else {
			scoringScheme = new SimpleScoringScheme(Integer.parseInt(requestData.getMatchScore()),
					Integer.parseInt(requestData.getMismatchScore()));
		}

		if (requestData.getUseAffineGapPenalty()) {
			int negInfinity = Math.min(-100, Integer.parseInt(requestData.getGapStartPenalty()))
					* Math.max(fileInfo1.getParsedSequence().length(), fileInfo2.getParsedSequence().length());
			result = localAlignmentService.computeLocalAlignmentWithAffineGap(fileInfo1.getParsedSequence(),
					fileInfo2.getParsedSequence(), scoringScheme, Integer.parseInt(requestData.getGapStartPenalty()),
					Integer.parseInt(requestData.getGapExtendPenalty()), negInfinity);

		} else {
			result = localAlignmentService.computeLocalAlignment(fileInfo1.getParsedSequence(),
					fileInfo2.getParsedSequence(), scoringScheme, Integer.parseInt(requestData.getGapPenalty()));
		}

		return result;
	}
}
