package com.zephyr.base.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zephyr.base.utils.JWTUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    private final HttpServletRequest request;

    @Autowired
    public MyMetaObjectHandler(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        // 读取token
        String userId = handleToken();
        this.strictInsertFill(metaObject, "createdDate", ZonedDateTime::now, ZonedDateTime.class);
        this.strictInsertFill(metaObject, "createdBy", () -> userId, String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = handleToken();
        this.strictUpdateFill(metaObject, "updatedDate", ZonedDateTime::now, ZonedDateTime.class);
        this.strictUpdateFill(metaObject, "updatedBy", () -> userId, String.class);
    }

    private String handleToken() {
        String token = JWTUtils.handleReq(request);
        if (token != null) {
            return JWTUtils.getSubject(token);
        }
        return null;
    }
}
