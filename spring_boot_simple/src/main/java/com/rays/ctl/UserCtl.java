package com.rays.ctl;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rays.common.BaseCtl;
import com.rays.common.DropDownList;
import com.rays.common.ORSResponse;
import com.rays.dto.AttachmentDTO;
import com.rays.dto.UserDTO;
import com.rays.form.UserForm;
import com.rays.service.AttachmentService;
import com.rays.service.RoleService;
import com.rays.service.UserService;

@RestController
@RequestMapping(value = "User")
public class UserCtl extends BaseCtl {

	@Autowired
	public UserService service;

	@Autowired
	public AttachmentService attachService;
	
	@Autowired
	public RoleService roleService;
	
	@GetMapping("preload")
	public ORSResponse preload() {
		
		ORSResponse res = new ORSResponse();
		
		List<DropDownList> roleList = roleService.search(null,0,0); 
		
		res.addResult("ImageId", roleList);
		
		return res;
		
	}

	@GetMapping("get/{id}")
	public ORSResponse get(@PathVariable Long id) {

		ORSResponse res = new ORSResponse();

		UserDTO dto = service.findById(id);

		if (dto != null) {
			res.addData(dto);
		} else {
			res.addMessage("No Record Found..!!");
		}

		return res;

	}

	@GetMapping("delete/{ids}")
	public ORSResponse delete(@PathVariable long[] ids) {

		ORSResponse res = new ORSResponse();

		if (ids != null && ids.length > 0) {

			for (long id : ids) {
				service.delete(id);
			}

			res.addMessage("Data Deleted Successfully..");

		}

		return res;

	}

	@GetMapping("profilePic/{imageId}")
	public void downloadPic(@PathVariable Long imageId, HttpServletResponse response) {

		try {

			UserDTO userDto = service.findById(imageId);

			AttachmentDTO attachmentDTO = null;

			if (userDto != null) {
				attachmentDTO = attachService.findById(userDto.getImageId());
			}

			if (attachmentDTO != null){
				response.setContentType(attachmentDTO.getType());
				OutputStream out = response.getOutputStream();
				out.write(attachmentDTO.getDoc());
				out.close();
			} else {
				response.getWriter().write("ERROR: File not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@PostMapping("profilePic/{userId}")
	public ORSResponse uploadPic(@PathVariable Long userId, @RequestParam("file") MultipartFile file,
			HttpServletRequest req) {

		AttachmentDTO attachmentDto = new AttachmentDTO(file);

		attachmentDto.setDescription("Profile Pic");

		attachmentDto.setUserId(userId);

		UserDTO userDto = service.findById(userId);

		if (userDto.getImageId() != null && userDto.getImageId() > 0) {
			attachmentDto.setId(userDto.getImageId());
		}
		Long imageId = attachService.save(attachmentDto);

		if (userDto.getImageId() == null) {
			userDto.setImageId(imageId);
			service.update(userDto);
		}

		ORSResponse res = new ORSResponse();

		res.addResult("ImageId", imageId);

		return res;

	}

	@PostMapping("save")
	public ORSResponse save(@RequestBody @Valid UserForm form, BindingResult bindingResult) {

		ORSResponse res = validate(bindingResult);

		if (!res.isSuccess()) {
			return res;
		}

		UserDTO dto = (UserDTO) form.getDto();

		if (dto.getId() != null && dto.getId() > 0) {

			service.update(dto);
			res.addData(dto.getId());
			res.addMessage("User Updated Successfully...");

		} else {
			Long pk = service.add(dto);
			res.addData(pk);
			res.addMessage("User Added Successfully...");
		}

		return res;

	}

	@PostMapping("search/{pageNo}")
	public ORSResponse search(@RequestBody UserForm form, @PathVariable int pageNo) {

		ORSResponse res = new ORSResponse();

		UserDTO dto = (UserDTO) form.getDto();

		List list = service.search(dto, pageNo, 5);

		if (list.size() == 0) {
			res.addMessage("No Record Found..!!");
		} else {
			res.addData(list);
		}
		return res;
	}

}
