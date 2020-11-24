package com.school.core.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.HomeworkDoneDto;
import com.school.core.entity.HomeworkDone;

public interface HomeworkDoneService {
     
//	boolean studentHomeworkDone(Long studentId,Long homeWorkId)throws Exception;

	boolean studentHomeworkDone(HomeworkDoneDto homeworkDoneDto)
			throws Exception;
	
	
	List<String> getHomeworkDone(String homeworkDoneId)throws Exception ;
	
	List<HomeworkDoneDto> getAllHomeworkDone(HomeworkDoneDto homewrokDone) throws Exception;
	
	String verifyHomework(Long homeworkDoneId,String feedBack)throws Exception;
	
	Map<String,Integer> studentsNotDoneHW(LocalDate homeworkDate,Long sectionId,Long subjectId)throws Exception;
	
	String putObjectInS3(Long studentId,MultipartFile file)throws Exception;
	
	
	
}
