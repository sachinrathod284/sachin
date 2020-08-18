/**
 * 
 */
package com.example.parkinglotsachin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.parkinglotsachin.pojo.FileReaderEntryPoint;
import com.example.parkinglotsachin.pojo.VehicleAllocationStatus;
import com.example.parkinglotsachin.service.VehicleTicketService;

/**
 * @author rathods
 *
 */
@RestController
public class VehicleParkingController {

	private static final Logger	logger = LoggerFactory.getLogger(VehicleParkingController.class);

	@RequestMapping(value = "/startReadingFileInput/{filePath}", method = RequestMethod.GET)
	public String startReadingFileInput(@PathVariable("filePath") String filePath) throws Exception {
		logger.info("FilePath is {0}", filePath);
		String[] input = new String[1];
		input[0] = filePath;
		FileReaderEntryPoint.main(input);
		logger.info("FilePath is {0} and it's processing is done", filePath);
		return filePath + "processing is done";
	}

	/*//not working because objects are managed runtime, so object not added in container of spring
	 * @RequestMapping(value = "/getSlotNumberbyRegistrationNumber/{regNo}", method = RequestMethod.GET)
	public int getSlotNumberbyRegistrationNumber(@PathVariable("regNo") String regNo) throws Exception {
		return VehicleTicketService.getInstance().getSlotNumberFromRegistrationNumber(regNo);
	}

	@RequestMapping(value = "/getAllVehicleStatus", method = RequestMethod.GET)
	public List<VehicleAllocationStatus> getAllVehicleStatus() throws Exception {
		return VehicleTicketService.getInstance().getStatus();
	}*/
}
