/**
 * 
 */
package com.example.parkinglotsachin.pojo;

/**
 * @author rathods This will tell you what is status of parking and how its
 *         occupied
 */

public class VehicleAllocationStatus {

	private int slot;
	private String registrationNumber;
	private String color;

	public VehicleAllocationStatus(int slotNumber, String registrationNumberTmp, String colorTmp) {
		this.slot = slotNumber;
		this.registrationNumber = registrationNumberTmp;
		this.color = colorTmp;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "VehicleAllocationStatus [slot=" + slot + ", registrationNumber=" + registrationNumber + ", color="
				+ color + "]";
	}

}
