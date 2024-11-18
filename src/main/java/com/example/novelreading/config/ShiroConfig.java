package com.example.novelreading.config;

import com.example.novelreading.filter.CorsFilter;
import com.example.novelreading.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  Shiro配置类
 **/
@Configuration
@Slf4j
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SessionsSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();


        //配置自定义filter
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("cors",new CorsFilter());
        filterMap.put("jwt", new JwtFilter());//定义filter
        factoryBean.setFilters(filterMap);

        // 配置系统受限资源
        // 配置系统公共资源

        factoryBean.setLoginUrl("/user/login");
        factoryBean.setSuccessUrl("/success");
        // 设置无权限时跳转的 url;
//        factoryBean.setUnauthorizedUrl("/notRole");
        // 设置拦截器
        Map<String, String> map = new LinkedHashMap<>();

        //开放登陆和注册接口
        map.put("/user/login", "anon");
        map.put("/user/register", "anon");
        map.put("/user/check/*", "anon");

        // 放行swagger
        map.put("/swagger/**", "anon");
        map.put("/v2/api-docs", "anon");
        map.put("/swagger-ui.html", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/favicon.ico", "anon");
        map.put("/captcha.jpg", "anon");
        map.put("/csrf", "anon");

        // 关键：jwt验证过滤器。
        //↓ 此处采用shiro1.6新增的默认过滤器：authcBearer-BearerHttpAuthenticationFilter。
//        filterRuleMap.put("/**", "authcBearer");
        map.put("/**", "jwt");

        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        //map.put("/**", "anon");

        factoryBean.setFilterChainDefinitionMap(map);
        log.info("shiro拦截器工厂类注入成功");

        //↑ 注意：如果有其他过滤法则要配在/**上，则使用逗号间隔。

        //  perms[actuator] 对应 PermissionsAuthorizationFilter
        factoryBean.setGlobalFilters(Arrays.asList("cors","noSessionCreation"));
        //↑ 这句非常关键：配置NoSessionCreationFilter，把整个项目变成无状态服务。
        //将corsFilter配置成全局，注意不能放在上面jwt的位置。只有放在这里才能不受"anon"等其他过滤器的影响，是真正的全局。

        factoryBean.setSecurityManager(securityManager);
        return factoryBean;
    }

    @Bean
    protected Authorizer authorizer() {
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();


        return authorizer;
    }
}