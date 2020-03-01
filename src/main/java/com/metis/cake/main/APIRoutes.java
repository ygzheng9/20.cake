package com.metis.cake.main;

import com.metis.cake.config.CorsInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.config.Routes;

/**
 * @author ygzheng
 */
@Before(CorsInterceptor.class)
public class APIRoutes extends Routes {

    @Override
    public void config() {
        setBaseViewPath("/view/main");

        add("/api", APIController.class);
    }
}
