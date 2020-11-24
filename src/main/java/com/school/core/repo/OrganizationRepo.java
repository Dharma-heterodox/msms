package com.school.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Organization;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Long> {

}
