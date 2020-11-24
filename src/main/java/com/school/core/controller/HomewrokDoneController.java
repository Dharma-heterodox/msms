package com.school.core.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.school.core.dto.HomeworkDoneDto;
import com.school.core.dto.ResponseObj;
import com.school.core.service.HomeworkDoneService;
import com.school.core.util.Constant;
//Created By : Dharma
//Date : 15-09-2020
//Purpose : To handle all the request & response related to Finished Home work
@RestController
@RequestMapping("/homeworkDone")
public class HomewrokDoneController {
	
	@Autowired
	private HomeworkDoneService homewrokDoneService;
	
	private Logger logger = LoggerFactory.getLogger(HomewrokDoneController.class);
	
	@PostMapping(value="/saveImage/{studentId}")
	public ResponseObj putHomeworkImage(@PathVariable ("studentId") String studentId,
			@RequestParam ("file") MultipartFile file)throws Exception{
		logger.debug("putHomeworkImage method reached");
		return new ResponseObj(homewrokDoneService.putObjectInS3(Long.valueOf(studentId), file),HttpStatus.OK);
	}
	
	
//	Created By : Dharma
//	Date:15-09-2020
//	Purpose: To save the finished home work file from students
	@RequestMapping(value="/save" , method =RequestMethod.POST)
	public ResponseObj saveStudentHomework(@RequestBody HomeworkDoneDto homeworkDone) throws Exception
	{
		try {
			logger.debug("saveStudentHomework method called");
			
			homewrokDoneService.studentHomeworkDone(homeworkDone);
		}catch(Exception e) {
			logger.debug("saveStudentHomework error occured");
			logger.error("Error at saveStudentHomework "+ e.getMessage() );
			throw new Exception(e);
		}
		
		
		return new ResponseObj(Constant.HOMEWORK_MSG,HttpStatus.OK);
		
	}
	
//	Created By : Dharma
//	Date:15-09-2020
//	Purpose: To retrieve all the finished home work for teachers.
	@RequestMapping(value="/getHomeworks", method =RequestMethod.POST)
	public ResponseObj getAllForTeacher(@RequestBody HomeworkDoneDto homeworkDone)throws Exception{
		logger.debug("getAllForTeacher method called : "+homeworkDone.getTeacherId()+":"+homeworkDone.getGradeId()+":"+homeworkDone.getSectionId());
		return new ResponseObj(homewrokDoneService.getAllHomeworkDone(homeworkDone),HttpStatus.OK);
		
	}
	
//	Created By : Dharma
//	Date:15-09-2020
//	Purpose: To retrieve the finished home work for particular teacher and student .
	@GetMapping(value="/getStudentWork/{homeworkDoneId}")
	public ResponseObj getOneForTeacher(@PathVariable ("homeworkDoneId") String homeworkDoneId)throws Exception{
		logger.debug("getOneForTeacher method called : ");
		return new ResponseObj(homewrokDoneService.getHomeworkDone(homeworkDoneId),HttpStatus.OK);
		
	}
	
//	Created By : Dharma
//	Date:19-09-2020
//	Purpose: To retrieve the finished home work for particular teacher and student .
	@GetMapping (value="/verified/{homewrokDoneId}/{command}")
	public ResponseObj homeworkVerified(@PathVariable("homewrokDoneId") Long homewrokDoneId,@PathVariable("command") String command )throws Exception{
		logger.debug("homeworkVerified method called : "+homewrokDoneId+":"+command);
		return new ResponseObj(homewrokDoneService.verifyHomework(homewrokDoneId, command),HttpStatus.OK);
	}
	
//	Created By : Dharma
//	Date:15-09-2020
//	Purpose: To retrieve all the finished home work for teachers.
	@RequestMapping(value="/homeworkND", method =RequestMethod.POST)
	public ResponseObj getStudentListND(@RequestBody HomeworkDoneDto homeworkDone)throws Exception{
		logger.debug("getStudentListND method called : "+homeworkDone.getTeacherId()+":"+homeworkDone.getGradeId()+":"+homeworkDone.getSectionId());
		return new ResponseObj(homewrokDoneService.studentsNotDoneHW(homeworkDone.getHomeworkDate(), homeworkDone.getSectionId(), homeworkDone.getSubjectId()),HttpStatus.OK);
		
	}



}



