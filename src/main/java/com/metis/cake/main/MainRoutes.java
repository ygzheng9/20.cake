package com.metis.cake.main;

import com.jfinal.config.Routes;

/**
 * @author ygzheng
 */
public class MainRoutes extends Routes {
    @Override
    public void config() {
        setBaseViewPath("/view/main");

        add("/", MainController.class);
    }
}
