package com.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author haibing.xiao
 *         Date 2013-05-29
 *         <p>构造回复XML</p>
 */
public class XmlRevertUtil {

    /**
     * 构建回复文本,回复的消息内容,长度不超过2048字节
     *
     * @param jsonObject
     * @return String
     */
    public static String creatRevertText(JSONObject jsonObject) {
        StringBuffer revert = new StringBuffer();
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[" + jsonObject.get("ToUserName") + "]]></ToUserName>");
        revert.append("<FromUserName><![CDATA[" + jsonObject.get("FromUserName") + "]]></FromUserName>");
        revert.append("<CreateTime>" + jsonObject.get("CreateTime") + "</CreateTime>");
        revert.append("<MsgType><![CDATA[text]]></MsgType>");
        revert.append("<Content><![CDATA[" + jsonObject.get("Content") + "]]></Content>");
        revert.append("<FuncFlag>0</FuncFlag>");
        revert.append("</xml>");
        return revert.toString();
    }

    public static String creatRequestText(String content) {
        StringBuffer revert = new StringBuffer();
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[gh_d76be90717c7]]></ToUserName>");
        revert.append("<FromUserName><![CDATA[o3TXNjgvax9Ao4DhIYdiLhtwLuBg]]></FromUserName>");
        revert.append("<CreateTime>1376383328</CreateTime>");
        revert.append("<MsgType><![CDATA[text]]></MsgType>");
        revert.append("<Content><![CDATA[" + content + "]]></Content>");
        revert.append("<MsgId>5911521380519646812</MsgId>");
        revert.append("</xml>");
        return revert.toString();
    }

    public static String creatRequestVoice(String content) {
        StringBuffer revert = new StringBuffer();
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[gh_d76be90717c7]]></ToUserName>");
        revert.append("<FromUserName><![CDATA[o3TXNjgvax9Ao4DhIYdiLhtwLuBg]]></FromUserName>");
        revert.append("<CreateTime>1376383328</CreateTime>");
        revert.append("<MsgType><![CDATA[voice]]></MsgType>");
        revert.append("<MediaId><![CDATA[media_id]]></MediaId>");
        revert.append("<Format><![CDATA[" + content + "]]></Format>");
        revert.append("<MsgId>5911521380519646812</MsgId>");
        revert.append("</xml>");
        return revert.toString();
    }

    /**
     * 构建回复音乐
     *
     * @param jsonObject
     * @return String
     */
    public static String creatRevertMusic(JSONObject jsonObject) {
        StringBuffer revert = new StringBuffer();
        JSONObject music = (JSONObject) jsonObject.get("Music");
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[" + jsonObject.get("ToUserName") + "]]></ToUserName>");
        revert.append("<FromUserName><![CDATA[" + jsonObject.get("FromUserName") + "]]></FromUserName>");
        revert.append("<CreateTime>" + jsonObject.get("CreateTime") + "</CreateTime>");
        revert.append("<MsgType><![CDATA[music]]></MsgType>");
        revert.append("<Music>");
        revert.append("<Title><![CDATA[" + music.get("Title") + "]]></Title>");
        revert.append("<Description><![CDATA[" + music.get("Description") + "]]></Description>");
        revert.append("<MusicUrl><![CDATA[" + music.get("MusicUrl") + "]]></MusicUrl>");
        revert.append("<HQMusicUrl><![CDATA[" + music.get("HQMusicUrl") + "]]></HQMusicUrl>");
        revert.append(" </Music>");
        revert.append("<FuncFlag>0</FuncFlag>");
        revert.append("</xml>");
        return revert.toString();
    }

    /**
     * 构建回复图文 限制为10条以内
     *
     * @param jsonObject
     * @return String
     */
    public static String creatRevertNews(JSONObject jsonObject) {
        StringBuffer revert = new StringBuffer();
        JSONArray jsonArray = (JSONArray) jsonObject.get("Articles");
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[" + jsonObject.get("ToUserName") + "]]></ToUserName>");
        revert.append("<FromUserName><![CDATA[" + jsonObject.get("FromUserName") + "]]></FromUserName>");
        revert.append("<CreateTime>" + jsonObject.get("CreateTime") + "</CreateTime>");
        revert.append("<MsgType><![CDATA[news]]></MsgType>");
        revert.append("<ArticleCount>" + jsonArray.size() + "</ArticleCount>");
        revert.append("<Articles>");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject item = (JSONObject) jsonArray.get(i);
            revert.append("<item>");
            revert.append("<Title><![CDATA[" + item.get("Title") + "]]></Title> ");
            revert.append("<Description><![CDATA[" + item.get("Description") + "]]></Description>");
            revert.append("<PicUrl><![CDATA[" + item.get("PicUrl") + "]]></PicUrl>");
            revert.append("<Url><![CDATA[" + item.get("Url") + "]]></Url>");
            revert.append("</item>");
        }
        revert.append("</Articles>");
        revert.append("<FuncFlag>1</FuncFlag>");
        revert.append("</xml>");
        return revert.toString();
    }

    /**
     * 构建回复图片
     *
     * @param jsonObject
     * @return String
     */
    public static String creatRevertImage(JSONObject jsonObject) {
        StringBuffer revert = new StringBuffer();
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[").append(jsonObject.getString("ToUserName")).append("]]></ToUserName>");
        revert.append("<FromUserName><![CDATA[").append(jsonObject.getString("FromUserName")).append("]]></FromUserName>");
        revert.append("<CreateTime>").append(jsonObject.getString("CreateTime")).append("</CreateTime>");
        revert.append("<MsgType><![CDATA[image]]></MsgType>");
        revert.append("<Image><MediaId><![CDATA[").append(jsonObject.getString("MediaId")).append("]]></MediaId></Image>");
        revert.append("<FuncFlag>0</FuncFlag>");
        revert.append("</xml>");
        return revert.toString();
    }

    /**
     * 构建回复语音
     *
     * @param jsonObject
     * @return String
     */
    public static String creatRevertVoice(JSONObject jsonObject) {
        StringBuffer revert = new StringBuffer();
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[").append(jsonObject.getString("ToUserName")).append("]]></ToUserName>");
        revert.append("<FromUserName><![CDATA[").append(jsonObject.getString("FromUserName")).append("]]></FromUserName>");
        revert.append("<CreateTime>").append(jsonObject.getString("CreateTime")).append("</CreateTime>");
        revert.append("<MsgType><![CDATA[voice]]></MsgType>");
        revert.append("<Voice><MediaId><![CDATA[").append(jsonObject.getString("MediaId")).append("]]></MediaId></Voice>");
        revert.append("<FuncFlag>0</FuncFlag>");
        revert.append("</xml>");
        return revert.toString();
    }

    /**
     * 构建回复视频
     *
     * @param jsonObject
     * @return String
     */
    public static String creatRevertVideo(JSONObject jsonObject) {
        StringBuffer revert = new StringBuffer();
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[").append(jsonObject.getString("ToUserName")).append("]]></ToUserName>");
        revert.append("<FromUserName><![CDATA[").append(jsonObject.getString("FromUserName")).append("]]></FromUserName>");
        revert.append("<CreateTime>").append(jsonObject.getString("CreateTime")).append("</CreateTime>");
        revert.append("<MsgType><![CDATA[video]]></MsgType>");
        revert.append("<Video>");
        revert.append("<MediaId><![CDATA[").append(jsonObject.getString("MediaId")).append("]]></MediaId>");
        revert.append("<ThumbMediaId><![CDATA[").append(jsonObject.getString("ThumbMediaId")).append("]]></ThumbMediaId>");
        revert.append("</Video>");
        revert.append("<FuncFlag>0</FuncFlag>");
        revert.append("</xml>");
        return revert.toString();
    }

}
