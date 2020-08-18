package com.example.parkinglotsachin.pojo;

public class Car implements Vehicle {
	private String registrationNumber;
	private String color;

	public Car(String registrationNumber, String color) {

		if (registrationNumber == null || color == null) {
			throw new IllegalArgumentException("No Data regarding Registration Number  && Color of Car");
		} else {
			this.registrationNumber = registrationNumber;
			this.color = color;
		}
	}

	public String getColor() {
		return this.color;
	}

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}
}