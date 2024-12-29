package com.rays.ctl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.ORSResponse;
import com.rays.dto.UserDTO;
import com.rays.form.LoginForm;
import com.rays.form.UserForm;
import com.rays.service.UserService;

@RestController
@RequestMapping(value="Login")
public class LoginCtl extends BaseCtl { 
	
	@Autowired
	public UserService service;
	
	@PostMapping("auth")
	public ORSResponse login(@RequestBody @Valid LoginForm form , BindingResult bindingResult) { 
		
		ORSResponse res = validate(bindingResult);
		
		if(!res.isSuccess()) {
			return res;
		}
		
		UserDTO dto = new UserDTO();
		
		dto.setLoginId(form.getLoginId());
		dto.setPassword(form.getPassword());
		
        dto = service.authenticate(dto.getLoginId(), dto.getPassword()); 
        
        if(dto!=null) {
        	res.addData(dto);
        }else {
        	res.addMessage("Login-Id & Password invalid...!!");
        }
		
		return res;
		
	}
	
	@PostMapping("signUp")
	public ORSResponse signUp(@RequestBody @Valid UserForm form , BindingResult bindingResult) {
		
		ORSResponse res = validate(bindingResult);
		
		if(!res.isSuccess()){
			return res;
		}
		
		UserDTO dto = new UserDTO(); 
		
		dto.setFirstName(form.getFirstName());
		dto.setLastName(form.getLastName());
		dto.setLoginId(form.getLoginId());
		dto.setPassword(form.getPassword());
		dto.setDob(form.getDob());
		dto.setRoleId(2l); 
		
		Long pk = service.add(dto);
		
		res.addData(pk);
		res.addMessage("User Added Successfully...");
		
		
		return res;
		
	}

}
