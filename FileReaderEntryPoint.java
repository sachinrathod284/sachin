package com.example.parkinglotsachin.pojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.parkinglotsachin.service.InputFileReaderService;

@Component
public class FileReaderEntryPoint {

	private static final Logger	logger = LoggerFactory.getLogger(FileReaderEntryPoint.class);
	
	public static void main(String[] args) throws Exception {
		InputFileReaderService commandService = InputFileReaderService.getInstance();
		BufferedReader bufferedReader;

		if (args.length == 0) {
			// Input Command Line Reader
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		} else {
			// Input File Reader
			String filePath = args[0];
			logger.info("got the file path {}", filePath);
			File inputFile =null;
			if(filePath.contains("parking_lot file_inputs.txt")) {
				 inputFile = new File(filePath);	
			}else {
				 inputFile = new File("C:\\install\\code\\sachin\\src\\main\\resources\\parking_lot file_inputs.txt");
			}
			bufferedReader = new BufferedReader(new FileReader(inputFile));
		}

		while (true) {
			String commandText = bufferedReader.readLine();
			if (commandText == null || "exit".equalsIgnoreCase(commandText)) {
				break;
			} else {
				boolean executionSuccess = commandService.execute(commandText);
				if (!executionSuccess) {
					break;
				}
			}
		}
	}

}
