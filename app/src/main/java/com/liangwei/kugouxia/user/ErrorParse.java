package com.liangwei.kugouxia.user;

/**
 * Created by weibao on 2018/3/17.
 */

public class ErrorParse {
    public static String parseBmobErrorCode(int code) {
        String result = null;
        switch (code) {
            default:
                result = "未知错误码" + code;
                break;
            case 9003:
                result = "上传文件出错";
                break;
            case 9004:
                result = "上传文件失败";
                break;
            case 9007:
                result = "文件大小超过10M";
                break;
            case 9008:
                result = "上传文件不存在";
                break;
            case 9022:
                result = "上传文件失败，请重试";
                break;
            case 9010:
                result = "网络超时";
                break;
            case 9012:
                result = "context 为空";
                break;
            case 9015:
                result = "未知错误";
                break;
            case 9016:
                result = "无网络";
                break;
            case 9019:
                result = "格式不正确";
                break;

        }

        return result;
    }

    public static String parseYouDaoError(int code) {
        String result = null;
        switch (code) {
            case 101:
                result = "缺少必填的参数，出现这个情况还可能是 et 的值和实际加密方式不对应";
                break;
            case 102:
                result = "不支持的语言类型";
                break;
            case 103:
                result = "翻译文本过长";
                break;
            case 104:
                result = "不支持的 API 类型";
                break;
            case 105:
                result = "不支持的签名类型";
                break;
            case 106:
                result = "不支持的响应类型";
                break;
            case 107:
                result = "不支持的传输加密类型";
                break;
            case 108:
                result = "appKey 无效，注册账号， 登录后台创建应用和实例并完成绑定， 可获得应用ID和密钥等信息，其中应用ID就是appKey（ 注意不是应用密钥）";
                break;
            case 109:
                result = "batchLog 格式不正确";
                break;
            case 110:
                result = "无相关服务的有效实例";
                break;
            case 111:
                result = "开发者账号无效，可能是账号为欠费状态";
                break;
            case 201:
                result = "解密失败，可能为 DES,BASE64,URLDecode 的错误";
                break;
            case 202:
                result = "签名检验失败";
                break;
            case 203:
                result = "访问 IP 地址不在可访问 IP 列表";
                break;
            case 301:
                result = "辞典查询失败";
                break;
            case 302:
                result = "翻译查询失败";
                break;
            case 303:
                result = "服务端的其它异常";
                break;
            case 401:
                result = "账户已经欠费";
                break;
            default:
                result = "未知error";
                break;

        }
        return result;
    }


}
