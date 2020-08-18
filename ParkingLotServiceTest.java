package com.example.parkinglotsachin;

import org.junit.Assert;
import org.junit.Test;

import com.example.parkinglotsachin.service.ParkinglotService;

public class ParkingLotServiceTest {

	@Test
    public void fillAvailableSlotWhenSlotIsAvailable() {
		ParkinglotService parkingLot = new ParkinglotService(2);

        int slot1 = parkingLot.fillAvailableSlot();
        Assert.assertEquals(1, slot1);

        int slot2 = parkingLot.fillAvailableSlot();
        Assert.assertEquals(2, slot2);
    }

	@Test
	public void parkinglotExists() {
		ParkinglotService parkingLot = new ParkinglotService(3);
		try {
			ParkinglotService parking = new ParkinglotService(3);
		} catch (Exception e) {
			Assert.assertEquals("Parking Lot created,so not allowed", e.getMessage());
		}
	}
	
	@Test
	public void emptySlotValidPark() {
		ParkinglotService parkingLot = new ParkinglotService(3);

		int slot = parkingLot.fillAvailableSlot();
		Assert.assertEquals(1, slot);

		int slot2 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(2, slot2);

		int slot3 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(3, slot3);

		parkingLot.emptySlot(2);
		int slot4 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(2, slot4);

		parkingLot.emptySlot(1);
		int slot5 = parkingLot.fillAvailableSlot();
		Assert.assertEquals(1, slot5);
	}
}
