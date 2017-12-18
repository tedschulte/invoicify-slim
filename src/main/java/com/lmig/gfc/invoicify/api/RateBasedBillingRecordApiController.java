package com.lmig.gfc.invoicify.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmig.gfc.invoicify.models.RateBasedBillingRecord;
import com.lmig.gfc.invoicify.models.User;
import com.lmig.gfc.invoicify.services.BillingRecordRepository;
import com.lmig.gfc.invoicify.services.CompanyRepository;

@RestController
@RequestMapping ("/api/ratefees")
public class RateBasedBillingRecordApiController {

	private BillingRecordRepository billingRecordRepository;
	private CompanyRepository companyRepository;
	
	public RateBasedBillingRecordApiController(BillingRecordRepository billingRecordRepository, CompanyRepository companyRepository) {
		this.billingRecordRepository = billingRecordRepository;
		this.companyRepository = companyRepository;
	}
	
	@PostMapping ("")
	public RateBasedBillingRecord create(@RequestBody RateBasedBillingRecord rateBasedBillingRecord, Authentication auth) {
		User user = (User) auth.getPrincipal();
		rateBasedBillingRecord.setCreatedBy(user);
		rateBasedBillingRecord.setClient(companyRepository.findOne(rateBasedBillingRecord.getClient().getId()));	
		return billingRecordRepository.save(rateBasedBillingRecord);
	}
	
}
