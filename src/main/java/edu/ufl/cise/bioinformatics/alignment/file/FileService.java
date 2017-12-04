package edu.ufl.cise.bioinformatics.alignment.file;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.ufl.cise.bioinformatics.alignment.FileInfo;

@Service
public class FileService {

	private static List<Character> validNucleotideBases;
	private static List<Character> validProteinBases;
	static {
		validNucleotideBases = Arrays.asList('A', 'T', 'C', 'G');
		validProteinBases = Arrays.asList('A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P',
				'S', 'T', 'W', 'Y', 'V', 'X');
	}

	public FileInfo validateAndParseFileForNucleotides(MultipartFile file) {
		FileInfo parsedInfo = new FileInfo();
		StringBuffer sb = new StringBuffer();
		String fileAsString = null;
		try {
			fileAsString = new String(file.getBytes());
		} catch (IOException e) {
			parsedInfo.setInvalid(true);
			parsedInfo.setErrorMessage("IOException while parsing " + file.getOriginalFilename());
			e.printStackTrace();
			return parsedInfo;
		}

		for (char ch : fileAsString.toCharArray()) {
			if (ch == '\n' || ch == ' ')
				continue;
			if (!Character.isLetter(ch) || !validNucleotideBases.contains(Character.toUpperCase(ch))) {
				parsedInfo.setInvalid(true);
				parsedInfo.setErrorMessage("Invalid input in " + file.getOriginalFilename());
				return parsedInfo;
			}
			sb.append(ch);
		}
		parsedInfo.setInvalid(false);
		parsedInfo.setParsedSequence(sb.toString());
		return parsedInfo;

	}

	public FileInfo validateAndParseFileForProteins(MultipartFile file) {
		FileInfo parsedInfo = new FileInfo();
		StringBuffer sb = new StringBuffer();
		String fileAsString = null;
		try {
			fileAsString = new String(file.getBytes());
		} catch (IOException e) {
			parsedInfo.setInvalid(true);
			parsedInfo.setErrorMessage("IOException while parsing " + file.getOriginalFilename());
			e.printStackTrace();
			return parsedInfo;
		}

		for (char ch : fileAsString.toCharArray()) {
			if (ch == '\n' || ch == ' ')
				continue;
			if (!Character.isLetter(ch) || !validProteinBases.contains(Character.toUpperCase(ch))) {
				parsedInfo.setInvalid(true);
				parsedInfo.setErrorMessage("Invalid input in " + file.getOriginalFilename());
				return parsedInfo;
			}
			sb.append(ch);
		}
		parsedInfo.setInvalid(false);
		parsedInfo.setParsedSequence(sb.toString());
		return parsedInfo;

	}

}
