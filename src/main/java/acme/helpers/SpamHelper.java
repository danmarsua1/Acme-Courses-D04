package acme.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.SpamRecord;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;

@Service
public class SpamHelper {
	
	@Autowired
	private AdministratorConfigurationRepository configuration;

	public List<SpamRecord> convertStringToSpamRecords(String stringSpamRecords) {

		List<SpamRecord> res = new ArrayList<SpamRecord>();
		SpamRecord newSpamRecord = null;

		List<String> spamRecords = Arrays.asList(stringSpamRecords.split(","));
		for (String oldSpamRecord : spamRecords) {
			String[] terms = oldSpamRecord.split("-");
			newSpamRecord = new SpamRecord(terms[0], Double.valueOf(terms[1]), terms[2]=="X" ? "" : terms[2]);
			res.add(newSpamRecord);
		}

		return res;
	}
	
	public String convertSpamRecordsToString(List<SpamRecord> spamRecords) {

		String stringSpamRecords = "";
		
		for (SpamRecord objectSpamRecord : spamRecords) {
			stringSpamRecords += objectSpamRecord.getTerm() + "-";
			stringSpamRecords += String.valueOf(objectSpamRecord.getWeight()) + "-";
			stringSpamRecords += objectSpamRecord.getBoosterTerm() != "X"
				? objectSpamRecord.getBoosterTerm()
				: "X";
			stringSpamRecords += ",";
		}

		return stringSpamRecords;
	}
	
	public boolean isSpamText(String stringSpamRecords, String text) {
		
		boolean res = false;
		Double totalFactor = 0.0;
		Double spamBooster = this.configuration.findConfiguration().getSpamBooster();
		Double spamThreshold = this.configuration.findConfiguration().getSpamThreshold();
		
		List<SpamRecord> spamRecords = this.convertStringToSpamRecords(stringSpamRecords);
		
		// Count number of terms
		int countWords = text.split("\\s+").length;
		
		// Format the piece of text before apply operations
		text = this.formatSpamText(text);
		
		for (SpamRecord sr:spamRecords) {
			totalFactor += this.applySpamFactorByText(sr, text, spamBooster);
		}
		
		totalFactor = totalFactor / countWords;
		
		return totalFactor > spamThreshold ? true : res;
	}
	
	// Transform operations: lower case, remove spaces, remove dashes, remove underscores, remove line breaks 
	public String formatSpamText(String text) {
		
		return text
				.toLowerCase()
				.replaceAll("\\s+", " ")
				.replaceAll("_+", " ")
				.replace("-", "")
				.replace("\n", "")
				.replace("\r", "")
				.trim();
	}
	
	public Double applySpamFactorByText(SpamRecord spamRecord, String text, Double spamBooster) {
		
		Double totalWeight = 0.0;
		
		String term = spamRecord.getTerm();
		Double termWeight = spamRecord.getWeight();
		String termBooster = spamRecord.getBoosterTerm();
		
		int countTerm = this.countOccurrences(text, term);
		totalWeight = countTerm * termWeight;
		
		if(!"X".equals(termBooster))
			totalWeight = totalWeight * spamBooster;
		
		return totalWeight;
	}
	
	public int countOccurrences(String text, String word)
	{
	    
	    int count = 0;
	    
	    // If the term is composed for 1 word
	    if(word.split(" ").length<=1) {
	    	
	    	// split the string by spaces
	    	String aux[] = text.split(" ");
		 
		    for (int i = 0; i < aux.length; i++)
		    {
			    // if exact match found increase count
			    if (word.equals(aux[i]))
			        count++;
		    }
		
		// If the term is composed for 2 or more words
	    } else {
	    	count = ( text.split("(?:^|\\W)" + word + "(?:$|\\W)", -1).length ) - 1;
		}
	 
	    return count;
	}
}
