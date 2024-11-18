package com.example.novelreading.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CorsFilter extends PathMatchingFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        configHeaders(httpRequest, httpResponse);//options和其他方法共用
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {

            log.info("收到一个OPTIONS请求--"+httpRequest.getRequestURI());
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }

        return super.preHandle(request, response);
    }


    private void configHeaders(HttpServletRequest request, HttpServletResponse response) {
        // 跨域的header设置
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        // 应该明确允许多种HTTP方法
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With");
        // 防止乱码，适用于传输JSON数据
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
    }

}
