package com.onlylemi.zhihudaily.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ZhihuDailyAPI
 * <p>
 * https://github.com/izzyleung/ZhihuDailyPurify/wiki/知乎日报-API-分析
 *
 * @author: onlylemi
 * @time: 2016-06-23 13:26
 */
public class ZhihuDailyAPI {

    public static final String API_URL = "http://news-at.zhihu.com/api/4/";

    public static final String START_IMAGE_320x432 = "320x432";
    public static final String START_IMAGE_480x728 = "480x728";
    public static final String START_IMAGE_720x1184 = "720x1184";
    public static final String START_IMAGE_1080x1776 = "1080x1776";

    private ZhihuDailyAPI() {

    }

    /**
     * 启动界面图像获取
     * eg: http://news-at.zhihu.com/api/4/start-image/1080*1776
     *
     * @return
     */
    public static String startImage() {
        return API_URL + "start-image/" + START_IMAGE_1080x1776;
    }

    /**
     * 最新消息
     * eg: http://news-at.zhihu.com/api/4/news/latest
     *
     * @return
     */
    public static String latest() {
        return API_URL + "news/latest";
    }

    /**
     * 消息内容获取
     * eg: http://news-at.zhihu.com/api/4/news/3892357
     *
     * @param id 文章id
     * @return
     */
    public static String news(String id) {
        return API_URL + "news/" + id;
    }

    /**
     * 历史某天消息
     * eg: http://news.at.zhihu.com/api/4/news/before/20160622
     *
     * @param time 时间：格式 2016-06-23
     * @return
     */
    public static String daily(String time) {
        String[] strs = time.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]),
                Integer.parseInt(strs[2]));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return API_URL + "news/before/" + format.format(date);
    }

    /**
     * 新闻额外信息
     * eg: http://news-at.zhihu.com/api/4/story-extra/4232852
     *
     * @param id
     * @return
     */
    public static String storyExtra(String id) {
        return API_URL + "story-extra/" + id;
    }

    /**
     * 新闻对应评论查看
     * eg1: http://news-at.zhihu.com/api/4/story/4232852/long-comments
     * eg2: http://news-at.zhihu.com/api/4/story/4232852/short-comments
     *
     * @param id   新闻id
     * @param type
     * @return
     */
    public static String comments(String id, CommentType type) {
        if (type == CommentType.SHORT) {
            return API_URL + "story/" + id + "/short-comments";
        }
        return API_URL + "story/" + id + "/long-comments";
    }

    public enum CommentType {
        LONG,
        SHORT
    }

    /**
     * 主题日报列表查看
     * eg: http://news-at.zhihu.com/api/4/themes
     *
     * @return
     */
    public static String themes() {
        return API_URL + "themes";
    }

    /**
     * 主题日报内容查看
     * eg: http://news-at.zhihu.com/api/4/theme/11
     *
     * @param id
     * @return
     */
    public static String themeContent(String id) {
        return API_URL + "themes/" + id;
    }

    /**
     * 查看新闻的推荐者
     * eg: http://news-at.zhihu.com/api/4/story/7101963/recommenders
     *
     * @param id 新闻id
     */
    public static String recommenders(String id) {
        return API_URL + "story/" + id + "/recommenders";
    }

    /**
     * 获取某个专栏之前的新闻
     * eg: http://news-at.zhihu.com/api/4/theme/11/before/7119483
     *
     * @param themeId
     * @param newsId
     * @return
     */
    public static String themeNews(String themeId, String newsId) {
        return API_URL + "theme/" + themeId + "/before/" + newsId;
    }

}
