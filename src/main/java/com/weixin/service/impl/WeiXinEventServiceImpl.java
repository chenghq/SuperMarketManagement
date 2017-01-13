package com.weixin.service.impl;

import com.common.constant.WeiXinCst;
import com.common.util.DateUtil;
import com.common.util.XmlRevertUtil;
import com.weixin.service.WeiXinEventService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/13.
 */
@Service
public class WeiXinEventServiceImpl implements WeiXinEventService {
    @Override
    public String attention(JSONObject jsonObject) {
        String message = WeiXinCst.WELCOME_STR;
        String event = jsonObject.getString("Event");
        // TODO 对string以及json字符串的判断
        JSONObject revertJSONObject =new JSONObject();
        revertJSONObject.put("ToUserName", jsonObject.get("FromUserName"));
        revertJSONObject.put("FromUserName", jsonObject.get("ToUserName"));
        revertJSONObject.put("CreateTime", DateUtil.format(new Date(), "yyyyMMdd"));
        StringBuffer buffer =new StringBuffer();
        buffer.append(message).append("\n");
        revertJSONObject.put("Content", buffer.toString());
        return XmlRevertUtil.creatRevertText(revertJSONObject);
    }

    @Override
    public String amityClew(JSONObject jsonObject) {
        // TODO
        return null;
    }
}
