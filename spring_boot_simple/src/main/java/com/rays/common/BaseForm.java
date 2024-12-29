package com.rays.common;

import com.rays.dto.UserDTO;

public class BaseForm { 
	
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	} 
	
	public BaseDTO getDTO() {
		return null;
	}
	
	public BaseDTO initDTO(UserDTO dto) {
		
		if(id!=null && id>0) {
			dto.setId(id);
		}else {
			dto.setId(null);
		}
		
		return dto;
	}
	
	
	
	

}
