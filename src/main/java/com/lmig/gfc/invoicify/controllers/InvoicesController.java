package com.lmig.gfc.invoicify.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lmig.gfc.invoicify.models.BillingRecord;
import com.lmig.gfc.invoicify.models.Company;
import com.lmig.gfc.invoicify.models.Invoice;
import com.lmig.gfc.invoicify.models.InvoiceLineItem;
import com.lmig.gfc.invoicify.models.User;
import com.lmig.gfc.invoicify.services.BillingRecordRepository;
import com.lmig.gfc.invoicify.services.CompanyRepository;
import com.lmig.gfc.invoicify.services.InvoiceRepository;

@Controller
@RequestMapping("/invoices")
public class InvoicesController {

	private InvoiceRepository invoiceRepository;

	private CompanyRepository companyRepository;

	private BillingRecordRepository billingRecordRepository;

	public InvoicesController(InvoiceRepository invoiceRepository, CompanyRepository companyRepository,
			BillingRecordRepository billingRecordRepository) {
		this.invoiceRepository = invoiceRepository;
		this.companyRepository = companyRepository;
		this.billingRecordRepository = billingRecordRepository;
	}

	@GetMapping("")
	public ModelAndView showInvoices() {
		ModelAndView mv = new ModelAndView("invoices/list");

		// Get all the invoices and add them to the model and view with the key
		// "invoices"
		List<Invoice> invoices = invoiceRepository.findAll();
		mv.addObject("invoices", invoices);
		// Add a key to the model and view named "showTable" which should be true if
		// there's more than one invoice and false if there are zero invoices
		mv.addObject("showTable", invoices.size() > 0);
		return mv;
	}

	@GetMapping("/clients")
	public ModelAndView chooseClient() {
		ModelAndView mv = new ModelAndView("invoices/clients");

		// Get all the clients and add them to the model and view with the key "clients"
		List<Company> clients = companyRepository.findAll();
		mv.addObject("clients", clients);
		return mv;
	}

	@GetMapping("/clients/{clientId}")
	public ModelAndView createInvoice(@PathVariable Long clientId) {
		ModelAndView mv = new ModelAndView("invoices/billing-records-list");

		// Get all the billing records for the specified client that have no associated
		// invoice line item and add them with the key "records"
		List<BillingRecord> clientRecords = billingRecordRepository.findByClientIdAndLineItemIsNull(clientId);
		mv.addObject("records", clientRecords);

		// Add the client id to the model and view with the key "clientId"
		mv.addObject("clientId", clientId);

		return mv;
	}

	@PostMapping("/clients/{clientId}")
	public String createInvoice(Invoice invoice, @PathVariable Long clientId, long[] recordIds, Authentication auth) {
		// Get the user from the auth.getPrincipal() method
		User user = (User) auth.getPrincipal();

		// Find all billing records in the recordIds array
		List<BillingRecord> records = new ArrayList<BillingRecord>();
		for (long id : recordIds) {
			records.add(billingRecordRepository.findOne(id));
		}
		// Create a new list that can hold invoice line items
		List<InvoiceLineItem> invoiceLineItem = new ArrayList<InvoiceLineItem>();

		// For each billing record in the records found from recordIds
		for (BillingRecord billingRecord : records) {

			// Create a new invoice line item
			InvoiceLineItem item = new InvoiceLineItem();

			// Set the billing record on the invoice line item
			item.setBillingRecord(billingRecord);

			// Set the created by to the user
			item.setCreatedBy(user);

			// Set the invoice on the invoice line item
			item.setInvoice(invoice);
			// Add the invoice line item to the list of invoice line items
			invoiceLineItem.add(item);
		}
		// Set the list of line items on the invoice
		invoice.setInvoiceLineItems(invoiceLineItem);
		// Set the created by on the invoice to the user
		invoice.setCreatedBy(user);
		// Set the client on the invoice to the company identified by clientId
		invoice.setCompany(companyRepository.findOne(clientId));
		// Save the invoice to the database
		invoiceRepository.save(invoice);

		return "redirect:/invoices";
	}

}
