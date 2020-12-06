package com.school.core.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.EmployeeDto;
import com.school.core.dto.GradeDto;
import com.school.core.dto.SectionDto;
import com.school.core.dto.SubjectDto;
import com.school.core.dto.TeacherMappingDto;
import com.school.core.entity.Employee;
import com.school.core.entity.User;
import com.school.core.entity.UserRequestErrors;
import com.school.core.entity.teacher.EmployeeReqErrors;
import com.school.core.entity.teacher.EmployeeRequest;
import com.school.core.repo.EmployeeRepo;
import com.school.core.repo.EmployeeRequestRepo;
import com.school.core.service.AddressService;
import com.school.core.service.EmployeeService;
import com.school.core.service.GradeService;
import com.school.core.service.SectionService;
import com.school.core.service.SubjectService;
import com.school.core.service.TeacherMappingService;
import com.school.core.service.UserService;
import com.school.core.util.Constant;
import com.school.core.util.ErrorCodeV;
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
	@Autowired
	private EmployeeRequestRepo employeeReqRepo;
	
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
	
	@Override
	@Transactional
	public boolean upload(Long schoolId, MultipartFile file) {
		List<EmployeeRequest> employees;
		try {
			employees = getEmployeesFromfile(schoolId,file);
			employeeReqRepo.saveAll(employees);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;
	}
	
	private List<EmployeeRequest> getEmployeesFromfile(long schoolId,MultipartFile file)throws Exception {
		Workbook workbook = null;
		Set<String> mobiles=null;
		List<EmployeeRequest> requestList=new ArrayList<EmployeeRequest>();
		try {
	        workbook = new XSSFWorkbook(file.getInputStream());
	        Sheet datatypeSheet = workbook.getSheetAt(0);
	        int length = datatypeSheet.getLastRowNum();
	        DataFormatter objDefaultFormat = new DataFormatter();
	        FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
	        mobiles=userService.getMobileNo();
	        for(int i=1; i<length; i++) {
	        	Row row = datatypeSheet.getRow(i);
	        	if(row == null)
	        		break;
	        	EmployeeRequest request=new EmployeeRequest();
	        	for(int j=1; j<row.getLastCellNum(); j++) {
	        		Cell currentCell = row.getCell(j);
	        		objFormulaEvaluator.evaluate(currentCell); // This will evaluate the cell, And any type of cell will return string value
	        	    String cellValueStr = objDefaultFormat.formatCellValue(currentCell,objFormulaEvaluator).trim();
	        	    try {
	        	    	switch(j) {
		        		case 1:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.EMPID_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.NUMBER_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors("EMP id "+ErrorCodeV.NO_REGEX));
		        			}else {
		        				request.setEmployeeId(Integer.valueOf(cellValueStr));
		        			}
		        			break;
		        		case 2:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors("Employee "+ErrorCodeV.NAME_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.NAME_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors("EMP "+ErrorCodeV.NAME_REGEX));
		        			}else {
		        				request.setEmployeeName(cellValueStr);
		        			}
		        			break;
		        		case 3:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.ACCOUNTNO_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.NUMBER_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors("Account "+ErrorCodeV.NO_REGEX));
		        			}else {
		        				request.setAccountNo(cellValueStr);
		        			}
		        			break;
		        		case 4:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.AADHAR_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.AADHAR_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.AADHAR_REGEX));
		        			}else {
		        				request.setAadhaarNo(cellValueStr);
		        			}
		        			break;
		        		case 5:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.GPAY_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.NUMBER_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.GPAY_REGEX));
		        			}else {
		        				request.setGrossPay(Long.valueOf(cellValueStr));
		        			}
		        			break;
		        		case 6:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.ACTIVE_NOTEMPTY));
		        			}else {
		        				request.setActive(cellValueStr.equals(Constant.YES));
		        			}
		        			break;
		        		case 7:
//		        			if(cellValueStr != null && cellValueStr.length() > 0) {
//			        			Date date = null;
//		        				if(cellValueStr.contains("/")) {
//		        					date = currentCell.getDateCellValue();
//		        				} else {
//				        			try {
//										date = new SimpleDateFormat("dd.MM.yyyy").parse(cellValueStr);
//									} catch (ParseException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//		        				}  
//		        				LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
//		        				employee.setDob(localDate);
//		        			}
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.GENDER_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.GENDER_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.GENDER_REGEX));
		        			}else {
		        				request.setGender(cellValueStr);
		        			}
		        			break;
		        		case 8:
		        			Date date = currentCell.getDateCellValue();
							if (date == null || StringUtils.isEmpty(date)) {
								request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.DOB_NOTEMPTY));
							}else {
								LocalDate localDate = new java.sql.Date(date.getTime()).toLocalDate();
								request.setDob(localDate);
							}
		        			break;	
		        		case 9:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors("EPF "+ErrorCodeV.NAME_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.NAME_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors("EPF "+ErrorCodeV.NAME_REGEX));
		        			}else {
		        				request.setEpfName(cellValueStr);
		        			}
		        			break;
		        		case 10:
		        			if(!isEmpty(cellValueStr) && !cellValueStr.matches(Constant.NUMBER_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors("PF "+ErrorCodeV.NO_REGEX));
		        			}else {
		        				request.setPfno(cellValueStr==""? null:Integer.valueOf(cellValueStr));
		        			}
		        			break;
