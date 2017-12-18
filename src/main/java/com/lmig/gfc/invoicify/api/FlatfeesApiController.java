package com.lmig.gfc.invoicify.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmig.gfc.invoicify.models.FlatFeeBillingRecord;
import com.lmig.gfc.invoicify.models.User;
import com.lmig.gfc.invoicify.services.BillingRecordRepository;
import com.lmig.gfc.invoicify.services.CompanyRepository;

@RestController
@RequestMapping("/api/flatfees")
public class FlatfeesApiController {

	private BillingRecordRepository billingRecordRepository;
	private CompanyRepository companyRepository;
	
	public FlatfeesApiController(BillingRecordRepository billingRecordRepository, CompanyRepository companyRepository) {
		this.billingRecordRepository = billingRecordRepository;
		this.companyRepository = companyRepository;
	}

	@PostMapping ("")
	public FlatFeeBillingRecord create(@RequestBody FlatFeeBillingRecord flatFeeBillingRecord, Authentication auth) {
		User user = (User) auth.getPrincipal();
		flatFeeBillingRecord.setCreatedBy(user);
		flatFeeBillingRecord.setClient(companyRepository.findOne(flatFeeBillingRecord.getClient().getId()));
		return billingRecordRepository.save(flatFeeBillingRecord);
	}

}
