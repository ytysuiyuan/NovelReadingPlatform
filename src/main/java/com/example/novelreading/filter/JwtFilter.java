package com.example.novelreading.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Slf4j
public class JwtFilter extends BearerHttpAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        boolean res=super.onAccessDenied(request, response);//jwt的登录在这里面
        log.info("onAccessDenied "+res);
        if (!res){
            throw new AuthorizationException("token失效或异常，请重新登录");//jwt验证器的错误抛不上来，应该是shiro机制的不完善（）
        }
//        jwt验证失败导致的登陆失败里，拿不到jwt验证失败的具体异常，因为要试过多个realmjwt的token错了还会去试其他realm，
//        导致他把具体异常截断了，这里只拿得到一个"试过所有realm但是都没登陆成功"的异常。
        return res;
    }
}
