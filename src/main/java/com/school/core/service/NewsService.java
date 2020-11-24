package com.school.core.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.NewsDto;

public interface NewsService {

	NewsDto createNews(Long schoolId, NewsDto newsDto, MultipartFile file) throws Exception;
	List<NewsDto> getAllNewsBySchoolId(Long schoolId);
	byte[] getNews(Long id);
}
