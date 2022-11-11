package acme.features.administrator.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.SpamRecord;
import acme.entities.Configuration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorConfigurationShowService implements AbstractShowService<Administrator,Configuration>{
	@Autowired
	protected AdministratorConfigurationRepository repository;
	
	@Override
	public boolean authorise(final Request<Configuration>request) {
		assert request!=null;
		return true;
	}
	
	@Override
	public Configuration findOne(final Request<Configuration>request) {
		assert request != null;
		
		Configuration result;
		
		result = this.repository.findConfiguration();
		
		return result;
	}
	
	@Override
	public void unbind(final Request<Configuration> request,final Configuration entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		request.unbind(entity, model,"currency","acceptedCurrencies","spamRecords","spamThreshold","spamBooster");
		model.setAttribute("spamRecordObjects", this.convertStringToSpamRecords(entity.getSpamRecords()));
		
	}
	
	// Other methods
	public List<SpamRecord> convertStringToSpamRecords(String stringSpamRecords){
		
		List<SpamRecord> res = new ArrayList<SpamRecord>();
		SpamRecord newSpamRecord = null;
		
		List<String> spamRecords = Arrays.asList(stringSpamRecords.split(","));
		for (String oldSpamRecord:spamRecords) {
			String[] terms = oldSpamRecord.split("-");
			newSpamRecord = new SpamRecord(terms[0], Double.valueOf(terms[1]), terms[2]);
			res.add(newSpamRecord);
		}
		
		return res;
		
	}
}
