package com.lei.mywechat.utils.datasource;

import com.lei.mywechat.utils.springutils.SpringContextUtil;
import org.apache.catalina.core.ApplicationContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DynamicDataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Pointcut("@annotation(com.lei.mywechat.utils.datasource.DS)")
    @Pointcut("execution(* com.lei.mywechat.mapper.*.*(..))")
    public void poinCut(){}

    @Before("poinCut()")
    private void before(JoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        DS ds = signature.getMethod().getAnnotation(DS.class);
        try {
            if(ds == null){//方法没有注解查找类
                ds = point.getTarget().getClass().getAnnotation(DS.class);
                if(ds == null){
                    Class[] classes = point.getTarget().getClass().getInterfaces();
                    for (Class aClass : classes) {
                        if(aClass.getAnnotation(DS.class) != null){
                            ds = (DS) aClass.getAnnotation(DS.class);
                            break;
                        }
                    }
                }
            }
            if(ds != null){
                DynamicDataSource.setDataSource(ds.value());
            }else{
                DynamicDataSource.setDataSource("dataSource1");
            }
        } catch (Exception e) {
            DynamicDataSource.setDataSource("dataSource1");
            e.printStackTrace();
        }
    }

    @AfterReturning("poinCut()")
    private void after(JoinPoint point){
        DynamicDataSource.clearDataSource();
    }
}
