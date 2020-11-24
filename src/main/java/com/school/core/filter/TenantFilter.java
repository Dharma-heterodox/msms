package com.school.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.school.core.context.TenantContext;

@Component
public class TenantFilter implements Filter {

  private static final String TENANT_HEADER = "X-TenantID";

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//    HttpServletResponse response = (HttpServletResponse) servletResponse;
//    HttpServletRequest request = (HttpServletRequest) servletRequest;
//    final String requestTokenHeader = request.getHeader("Authorization");
//    final String tenantHeader = request.getHeader(TENANT_HEADER);
//    if(requestTokenHeader != null && requestTokenHeader.startsWith("jwt ")) { 
//	    if (tenantHeader != null && !tenantHeader.isEmpty()) {
//	      TenantContext.setCurrentTenant(Integer.parseInt(tenantHeader));
//	    } else {
//	      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//	      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//	      response.getWriter().write("{\"error\": \"No tenant supplied\"}");
//	      response.getWriter().flush();
//	      return;
//	    }
//	  }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
  }
}