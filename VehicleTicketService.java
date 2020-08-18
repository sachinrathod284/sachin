/**
 * 
 */
package com.example.parkinglotsachin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.example.parkinglotsachin.pojo.Vehicle;
import com.example.parkinglotsachin.pojo.VehicleAllocationStatus;

/**
 * @author rathods
 *
 */

public class VehicleTicketService {

	private static VehicleTicketService vehicleTicketService;
	private ParkinglotService parkinglotService;
	private static Map<Integer, Ticket> ticketMap;

	private static final Logger	logger = LoggerFactory.getLogger(VehicleTicketService.class);
	
	VehicleTicketService(ParkinglotService parkinglotService) {
		this.parkinglotService = parkinglotService;
		ticketMap = new HashMap<Integer, Ticket>();
	}

	/**
	 * @return ticketService instance based on slots
	 */
	public static VehicleTicketService createInstance(int numberOfSlots) {
		if (numberOfSlots < 1) {
			throw new RuntimeException("Number of slots shouldn't be less than 1");
		}
		if (vehicleTicketService == null) {
			ParkinglotService parkingLot = ParkinglotService.getInstance(numberOfSlots);
			vehicleTicketService = new VehicleTicketService(parkingLot);
		}
		return vehicleTicketService;
	}

	/**
	 *
	 * @return ticketService instance
	 */
	public static VehicleTicketService getInstance() {
		if (vehicleTicketService == null) {
			logger.error("Parking Lot is not initialized");
			// throw new IllegalStateException("Parking Lot is not initialized");
			// controller service failing, so wait for init
		}
		return vehicleTicketService;
	}

	/**
	 * Parks a vehicle
	 *
	 * @return slotNumber => slot number at which the vehicle needs to be parked
	 */

	public Integer issueParkingTicket(Vehicle vehicle) {
		if (StringUtils.isEmpty(vehicle)) {
			logger.error("Vehicle is null and it's not allowed");
			throw new IllegalArgumentException("Vehicle is null and it's not allowed");
		}
		boolean isTicketTakenFlag = true;
		for (Ticket ticket : ticketMap.values()) {
			if (vehicle.getRegistrationNumber().equals(ticket.vehicle.getRegistrationNumber())) {
				isTicketTakenFlag = false;
			}
		}
		if (isTicketTakenFlag) {
			int assignedSlotNumber = parkinglotService.fillAvailableSlot();
			Ticket ticket = new Ticket(assignedSlotNumber, vehicle);
			ticketMap.put(assignedSlotNumber, ticket);
			return assignedSlotNumber;
		} else {
			logger.error("Same Registration Number Vehicles are not allowed");
			throw new RuntimeException("Same Registration Number Vehicles are not allowed");
		}

	}

	/**
	 * Exits a vehicle from the parking lot
	 * @param slotNumber
	 */
	public String exitVehicle(int slotNumber) {
		if (ticketMap.containsKey(slotNumber)) {
			parkinglotService.emptySlot(slotNumber);
			ticketMap.remove(slotNumber);
			logger.info("vehicle removed from slot" + slotNumber);
			return "vehicle removed from slot" + slotNumber;
		} else {
			logger.error("No vehicle found at given slot. Incorrect input");
			throw new RuntimeException("No vehicle found at given slot. Incorrect input");
		}
	}

	/**
	 * returns all the registration numbers of the vehicles with the given color
	 *
	 * @param color
	 *
	 * @return List of all vehicles color
	 */
	public List<String> getRegistrationNumbersFromColor(String color) {
		if (StringUtils.isEmpty(color)) {
			throw new IllegalArgumentException("color cannot be null");
		}
		return ticketMap.entrySet().stream().filter(item -> ((Ticket) item.getValue()).vehicle.getColor().equalsIgnoreCase(color))
				.map(item -> ((Ticket) item.getValue()).vehicle.getRegistrationNumber()).collect(Collectors.toList());
	}

	/**
	 * @return slot number at which the Vehicle by registrationNumber is parked
	 */
	public int getSlotNumberFromRegistrationNumber(String registrationNumber) {
		if (StringUtils.isEmpty(registrationNumber)) {
			throw new IllegalArgumentException("registrationNumber should not be null");
		}
		Optional<Entry<Integer, Ticket>> filter = ticketMap.entrySet().stream().findAny()
				.filter(item -> ((Ticket) item.getValue()).vehicle.getRegistrationNumber().equalsIgnoreCase(registrationNumber));
		if (filter.isPresent()) {
			return filter.get().getValue().slotNumber;
		} else {
			throw new RuntimeException("No slot found");
		}
	}

	/**
	 * returns all the slot numbers of the vehicles with color
	 *
	 * @param color
	 * @return List Integer
	 */
	public List<Integer> getSlotNumbersFromColor(String color) {
		if (StringUtils.isEmpty(color)) {
			throw new IllegalArgumentException("Color cannot be null");
		}
		List<Integer> collectedRegistrationNumbers = ticketMap.entrySet().stream()
				.filter(item -> ((Ticket) item.getValue()).vehicle.getColor().equalsIgnoreCase(color))
				.map(item -> ((Ticket) item.getValue()).slotNumber).collect(Collectors.toList());
		return collectedRegistrationNumbers;
	}

	/**
	 * returns the status of the ticketing system, a list of all the tickets
	 * converted to status objects
	 *
	 * @return List of VehicleAllocationStatus
	 */
	public List<VehicleAllocationStatus> getStatus() {
		List<VehicleAllocationStatus> vehicleAllocationStatusList = new ArrayList<>();
		ticketMap.entrySet().stream().forEach(item -> {
			vehicleAllocationStatusList.add(new VehicleAllocationStatus(((Ticket) item.getValue()).slotNumber,
					((Ticket) item.getValue()).vehicle.getRegistrationNumber(), ((Ticket) item).vehicle.getColor()));
		});
		return vehicleAllocationStatusList;
	}

	/**
	 * This will be available to This class only because its dependent.
	 */
	private class Ticket {
		int slotNumber;
		Vehicle vehicle;

		Ticket(int slotNumber, Vehicle vehicle) {
			this.slotNumber = slotNumber;
			this.vehicle = vehicle;
		}
	}
}
