/**
 * 
 */
package com.school.core.util;

/**
 * @author wings
 *
 */
public enum Roles {
	
	ADMIN(0),COORDINATOR(0),PARENT(0),STAFF(0),STUDENT(0),TEACHER(1),OFFICE_STAFF(2),PRINCIPAL(3),VICE_PRINCIPAL(4);
		
	public int intValue;	
	

	Roles(int value) {
		this.intValue=value;
	};
	
	
	public int getInvalue(Roles roles) {
		switch (roles) {
		case TEACHER:
		 return TEACHER.intValue;
		case OFFICE_STAFF:
			 return OFFICE_STAFF.intValue;
		case PRINCIPAL:
			 return PRINCIPAL.intValue;
		case VICE_PRINCIPAL:
			 return VICE_PRINCIPAL.intValue;	
		default:
			return 0;
		}
		
	}
	
	
	
	

}
