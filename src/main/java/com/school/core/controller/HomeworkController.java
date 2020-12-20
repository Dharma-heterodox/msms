package com.school.core.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.school.core.dto.AuthorizeDto;
import com.school.core.dto.HomeworkDto;
import com.school.core.dto.ResponseObj;
import com.school.core.dto.SubjectDto;
import com.school.core.service.HomeworkService;
import com.school.core.service.SubjectService;
import com.school.core.util.Constant;

@RestController
@RequestMapping("/homework")
public class HomeworkController {

	@Autowired
	private HomeworkService homeworkService;
	
	@Autowired
	private SubjectService subjectService;
	
	private Logger logger = LoggerFactory.getLogger(HomeworkController.class);

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<HomeworkDto> getAllHomeWorksBySchoolId(@PathVariable("schoolId") Long schoolId) {
		return homeworkService.getAllHomeWorksBySchoolId(schoolId);
	}

	@GetMapping(value="/student/{studentId}")
	@ResponseStatus(HttpStatus.OK)
	public List<HomeworkDto> getAllHomeWorksByStudent(@PathVariable("schoolId") Long schoolId,@PathVariable("studentId") Long studentId) {
		return homeworkService.getAllHomeWorksByStudentId(schoolId, studentId);
	}
	
	@GetMapping(value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public byte[] getHomeWork(@PathVariable("schoolId") Long schoolId, @PathVariable("id") Long id) {
		return homeworkService.getHomework(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public HomeworkDto createHomework(@PathVariable("schoolId") Long schoolId, @RequestParam("homework") String homework, 
			@RequestParam("file") MultipartFile file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		HomeworkDto homeworkDto = mapper.readValue(homework, HomeworkDto.class);
//		homeworkDto.setActive(true);
		return homeworkService.createHomework(schoolId, homeworkDto, file);
	}
	
//	Created By : Dharma
//	Date:20-10-2020
//	Purpose: To create a new homework by teacher
	@RequestMapping(value="/saveImage/{teacherId}" , method =RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseObj saveHomeworkImage(@PathVariable("teacherId") Long teacherId,@RequestParam ("file") MultipartFile file) throws Exception {
		String url=homeworkService.putHomeworkInS3(teacherId, file);
		logger.debug("POST save HW By teacher :: "+url);
		return new ResponseObj(url,HttpStatus.OK);
	}
	
//	Created By : Dharma
//	Date:20-10-2020
//	Purpose: To create a new homework by teacher
	@RequestMapping(value="/save" , method =RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String saveHomework(@RequestBody HomeworkDto homework) throws Exception {
		Long id=homeworkService.saveHomework(homework);
		logger.debug("POST save HW By teacher :: "+homework.getTitle());
		if(id!=null && id>0) {
			return "Homework for students created successfully";
		}
		return "Something went wrong";
	}
	
//	Created By : Dharma
//	Date:20-10-2020
//	Purpose: To list subjects for students
	@GetMapping(value="/subject/{gradeId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseObj getAllforGarde(@PathVariable("gradeId") Long gradeId)throws Exception
	{
		logger.debug("Get Subject list based on Grade :: "+gradeId);
		return new ResponseObj(homeworkService.getAllByGradeId(gradeId),HttpStatus.OK);
	}
//	Created By : Dharma
//	Date:20-10-2020
//	Purpose: To list Homework for Admin	
	@RequestMapping(value="/admin/hwList" , method =RequestMethod.POST)
	public ResponseObj getHW4Admin(@RequestBody HomeworkDto homework)throws Exception{
		logger.debug("Get Homework list for Admin,Section :: "+homework.getSectionId());
		return new ResponseObj(homeworkService.getAdminHWList(homework),HttpStatus.OK);
	}
//	Created By : Dharma
//	Date:20-10-2020
//	Purpose: Admin to Approve/reject Homework	
	@RequestMapping(value="/admin/auth" , method =RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseObj authorizeHomework(@RequestBody AuthorizeDto dto)throws Exception{
		logger.debug("Authorize Homework :: "+dto.getIds().size()+" :: "+dto.getAuth());
		String authDone=null;
		if(Constant.APPROVED==dto.getAuth()) {
			homeworkService.authorizeHomework(dto.getIds(), true);
			authDone="Approved";
		}else {
			homeworkService.authorizeHomework(dto.getIds(), false);
			authDone="Rejected";
		}
		return new ResponseObj("Homewrok List "+authDone+" Successfully",HttpStatus.OK);
	}
//	Created By : Dharma
//	Date:20-10-2020
//	Purpose: To list List Homework for Students
	@PostMapping("/student/hwList")
	@ResponseStatus(HttpStatus.OK)
	public ResponseObj getHW4Student(@RequestBody HomeworkDto homework)throws Exception{
		logger.debug("Get Homework list for Student,Section :: "+homework.getGradeId());
		return new ResponseObj(homeworkService.getHW4Student(homework.getSectionId(),homework.getHomeworkDate()),HttpStatus.OK);
	}
//	Created By : Dharma
//	Date:20-10-2020
//	Purpose: To retrieve incomplete homework for particular teacher
	@PostMapping("/teacher/hwList")
	@ResponseStatus(HttpStatus.OK)
	public ResponseObj editHomeworkList(@RequestBody HomeworkDto homework)throws Exception{
		logger.debug("Get Homework list for Edit :: "+homework.getTeacherId());
		return new ResponseObj(homeworkService.getHome4Edit(homework),HttpStatus.OK);
	}
//	Created By : Dharma
//	Date:20-10-2020
//	Purpose: To save completed homework
	@PostMapping("/teacher/editHW")
	@ResponseStatus(HttpStatus.OK)
	public ResponseObj editHomework(@RequestBody HomeworkDto homework)throws Exception{
		String response=null;
		logger.debug("Edit Homework :: "+homework.getId());
		int i=homeworkService.editHomework(homework);
		if(i>0) {
			return new ResponseObj("Homework updated Successfully",HttpStatus.OK);
		}else {
			return new ResponseObj("Homework update Failed",HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
}
