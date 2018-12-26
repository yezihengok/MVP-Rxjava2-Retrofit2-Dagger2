package com.demo.mvp.bean;

/**
 *
 * 新闻model
 * @author yzh-t105
 * @time 2018/12/20 10:10
 */
public class News {
    private String title;
    private String content;
    private String time;

    public News() {
    }

    public News(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