//		        		case 11:
//		        			Date dobdate = currentCell.getDateCellValue();
//							if (dobdate == null || StringUtils.isEmpty(dobdate)) {
//								request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.DOJ_NOTEMPTY));
//							}else {
//								LocalDate localDate = new java.sql.Date(dobdate.getTime()).toLocalDate();
//								request.setDoj(localDate);
//							}//PF NO
//		        			break;
		        		case 11:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors("ESI "+ErrorCodeV.NAME_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.NAME_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors("ESI "+ErrorCodeV.NAME_REGEX));
		        			}else {
		        				request.setEsiName(cellValueStr);
		        			}
		        			break;
		        		case 12:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors("ESI "+ErrorCodeV.NO_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.NUMBER_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors("ESI "+ErrorCodeV.NO_REGEX));
		        			}else {
		        				request.setPfno(Integer.valueOf(cellValueStr));
		        			}
		        			break;
		        		case 13:
		        			Date dobdate = currentCell.getDateCellValue();
							if (dobdate == null || StringUtils.isEmpty(dobdate)) {
								request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.DOJ_NOTEMPTY));
							}else {
								LocalDate localDate = new java.sql.Date(dobdate.getTime()).toLocalDate();
								request.setDoj(localDate);
							}//PF NO
		        			break;
		        		case 14:
		        			request.setUanNo(cellValueStr);
		        			break;
		        		case 15:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.QUALF_NOTEMPTY));
		        			}else {
		        				request.setQualification(cellValueStr);
		        			}
		        			break;
		        		case 16:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.ADDRESS_NOTEMPTY));
		        			}else {
		        				request.setAddressOne(cellValueStr);
		        			}
		        			break;
		        		case 17:
		        			request.setPincode(cellValueStr==null ? null:Long.valueOf(cellValueStr));
		        			break;
		        		case 18:
		        			request.setPhoneNumber(cellValueStr);
		        			break;
		        		case 19:
		        			if(isEmpty(cellValueStr)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.MOBILE_NOTEMPTY));
		        			}else if(!cellValueStr.matches(Constant.MOBILE_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.MOBILE_REGEX));
		        			}else if(mobiles.contains(cellValueStr)){
		        				request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.MOBILE_FOUND));
		        			}else {
		        				request.setMobile(cellValueStr);
		        			}
		        			break;
		        		case 20:
		        			if(!isEmpty(cellValueStr)&& !cellValueStr.matches(Constant.MOBILE_REGEX)) {
		        				request.addErrorCode(new EmployeeReqErrors("Alternate"+ErrorCodeV.MOBILE_REGEX));
		        			}else {
		        				request.setAlternateMobile(cellValueStr);
		        			}
		        			break;	
		        		case 21:
		        			if (isEmpty(cellValueStr)) {
								request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.CASTCAT_NOTEMPTY));
							}else {
		        				request.setCasteCat(cellValueStr);
		        			}
		        			break;
		        		case 22:
		        			if (isEmpty(cellValueStr)) {
								request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.RELIGION_NOTEMPTY));
							}else {
		        				request.setReligion(cellValueStr);
		        			}
		        			break;
		        		case 23:
		        			if (isEmpty(cellValueStr)) {
								request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.CAST_NOTEMPTY));
							}else {
		        				request.setCaste(cellValueStr);
		        			}
		        			break;
		        		case 24:
		        			if (isEmpty(cellValueStr)) {
								request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.TYPE_NOTEMPTY));
							}else {
		        				request.setEmpType(cellValueStr);
		        			}
		        			break;
		        		case 25:
		        			if (isEmpty(cellValueStr)) {
								request.addErrorCode(new EmployeeReqErrors(ErrorCodeV.TYPEORDER_NOTEMPTY));
							}else {
		        				request.setTypeOrder(Integer.valueOf(cellValueStr));
		        			}
		        			break;
	        			default:
	        				break;
	        		}
	        	    	if (request.getErrors().size() > 0) {
							request.setRequestStatus(Constant.REQUEST_FAILED);
						} else {
							request.setRequestStatus(Constant.REQUEST_SUCCESS);
						}
						request.setSchoolId(schoolId);
						requestList.add(request);
	        		}catch(Exception jex) {
	        			log.error("Row :"+i+" Cell :" + j + " : "+cellValueStr+" - " + jex.getMessage());
	        		}
	            }
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
		return requestList;
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
//		employeeDto.setSubCategoryName(getRoleKey(employee.getSubCategory()));
		employeeDto.setSubjectTaking(employee.getSubjectTaking());
		employeeDto.setUserId(employee.getUserId());
		return employeeDto;
	}
	
//	private String getRoleKey(int intValue) {
//		switch(intValue) {
//		case 1:
//			return Roles.TEACHER.toString();
//		case 2:
//			return Roles.OFFICE_STAFF.toString();
//		case 3:
//			return Roles.PRINCIPAL.toString();
//		case 4:
//			return Roles.VICE_PRINCIPAL.toString();	
//		default:
//			return null;
//		}
//	}
	
	private boolean isEmpty(@Nullable String str) {
		return str == null || "".equals(str.trim());
	}
	
	
}
