package com.metis.cake.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import com.google.common.collect.ImmutableList;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;

import java.io.File;
import java.util.*;

/**
 * @author ygzheng
 */

public class MainController extends Controller {
    public void metis() {
        int total = 11;

        Integer current = getInt("p", 1);

        int next = current + 1;
        int prev = current - 1;

        if (next > total) {
            next = 1;
        }

        if (prev < 1) {
            prev = 1;
        }

        set("currentPage", current);
        set("next", next);
        set("prev", prev);

        ImmutableList<Integer> vedios = ImmutableList.of(5, 8, 11);
        int overviewId = 3;

        if (current == overviewId) {
            render("metis_v3_2.html");
        } else if (vedios.contains(current)) {
            render("metis_v3_video.html");
        } else {
            render("metis_v3_1.html");
        }
    }

    public void metisQr() {
        // 生成二维码
        String data = "http://47.92.175.81:5000/login";

        // 渲染二维码图片，长度与宽度为 200 像素
        renderQrCode(data, 200, 200);
    }

    public void stats() {
        // 把制定目录下的图片，生成成 HTML，共本地浏览使用
        // 1. 本地目录下文件列表；2. 根据后缀名过滤文件；3. 根据文件长度排序；4. 将所有文件名合并成一个列表，送给 html 渲染
        // 统计学习的截屏
        String folderBase = "/Users/ygzheng/Desktop/统计学习方法";
        String outfile = "/Users/ygzheng/Desktop/统计学习方法/index.html";

        String p = get("p", "1");

        String path = folderBase + "/" + p;

        List<String> allNames = new ArrayList<>();
        File[] files = FileUtil.ls(path);
        for (File f : files) {
            if (FileUtil.extName(f).compareToIgnoreCase("png") == 0) {
                allNames.add(FileUtil.getName(f));
            }
        }

        // 判断文件名的长度，按长度进行分组，每组内排序，最后在合并成一个大的数组
        // 1.png 2.png 1-2.png 1-12.png
        Map<Integer, ArrayList<String>> partitions = new HashMap<>();
        for (String s : allNames) {
            Integer len = s.length();
            partitions.computeIfAbsent(len, k -> new ArrayList<>());
            partitions.get(len).add(s);
        }

        for (Map.Entry<Integer, ArrayList<String>> entry : partitions.entrySet()) {
            entry.getValue().sort(String::compareToIgnoreCase);
        }

        List<Integer> lens = new ArrayList<>(partitions.keySet());
        lens.sort(Comparator.comparingInt(a -> a));

        List<String> finals = new ArrayList<>();
        for (Integer i : lens) {
            finals.addAll(partitions.get(i));
        }
        set("names", finals);
        set("base", p);

        // 根据模板，生成文件
        Kv param = new Kv();
        param.set("names", finals);
        param.set("base", p);
        String html = renderToString("stats.html", param);
        FileUtil.writeString(html, outfile, CharsetUtil.CHARSET_UTF_8);


        render("stats.html");
    }
}
