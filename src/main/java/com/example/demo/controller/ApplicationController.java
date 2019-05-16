package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.entity.ConversationInput;
import com.example.demo.entity.Employee;
import com.example.demo.services.POSTaggerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/demo")
public class ApplicationController {
	
	private static final Logger LOGGER = LogManager.getLogger(ApplicationController.class);
	
	//private static final Logger logger = LogManager.getLogger(ApplicationController.class);

	@Resource 
	EmployeeDao employeeService;
	
	@Resource
	POSTaggerService posTaggerService;
	
	@PostMapping(value = "/getPOSTags")
	public ResponseEntity<String[]> getPOSTags(@RequestBody ConversationInput conversationInput) {
		String[] tags = posTaggerService.tag(conversationInput.getSentence());
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<String[]>(tags, responseHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value = "/employeeList")
	public List<Employee> getEmployees() {
	return employeeService.findAll();
	}
	
	@GetMapping(value = "/downloadEmployeeList")
	public ResponseEntity<byte[]> downloadEmployeeList() throws Exception {
		List<Employee> employeeList = employeeService.findAll();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String employeeListJson = objectMapper.writeValueAsString(employeeList);
		byte[] employeeListByteArr = employeeListJson.getBytes();
		
		String fileName = "employees.json";
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentLength(employeeListByteArr.length);
		responseHeaders.setContentType(new MediaType("text","json"));
		responseHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		
		return new ResponseEntity<byte[]>(employeeListByteArr, responseHeaders, HttpStatus.OK);
		
	}
	
	@PostMapping(value = "/createEmp")
	public void createEmployee(@RequestBody Employee emp) {
	    LOGGER.info("emp: " + emp);	
		employeeService.insertEmployee(emp);
	}
	@PutMapping(value = "/updateEmp")
	public void updateEmployee(@RequestBody Employee emp) {
	 employeeService.updateEmployee(emp);
	}
	@PutMapping(value = "/executeUpdateEmp")
	public void executeUpdateEmployee(@RequestBody Employee emp) {
	 employeeService.executeUpdateEmployee(emp);
	}
	@DeleteMapping(value = "/deleteEmpById")
	public void deleteEmployee(@RequestBody Employee emp) {
	 employeeService.deleteEmployee(emp);
	}
	
	@RequestMapping(value = "/customerList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<String> getCustomers() {
		final String uri = "http://localhost:6217/api/customers/";

	    RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        //set up HTTP Basic Authentication Header
        //requestHeaders.add("Authorization", authorizationHeader);
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return response;
	}
	
}
