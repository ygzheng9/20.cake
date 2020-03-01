package com.metis.cake.main;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;

/**
 * @author ygzheng
 */
public class APIController extends Controller {
    private static String okStatus = "1000";

    public void login() {
        Kv data = new Kv();
        data.set("msg", "欢迎回来");
        data.set("status", okStatus);

        renderJson(data);
    }
}
