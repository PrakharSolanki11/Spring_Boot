package com.rays.ctl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.DemoResponse;
import com.rays.common.ORSResponse;
import com.rays.dto.Person;

@RestController
@RequestMapping(value = "Demo")
public class DemoCtl {

	@GetMapping
	public String display() {
		return "Spring Boot Application";
	}

	@GetMapping("display1")
	public DemoResponse display1() {

		DemoResponse res = new DemoResponse();

		Person person = new Person();

		person.setFirstName("Prakhar");
		person.setLastName("Solanki");
		person.setLogin("solankiprakhar11@gmail.com");
		person.setPassword("Prakhar@123");

		Person person1 = new Person();

		person1.setFirstName("Riya");
		person1.setLastName("Garhwal");
		person1.setLogin("riya@gmail.com");
		person1.setPassword("Riya@123");

		Map map = new HashMap();

		map.put("dto1", person);
		map.put("dto2", person1);

		res.setResult(map);

		res.setMessage("Spring Boot Started..");

		res.setData("Person Object"); 

		return res;
	}
	
	@GetMapping("testORSResponse")
	public ORSResponse testORSResponse() {

		ORSResponse res = new ORSResponse();

		Map errors = new HashMap();
		errors.put("firstName", "First name is required");
		errors.put("lastName", "Last name is required");
		errors.put("loginId", "Login id is required");
		errors.put("password", "Password is required");

		res.addInputError(errors);

		Person  person= new Person();

		person.setFirstName("Prakhar");
		person.setLastName("Solanki");
		person.setLogin("solankiprakhar11@gmail.com");
		person.setPassword("Prakhar@123");

		res.addData(person);

		res.addMessage("login-Id and password invalid");

		res.addResult("token", "token1223344");
		
		res.setSuccess(true);

		return res;
	}

}
