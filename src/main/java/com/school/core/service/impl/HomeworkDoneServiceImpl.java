package com.school.core.service.impl;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.controller.HomewrokDoneController;
import com.school.core.dto.HomeworkDoneDto;
import com.school.core.dto.StudentDto;
import com.school.core.entity.Homework;
import com.school.core.entity.HomeworkDone;
import com.school.core.entity.Student;
import com.school.core.repo.HomeworkDoneRepo;
import com.school.core.repo.HomeworkRepo;
import com.school.core.repo.StudentRepo;
import com.school.core.service.HomeworkDoneService;
import com.school.core.service.HomeworkService;
import com.school.core.util.S3ServiceUtil;

//Created By : Dharma
//Date : 13-09-2020
//Purpose : To do business logics for finished Home Work
@Service
public class HomeworkDoneServiceImpl implements HomeworkDoneService{
	

	@Autowired
	private HomeworkDoneRepo homeworkDoneRepo;
	
	@Autowired
	private StudentRepo studentRepo;
	
	@Value("${aws.s3.bucketurl}")
	private String s3BucketUrl;
	
	@Autowired
	private S3ServiceUtil s3Service;
	
	private Logger logger = LoggerFactory.getLogger(HomeworkDoneServiceImpl.class);
	
	final String urlSeperator="|";
	
	@Override
	public String putObjectInS3(Long studentId,MultipartFile file)throws Exception{
		String url=null;
		try {
			logger.debug("putObjectInS3 service invoked : "+studentId);
			if(file == null || file.isEmpty()) {
				throw new ValidationException("Please upload the finished home work image");
			}
			url=s3Service.putHWObjectinS3(studentId, file);
		}catch(Exception ex) {
			logger.debug("Exception at putObjectInS3 service");
			logger.error("Error at putObjectInS3 service : "+ex.getMessage());
			throw new Exception(ex);
		}
		return url;
		
	}
	//Created By : Dharma
	//Date : 13-09-2020
	//Purpose : To do save the completed student homework in DB
	@Override
	public boolean studentHomeworkDone(HomeworkDoneDto homeworkDoneDto) throws Exception {
		// TODO Auto-generated method stub
			File fileIo=null;
		try {
			logger.debug("studentHomeworkDone service invoked : "+homeworkDoneDto.getStudentId()+" : "+homeworkDoneDto.getHomeworkDate());
			
			HomeworkDone workDone=getHomeworkDone(homeworkDoneDto);
			homeworkDoneRepo.save(workDone);
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.debug("Exception at studentHomeworkDone service");
			logger.error("Error at studentHomeworkDone service : "+ex.getMessage());
			throw new Exception(ex);
		}
		
	}
	
	private HomeworkDone getHomeworkDone(HomeworkDoneDto homeworkDoneDto)throws Exception{
		HomeworkDone workDone=new HomeworkDone();
		workDone.setGradeId(homeworkDoneDto.getGradeId());
		workDone.setHomeworkDate(homeworkDoneDto.getHomeworkDate());
		workDone.setSchoolId(homeworkDoneDto.getSchoolId());
		workDone.setStudentId(homeworkDoneDto.getStudentId());
		workDone.setSectionId(homeworkDoneDto.getSectionId());
		workDone.setSubjectId(homeworkDoneDto.getSubjectId());
		workDone.setTeacherId(homeworkDoneDto.getTeacherId());
		workDone.setVerified(false);//Teachers not verified
//		workDone.setFile(file.getBytes());
		workDone.setTeacherHomeworkId(homeworkDoneDto.getTeacherHomeworkId());
		workDone.setFileURL(homeworkDoneDto.getS3URL());
		workDone.setStudentName(homeworkDoneDto.getStudentName());
		workDone.setRollNo(homeworkDoneDto.getRollNo());
		return workDone;
	}

	/*
	 * Swami
	 * 
	 */	
	@Override
	public List<String> getHomeworkDone(String homeworkDoneId)throws Exception {
		// TODO Auto-generated method stub
		String url=null;
		url = homeworkDoneRepo.findOneByHomeworkDoneId(Long.valueOf(homeworkDoneId));
		return s3Service.makeURL(url);
	}
	
	//Created By : Dharma
	//Date : 15-09-2020
	//Purpose : To do retrieve all home for teachers
	@Override
	public List<HomeworkDoneDto> getAllHomeworkDone(HomeworkDoneDto homewrokDone) throws Exception{
		try {
			List<HomeworkDone> homeworkDone=homeworkDoneRepo.findAllForTeacher(homewrokDone.getTeacherId(),homewrokDone.getGradeId(),homewrokDone.getSectionId(),homewrokDone.getHomeworkDate());
			return getHomeworkDoneDtoList(homeworkDone);
		}catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	private List<HomeworkDoneDto> getHomeworkDoneDtoList(List<HomeworkDone> homeworkDone)throws Exception{
		List<HomeworkDoneDto> homeworkDoneDtoList=new ArrayList<HomeworkDoneDto>(homeworkDone.size());
		homeworkDone.forEach(h -> {
			try {
				HomeworkDoneDto dto=new HomeworkDoneDto();
				dto.setDescription(h.getDescription());
				dto.setGradeId(h.getGradeId());
				dto.setHomeworkDate(h.getHomeworkDate());
				dto.setId(h.getId());
				dto.setSchoolId(h.getSchoolId());
				dto.setSectionId(h.getSectionId());
				dto.setStudentId(h.getStudentId());
				dto.setS3URL(h.getFileURL());
				dto.setSubjectId(h.getSubjectId());
				dto.setTeacherId(h.getTeacherId());
				dto.setStudentName(h.getStudentName());
				dto.setRollNo(h.getRollNo());
				dto.setS3Download(s3Service.makeURL(h.getFileURL()));
				homeworkDoneDtoList.add(dto);
			}catch(Exception ex) {
				logger.debug("Error in getHomeworkDoneDtoList : "+h.getStudentId());
				logger.error("Error in getHomeworkDoneDtoList : "+ex.getMessage());
			}
		});
		return homeworkDoneDtoList;
	}
	
	
	@Override
	public String verifyHomework(Long homeworkDoneId,String feedBack)throws Exception{
		
		int i=homeworkDoneRepo.verifyHomewrokDone(feedBack, homeworkDoneId);
		if(i==0) {
			return "Verification Failed";
		}
		return "Verified";
	}

	@Override
	public Map<String,Integer> studentsNotDoneHW(LocalDate homeworkDate,Long sectionId,Long subjectId)throws Exception{
		Map<String,Integer> studentDetail=new TreeMap<String,Integer>();
		List<Student> students= null;
		List<Long> studentsId=null;
//		List<HomeworkDone> homeworkDone=null;
		List<HomeworkDone> homeworkDone=homeworkDoneRepo.getAllBySectionId(sectionId, subjectId, homeworkDate);
		if(!CollectionUtils.isEmpty(homeworkDone)) {
			studentsId=getHWDoneStudents(homeworkDone);
			students=studentRepo.findStudentsHWND(studentsId);
			if(!CollectionUtils.isEmpty(students)) {
				students.stream().forEach(h -> {
					studentDetail.put(h.getFirstName(), h.getRollNo());
				});
			}
		}
		
		return studentDetail;
		
	}
	
	private List<Long> getHWDoneStudents(List<HomeworkDone> homeworkDone)throws Exception{
		List<Long> studentsID=new ArrayList<Long>(homeworkDone.size());
		homeworkDone.forEach(h -> {
			studentsID.add(h.getStudentId());
		});
		return studentsID;
	}
	
	
	
	
	
	

}
