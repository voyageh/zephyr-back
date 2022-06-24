package com.zephyr.security.filter;

import com.zephyr.base.utils.JWTUtils;
import com.zephyr.base.utils.RedisUtils;
import com.zephyr.security.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // 获取Token
        String token = JWTUtils.handleReq(request);
        if (token != null) {
            // 验证token
            JWTUtils.verifyToken(token);
            // 判断当前上下文有无登录信息
            SecurityContext ctx = SecurityContextHolder.getContext();
            if (ctx.getAuthentication() == null) {
                // 赋予当前登录信息
                String userId = JWTUtils.getSubject(token);
                UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(userId);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String userId) {
        //从redis获取当前用户信息
        SecurityUser securityUser = (SecurityUser) redisUtils.get(userId);
        return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
    }
}