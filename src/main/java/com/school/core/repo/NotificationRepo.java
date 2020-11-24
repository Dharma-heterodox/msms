package com.school.core.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.core.entity.Notification;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

}
