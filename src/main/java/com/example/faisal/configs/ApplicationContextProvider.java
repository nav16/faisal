package com.example.faisal.configs;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext _context;

    public static ApplicationContext getApplicationContext() {
        return _context;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (_context == null) _context = context;
    }

    public static <T> T getBean(Class<T> aClass) {
        return _context.getBean(aClass);
    }

}
