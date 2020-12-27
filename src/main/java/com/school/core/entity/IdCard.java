package com.school.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class IdCard extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card_id")
    private Long id;
	@Column(length=50)
	private String idNumber;
	@Column(length=50)
	private String displayName;
	@Column(length=50)
	private String dob;
	@Column(length=50)
	private String bloodGroup;
	@Column(length=50)
	private String emergencyContactNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}
	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bloodGroup == null) ? 0 : bloodGroup.hashCode());
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((emergencyContactNo == null) ? 0 : emergencyContactNo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNumber == null) ? 0 : idNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdCard other = (IdCard) obj;
		if (bloodGroup == null) {
			if (other.bloodGroup != null)
				return false;
		} else if (!bloodGroup.equals(other.bloodGroup))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (emergencyContactNo == null) {
			if (other.emergencyContactNo != null)
				return false;
		} else if (!emergencyContactNo.equals(other.emergencyContactNo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idNumber == null) {
			if (other.idNumber != null)
				return false;
		} else if (!idNumber.equals(other.idNumber))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "IdCard [id=" + id + ", idNumber=" + idNumber + ", displayName=" + displayName + ", dob=" + dob
				+ ", bloodGroup=" + bloodGroup + ", emergencyContactNo=" + emergencyContactNo + "]";
	}
	
}
