package com.tool.rental;

import com.tool.rental.exception.RequestException;
import com.tool.rental.model.Checkout;
import com.tool.rental.service.AgreementService;

import java.time.LocalDate;

public class ToolRentalApplication {

	public static void main(String[] args) {
		Checkout jakr = new Checkout("JAKR",5,25, LocalDate.now());
		Checkout ladw = new Checkout(null,5,0, LocalDate.of(2023, 7, 4));
		Checkout chns = new Checkout("CHNS",5,25, LocalDate.of(2023, 7, 4));
		try {
			AgreementService agreementService = new AgreementService();
			agreementService.checkOut(jakr);
		} catch (RequestException requestException) {
			System.err.println(requestException.getMessage());
		} catch (Exception exception) {
			System.err.println(exception.getMessage());
		}

	}

}
