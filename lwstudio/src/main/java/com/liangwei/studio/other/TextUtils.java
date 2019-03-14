package com.liangwei.studio.other;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 制作特殊文本等
 * Created by weibao on 2018/5/26.
 */

public class TextUtils
{
    /*ั͡ζั͡伟ั͡ζั͡宝ั͡ζั͡✾
     *制作花藤文字
     */
    public static String makeHuaTengText(String text){
        String left="ั͡ζั͡";
        String right="ั͡ζั͡✾";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =left+text.charAt(i);
            result=result+charText;
            if(i==text.length()-1){
                result=result+right;
            }
        }
        return result;
    }
    /**
     *ℳℓ
     *制作Me文字
     */
    public static String makeMeText(String text){
        String left="ℳℓ";
        String right="ℳℓ";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =left+text.charAt(i);
            result=result+charText;
            if(i==text.length()-1){
                result=result+right;
            }
        }
        return result;
    }
    /**
     *
     *制作带刺文字
     */
    public static String makeDaiCiText(String text){
        String left="ۣۖิ";
        String right="ۣۖิ";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =left+text.charAt(i);
            result=result+charText;
            if(i==text.length()-1){
                result=result+right;
            }
        }
        return result;
    }


    /**
     *❦伟❧❦宝❧❦
     *制作心文字
     */
    public static String makeXinText(String text){
        String left="❦";
        String right="❧❦";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =text.charAt(i)+right;
            result=result+charText;
            if(i==0){
                result=left+result;
            }
        }
        return result;
    }


    /**
     *҉伟҉宝҉
     *制作菊花文字
     */
    public static String makeJuHuaText(String text){
        String resource="҉伟҉";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =text.charAt(i)+"";
            String replaced= resource.replace("伟",charText);
            result=result+replaced;

        }
        return result;
    }


    /**
     *⃟伟⃟宝⃟
     *制作菱形Diamond文字
     */
    public static String makeDiamondText(String text){
        String left="⃟";
        String right="⃟";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =text.charAt(i)+right;
            result=result+charText;
            if(i==0){
                result=left+result;
            }
        }
        return result;
    }

    /**
     *⃠伟⃠宝⃠
     *制作禁止Ban文字
     */
    public static String makeBanText(String text){
        String left="⃠";
        String right="⃠";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =text.charAt(i)+right;
            result=result+charText;
            if(i==0){
                result=left+result;
            }
        }
        return result;
    }

    /**
     *⃢伟⃢宝⃢
     *制作禁止胶囊capsule文字
     */
    public static String makeCapsuleText(String text){
        String source="⃢伟⃢";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =text.charAt(i)+"";
            result=result+source.replace("伟",charText);
        }
        return result;
    }

    /**
     *⃓伟⃓
     *制作小棒stick文字
     */
    public static String makeStickText(String text){
        String source="⃓伟⃓";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =text.charAt(i)+"";
            result=result+source.replace("伟",charText);
        }
        return result;
    }

    /**
     *⃓⃘⃘伟⃘⃘
     *制作带圈文字
     */
    public static String makeCirclesText(String text){
        String source="⃘⃘伟⃘⃘";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =text.charAt(i)+"";
            result=result+source.replace("伟",charText);
        }
        return result;
    }


    /**
     *⃓̶P̶h̶o̶e̶n̶i̶x̶
     *制作删除字
     */
    public static String makeDeleteText(String text){
        String left="̶̶";
        String right="̶̶";
        String result ="";
        for(int i=0;i<text.length();i++){
            String charText =text.charAt(i)+right;
            result=result+charText;
            if(i==0){
                //result=left+result;
            }
        }
        return result;
    }
    /**
     *⃓[̲̅边̲̅]
     *制作边框字
     */
    public static String makeBorderText(String text){
        String result = "";
        String target = "[̲̅边̲̅]";
        char[] chars = text.toCharArray();
        for (Character character:chars){
            result += target.replace("边",String.valueOf(character));
        }
        return result;
    }
    /**
     * ꧁翅꧂
     * 翅膀文字
     */
    public static String makeWingText(String text){
        String result = null;
        String target = "꧁翅꧂";
        return target.replace("翅",text);
    }

    /**
     * ༺ۣۖิ王༒ۣۖิ
     * king
     */
    public static String makeKingText(String text){
        String result = "";
        String target = "༺ۣۖิ王༒ۣۖิ";
        char[] chars = text.toCharArray();
        for (Character character:chars){
            result += target.replace("王",String.valueOf(character));
        }
        return result;
    }
    /**
     * 正则表达式
     */
    public static class RegularExpression{
        /**
         * 正则表达式校验邮箱
         * @param emaile 待匹配的邮箱
         * @return 匹配成功返回true 否则返回false;
         */
        public static boolean isEmail(String emaile){
            String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
            //正则表达式的模式
            Pattern p = Pattern.compile(RULE_EMAIL);
            //正则表达式的匹配器
            Matcher m = p.matcher(emaile);
            //进行正则匹配
            return m.matches();
        }
    }

    /**
     * 倒换文本
     * @param src
     */
    public static String oppsite(String src){
        StringBuilder stringBuilder = new StringBuilder();
        int index = src.length()-1;
        for (;index>=0;index--){
            stringBuilder.append(src.charAt(index));
        }
        return stringBuilder.toString();
    }
}
