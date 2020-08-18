package com.example.parkinglotsachin;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.example.parkinglotsachin.pojo.Car;
import com.example.parkinglotsachin.service.VehicleTicketService;

public class VehicleTicketServiceTest {

	@Test
	public void allTestCasesIntoOne() {
		VehicleTicketService ticketService = VehicleTicketService.createInstance(4);

		int slot1 = ticketService.issueParkingTicket(new Car("KA-01-HH-1234", "White test"));
		Assert.assertEquals(1, slot1);

		int slot2 = ticketService.issueParkingTicket(new Car("KA-01-BB-0001", "White test"));
		Assert.assertEquals(2, slot2);

		int slot3 = ticketService.issueParkingTicket(new Car("KA-01-HH-7777", "Black"));
		Assert.assertEquals(3, slot3);

		int slot4 = ticketService.issueParkingTicket(new Car("KA-01-HH-3141", "Blue"));
		Assert.assertEquals(4, slot4);

		ticketService.exitVehicle(4);

		int slotNew = ticketService.issueParkingTicket(new Car("KA-01-P-899", "Red"));
		Assert.assertEquals(4, slotNew);

		try {
			ticketService.issueParkingTicket(new Car("KA-01-HH-12345", "White test"));
			Assert.assertTrue("Parking is full", false);
		} catch (Exception e) {
			Assert.assertEquals("", "Parking is full", e.getMessage());
		}

		List<String> registrationNumbers = ticketService.getRegistrationNumbersFromColor("White test");
		Assert.assertEquals(2, registrationNumbers.size());

		List<Integer> slotNumbers = ticketService.getSlotNumbersFromColor("White test");
		Assert.assertEquals(2, slotNumbers.size());

		int slotNumber = ticketService.getSlotNumberFromRegistrationNumber("KA-01-HH-1234");
		Assert.assertEquals(1, slotNumber);
	}
	
}
