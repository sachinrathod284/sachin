package com.example.parkinglotsachin.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParkinglotService {

	private static ParkinglotService parkinglotService;
	private static Map<Integer, Slots> slotMap;
	static Integer count = 0;

	private static final Logger	logger = LoggerFactory.getLogger(ParkinglotService.class);
	
	/**
	 * Count the Commands
	 */
	static Integer countCalledCommands() {
		count++;
		return count;
	}

	public ParkinglotService(int slotNumbers) {
		slotMap = new HashMap<>();
		for (int i = 1; i <= slotNumbers; i++) {
			slotMap.put(i, new Slots(i));
		}
	}

	/**
	 * get instance from slot numbers and its init only once with singleton obj
	 */

	static ParkinglotService getInstance(int slotNumbers) {
		if (parkinglotService == null) {
			parkinglotService = new ParkinglotService(slotNumbers);
		}
		return parkinglotService;
	}

	/**
	 * Get the next available slot and make it unavailable
	 *
	 * @return slot number which was marked unavailable
	 */
	public Integer fillAvailableSlot() throws RuntimeException {
		int nextAvailableSlotNumber = -1;
		for (int i = 1; i <= slotMap.size(); i++) {
			Slots s = slotMap.get(i);
			if (s.status) {
				nextAvailableSlotNumber = s.slotNumbers;
				s.status = false;
				break;
			}
		}
		if (nextAvailableSlotNumber != -1) {
			return nextAvailableSlotNumber;
		} else {
			logger.error("Parking is full");
			throw new RuntimeException("Parking is full");
		}
	}

	/**
	 * @param slotNumber
	 * 
	 */
	public void emptySlot(int slotNumber) {
		if (slotMap.containsKey(slotNumber)) {
			if (slotMap.get(slotNumber).status) {
				logger.error("The slot is already empty");
				throw new IllegalStateException("The slot is already empty");
			} else {
				slotMap.get(slotNumber).status = true;
			}
		} else {
			logger.error("The slot number is wrong");
			throw new IllegalStateException("The slot number is wrong");
		}
	}

	/**
	 * Slots entity belong to Parking class
	 */
	private class Slots {
		private int slotNumbers;
		private boolean status;

		public Slots(int slotNumbers) {
			this.slotNumbers = slotNumbers;
			this.status = true;
		}
	}

}
