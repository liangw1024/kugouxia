package com.liangwei.kugouxia.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 套图分类对象
 */
public class TaoTuCategoryBean {
    public static List<TaoTuCategoryBean> beans = new ArrayList<>();
    /**
     * 分类名字
     */
    private String name;
    /**
     * 分类ID
     */
    private String id;

    public TaoTuCategoryBean(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List getBeans() {
        if (beans.size() == 0) {
            beans.add(new TaoTuCategoryBean("动漫", ID.CATEGORY_COMIC));
            beans.add(new TaoTuCategoryBean("古风", ID.CATEGORY_ANTIQUITY));
            beans.add(new TaoTuCategoryBean("情侣", ID.CATEGORY_LOVERS));
            beans.add(new TaoTuCategoryBean("男生", ID.CATEGORY_BOY));
            beans.add(new TaoTuCategoryBean("女生", ID.CATEGORY_GRIL));
            beans.add(new TaoTuCategoryBean("明星", ID.CATEGORY_STAR));
            beans.add(new TaoTuCategoryBean("搞怪", ID.CATEGORY_TRICK));
        } else {

        }
        return beans;
    }

    public static class ID{
        /**动漫*/
        public static String CATEGORY_COMIC = "1";
        /**古风*/
        public static String CATEGORY_ANTIQUITY = "2";
        /**情侣*/
        public static String CATEGORY_LOVERS = "3";
        /**男生*/
        public static String CATEGORY_BOY = "4";
        /**女生*/
        public static String CATEGORY_GRIL = "5";
        /**明星*/
        public static String CATEGORY_STAR = "6";
        /**搞怪*/
        public static String CATEGORY_TRICK = "7";

    }

}
