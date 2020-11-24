package com.school.core.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.school.core.dto.NewsDto;
import com.school.core.entity.Homework;
import com.school.core.entity.News;
import com.school.core.repo.NewsRepo;
import com.school.core.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsRepo newsRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<NewsDto> getAllNewsBySchoolId(Long schoolId) {
		List<News> newss = newsRepo.findAllBySchoolId(schoolId);
		return newss.stream().map(a -> modelMapper.map(a, NewsDto.class)).collect(Collectors.toList());
	}
	
	public byte[] getNews(Long id) {
		News news = newsRepo.getOne(id);
		return news != null ? news.getFile() : null;
	}

	@Override
	public NewsDto createNews(Long schoolId, NewsDto newsDto, MultipartFile file) throws Exception {
		News news = modelMapper.map(newsDto, News.class);
		news.setSchoolId(schoolId);
		news.setActive(true);
		try {
			news.setFile(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		news = newsRepo.save(news);
		return modelMapper.map(news, NewsDto.class);
	}
}
