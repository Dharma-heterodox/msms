package com.school.core.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.EmployeeDto;
import com.school.core.dto.GradeDto;
import com.school.core.dto.SectionDto;
import com.school.core.dto.SubjectDto;
import com.school.core.dto.TeacherMappingDto;
import com.school.core.entity.Employee;
import com.school.core.entity.User;
import com.school.core.repo.EmployeeRepo;
import com.school.core.service.AddressService;
import com.school.core.service.EmployeeService;
import com.school.core.service.GradeService;
import com.school.core.service.SectionService;
import com.school.core.service.SubjectService;
import com.school.core.service.TeacherMappingService;
import com.school.core.service.UserService;
import com.school.core.util.Constant;
import com.school.core.util.Roles;
import com.school.core.util.StringUtil;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AddressService addressService;
	@Autowired
	private UserService userService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private SectionService sectionService;
	@Autowired
	private TeacherMappingService mappingService;
	@Autowired
	private SubjectService subjectService;
	
	@Override
	public List<EmployeeDto> getAllEmployeeBySchoolId(Long schoolId) {
		List<Employee> employees = employeeRepo.findAllBySchoolId(schoolId);
		return employees.stream().map(a -> modelMapper.map(a, EmployeeDto.class)).collect(Collectors.toList());
	}

	@Override
	public EmployeeDto createEmployee(Long schoolId, EmployeeDto employeeDto) {
		Employee existingEmployee = employeeRepo.getEmployeeByMobile(schoolId, employeeDto.getMobile());
		if(existingEmployee != null) {
			throw new ValidationException("Employee already exist for mobile :"+employeeDto.getMobile());
		}
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		//AddressDto addressDto = employeeDto.getAddress();
		employee.setSchoolId(schoolId);
		employee.setActive(true);
		User user=createEmployeeAccount(employeeDto);
		employee.setUserId(user.getId());
		employee = employeeRepo.save(employee);
		EmployeeDto updatedEmployeeDto =  modelMapper.map(employee, EmployeeDto.class);
		GradeDto grade = null;
		SubjectDto subject = null;
		SectionDto section = null;
		if(employee.getSubjectTaking() != null && employee.getSubjectTaking().length() > 0) {
			subject = subjectService.getSubject(schoolId, null, employee.getSubjectTaking());
			if(subject == null) {
				subject = new SubjectDto();
				subject.setSubjectName(StringUtil.fullCapitalize(employee.getSubjectTaking()));
				subject.setActive(true);
				subject = subjectService.createSubject(schoolId, subject);
			}
		}
		if(employeeDto.getClassHandling() != null && employeeDto.getClassHandling().length() > 0) {
			TeacherMappingDto teacherMapping = new TeacherMappingDto();
			teacherMapping.setAcademicYear(Constant.lastAcademicYear);
			teacherMapping.setClassTeacher(true);
			String gradeName = StringUtil.getGrade(employeeDto.getClassHandling());
			if(gradeName != null && !(gradeName.contains("KG") || gradeName.contains("K.G"))) {
				gradeName = gradeName + " Std";
			}
			grade = gradeService.getByGrade(schoolId, gradeName);
			section = sectionService.getBySection(schoolId, grade.getId(), StringUtil.getSection(employeeDto.getClassHandling()));
			teacherMapping.setGradeId(grade.getId());
			teacherMapping.setSectionId(section.getId());
			teacherMapping.setSubjectId(subject.getId());
			teacherMapping.setTeacherId(employee.getId());
			teacherMapping.setSection(section.getSection());
			teacherMapping.setSubject(subject.getSubjectName());
			teacherMapping.setGrade(grade.getGrade());
			mappingService.createTeacherMapping(schoolId, teacherMapping);
		}
		
		/*
		 * if(addressDto != null && addressDto.getAddressLine1() != null) {
		 * addressDto.setOrganizationId(employeeDto.getId());
		 * addressService.createAddress(addressDto); }
		 */
		return employeeDto;
	}
	
	public EmployeeDto getEmployee(Long employeeId) {
		Employee emp = employeeRepo.getOne(employeeId);
		return modelMapper.map(emp, EmployeeDto.class);
	}
	
	@Override
	public EmployeeDto getEmployeeByUserId(Long userId)throws Exception{
		Employee emp = employeeRepo.getEmployeeByUserId(userId);
		return getDtoObject(emp);
	}
	
	private User createEmployeeAccount(EmployeeDto employeeDto) {
		if(employeeDto.getMobile() != null && employeeDto.getMobile().length() > 0) {
			User user = new User();
			user.setUserName(employeeDto.getMobile());
			user.setMobile(employeeDto.getMobile());
			user.setFirstName(employeeDto.getFirstName());
			user.setLastName(employeeDto.getLastName());
			String password = "test123$";
			if(employeeDto != null) {
				password = employeeDto.getDob().toString().replaceAll("-", "");
			}
			System.out.println("username === " + user.getUserName() +"       password ==="+password);
			user.setPassword(password);
			user.setEmail(employeeDto.getEmail());
			user.setActive(true);
			/*
			 * if(parent.getSchoolId() != null) { Organization org =
			 * orgservice.getOrganization(employee.getSchoolId()); Set<Organization> orgs =
			 * new HashSet<Organization>(); orgs.add(org); user.setOrganizations(orgs); }
			 */
			userService.saveEmployeeAccount(user);
			return user;
		} else return null;
	}
	
	public boolean upload(Long schoolId, MultipartFile file) {
		Set<EmployeeDto> employees = getEmployeesFromfile(file);
		if(!CollectionUtils.isEmpty(employees)) {
			for(EmployeeDto employeeDto : employees) {
				try {
					createEmployee(schoolId, employeeDto);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		return true;
	}
	
	private Set<EmployeeDto> getEmployeesFromfile(MultipartFile file) {
		Workbook workbook = null;
		Set<EmployeeDto> employees = new HashSet<EmployeeDto>();
		try {
	        workbook = new XSSFWorkbook(file.getInputStream());
	        Sheet datatypeSheet = workbook.getSheetAt(0);
	        int length = datatypeSheet.getLastRowNum();
	        DataFormatter objDefaultFormat = new DataFormatter();
	        FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
	        EmployeeDto employee = null;
	        for(int i=2; i<length; i++) {
	        	Row row = datatypeSheet.getRow(i);
	        	if(row == null)
	        		break;
	        	employee = new EmployeeDto();
	        	for(int j=1; j<row.getLastCellNum(); j++) {
	        		Cell currentCell = row.getCell(j);
	        		objFormulaEvaluator.evaluate(currentCell); // This will evaluate the cell, And any type of cell will return string value
	        	    String cellValueStr = objDefaultFormat.formatCellValue(currentCell,objFormulaEvaluator);
	        		switch(j) {
		        		case 1:
		        			String name = StringUtil.fullCapitalize(cellValueStr);
		        			employee.setFirstName(name);
		        			employee.setDisplayName(name);
		        			break;
		        		case 2:
		        			employee.setClassHandling(cellValueStr);
		        			break;
		        		case 3:
		        			employee.setCategory(StringUtil.fullCapitalize(cellValueStr));
		        			break;
		        		case 4:
		        			employee.setDesignation(cellValueStr);
		        			break;
		        		case 5:
		        			employee.setQualification(cellValueStr);
		        			break;
		        		case 6:
		        			employee.setSubjectTaking(StringUtil.fullCapitalize((cellValueStr)));
		        			break;
		        		case 7:
		        			if(cellValueStr != null && cellValueStr.length() > 0) {
			        			Date date = null;
		        				if(cellValueStr.contains("/")) {
		        					date = currentCell.getDateCellValue();
		        				} else {
				        			try {
										date = new SimpleDateFormat("dd.MM.yyyy").parse(cellValueStr);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		        				}  
		        				LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
		        				employee.setDob(localDate);
		        			}
		        			break;
		        		case 8:
		        			String name1 = StringUtil.fullCapitalize(cellValueStr);
		        			if(name1.startsWith("W/o")) {
		        				employee.setSpouseName(name1);
		        				employee.setGender("Female");
		        			} else if(name1.startsWith("S/o")) {
		        				employee.setGender("Male");
		        			} else {
		        				employee.setGender("Female");
		        			}
		        			break;	
		        		case 9:
		        			employee.setAddress(StringUtil.fullCapitalize(cellValueStr));
		        			break;
		        		case 10:
		        			if(cellValueStr != null && cellValueStr.length() > 0) {
			        			Date doj = null;
		        				if(cellValueStr.contains("/")) {
		        					doj = currentCell.getDateCellValue();
		        				} else {
				        			try {
										doj = new SimpleDateFormat("dd.MM.yyyy").parse(cellValueStr);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		        				}
			        			LocalDate dateOfJoin = new java.sql.Date(doj.getTime()).toLocalDate();
			        			employee.setDateOfJoin(dateOfJoin);
		        			}
		        			break;
		        		case 11:
		        			employee.setMarritalStatus(StringUtil.fullCapitalize(cellValueStr));
		        			break;
		        		case 12:
		        			employee.setMobile(cellValueStr);
		        			break;
		        		case 13:
		        			employee.setAlternateMobile(cellValueStr);
		        			break;
	        			default:
	        				break;
	        		}
	            }
	        	employees.add(employee);
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
			if(workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return employees;
	}
	
	private EmployeeDto getDtoObject(Employee employee)throws Exception{
		EmployeeDto employeeDto=new EmployeeDto();
		employeeDto.setId(employee.getId());
		employeeDto.setAadhaarNo(employee.getAadhaarNo());
		employeeDto.setActive(true);
		employeeDto.setAddress(employee.getAddress());
		employeeDto.setAlternateMobile(employee.getAlternateMobile());
		employeeDto.setCategory(employee.getCategory());
//		employeeDto.setClassHandling(employee.get);
		employeeDto.setDateOfJoin(employee.getDateOfJoin());
		employeeDto.setDesignation(employee.getDesignation());
		employeeDto.setDisplayName(employee.getDisplayName());
		employeeDto.setDob(employee.getDob());
		employeeDto.setEmail(employee.getEmail());
		employeeDto.setFirstName(employee.getFirstName());
		employeeDto.setGender(employee.getGender());
		employeeDto.setLastName(employee.getLastName());
		employeeDto.setMajor(employee.getMajor());
		employeeDto.setMarritalStatus(employee.getMarritalStatus());
		employeeDto.setMobile(employee.getMobile());
		employeeDto.setQualification(employee.getQualification());
		employeeDto.setSpouseName(employee.getSpouseName());
		employeeDto.setSubCategory(employee.getSubCategory());
		employeeDto.setSubCategoryName(getRoleKey(employee.getSubCategory()));
		employeeDto.setSubjectTaking(employee.getSubjectTaking());
		employeeDto.setUserId(employee.getUserId());
		return employeeDto;
	}
	
	private String getRoleKey(int intValue) {
		switch(intValue) {
		case 1:
			return Roles.TEACHER.toString();
		case 2:
			return Roles.OFFICE_STAFF.toString();
		case 3:
			return Roles.PRINCIPAL.toString();
		case 4:
			return Roles.VICE_PRINCIPAL.toString();	
		default:
			return null;
		}
	}
	
	
}
