package com.mind.xzs.core.auth;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.mind.xzs.core.config.SystemConfig;
import com.mind.xzs.core.wx.TokenHandlerInterceptor;
import com.mind.xzs.domain.PowerEntity;
import com.mind.xzs.service.PowerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private TokenHandlerInterceptor tokenHandlerInterceptor;


    private static final String[] exclude_path = {
            "/error",
            "/swagger-resources/**",
            "/api/wx/**",
            "/api/student/user/register",
            "/api/user/login"};


    @Autowired
    private PowerService powerService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置跨域访问
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }

    /**
     * 注册sa-token的拦截器，打开注解式鉴权功能 (如果您不需要此功能，可以删除此类)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        try {

            LambdaQueryWrapper<PowerEntity> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(PowerEntity::getEnable,1);
            List<PowerEntity> powers = powerService.list(queryWrapper);
            SaInterceptor saInterceptor=new SaInterceptor(h->{
                StpUtil.checkLogin();
                if(powers.size()>0){
                    powers.forEach(p->{
                        // 根据路由划分模块，不同模块不同鉴权
                        SaRouter.match(p.getPowerUrl(), r -> StpUtil.checkPermissionOr(p.getPowerCode(),"power:admin"));//如果是超管角色可跳过所有权限认证
                    });
                }
            });
            // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
            registry.addInterceptor(saInterceptor).addPathPatterns("/**")
                    .excludePathPatterns(exclude_path);

            //对微信拦截
            List<String> securityIgnoreUrls = systemConfig.getWx().getSecurityIgnoreUrls();
            String[] ignores = new String[securityIgnoreUrls.size()];
            registry.addInterceptor(tokenHandlerInterceptor)
                    .addPathPatterns("/api/wx/**")
                    .excludePathPatterns(securityIgnoreUrls.toArray(ignores));
//            super.addInterceptors(registry);

        }catch (NotLoginException e){
            throw e;
        }

//        // 配置自定义拦截器
//        registry.addInterceptor(new TraceAnnotationInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns(trace_exclude_path);
    }

}
