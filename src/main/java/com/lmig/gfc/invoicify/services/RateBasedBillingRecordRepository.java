package com.lmig.gfc.invoicify.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lmig.gfc.invoicify.models.RateBasedBillingRecord;

@Repository
public interface RateBasedBillingRecordRepository extends JpaRepository<RateBasedBillingRecord, Long> {

}
