package com.ht.rule.drools.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 描述：对head进行一个过滤，添加对接外联平台的app数据和相关数据
 *
 * @param * @param null
 * @author 张鹏
 * @return 
 * @date 2018/2/2 18:14
 */
//@Configuration
public class HeadFiterConfig {
    @Bean
    public RequestInterceptor headerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                //对head进行一个拦截
				Enumeration<String> headerNames = request.getHeaderNames();
				if (headerNames != null) {
					while (headerNames.hasMoreElements()) {
						//String name = headerNames.nextElement();
						//String values = request.getHeader(name);
						//requestTemplate.header(name, values);

					}
				}
                requestTemplate.header("Content-Type","application/json");
                requestTemplate.header("app","FK");
            }
        };
    }
}
