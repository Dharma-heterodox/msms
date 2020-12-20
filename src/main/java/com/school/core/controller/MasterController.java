package com.school.core.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.school.core.entity.BoardMaster;
import com.school.core.entity.MediumMaster;
import com.school.core.service.MasterService;

@RestController
@RequestMapping("/master")
public class MasterController {

	@Autowired
	private MasterService masterService;
	
	@RequestMapping(value = "/board", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public BoardMaster createBoard(@Valid @RequestBody BoardMaster board, BindingResult bindingResult) {
		return masterService.saveBoard(board); 
    }
	
	@RequestMapping(value = "/medium", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    public MediumMaster createOrganization(@Valid @RequestBody MediumMaster medium, BindingResult bindingResult) {
		return masterService.saveMedium(medium); 
    }
	
}
