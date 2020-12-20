package com.school.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.dto.OrganizationDto;
import com.school.core.dto.ResponseObj;
import com.school.core.entity.User;
import com.school.core.service.OrganizationService;
import com.school.core.service.UserService;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
    public OrganizationDto createOrganization(@Valid @RequestBody OrganizationDto organization, BindingResult bindingResult) {
		return organizationService.saveOrganization(organization); 
    }
	
	@GetMapping(value = "/{orgId}" )
	@ResponseStatus(HttpStatus.OK)
	public OrganizationDto createSection(@PathVariable("orgId") Long orgId)throws Exception {
		return organizationService.getOrganizationDetail(orgId);
	}
	
	@RequestMapping(value={"/api/users/{email}"}, method = RequestMethod.GET)
	@ResponseBody
    public User login(@PathVariable("email") String email){
        return userService.findUserByEmail(email);
    }
	
	@RequestMapping(value={"/users/{id}"}, method = RequestMethod.GET)
	@ResponseBody
    public User getUser(@PathVariable("id") String id){
        return userService.findById(Long.parseLong(id));
    }
}
