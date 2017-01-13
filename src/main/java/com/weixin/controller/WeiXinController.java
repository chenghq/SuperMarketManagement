package com.weixin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.constant.WeiXinCst;
import com.common.util.AccessControl;
import com.weixin.dao.bean.HllUser;
import com.weixin.service.HllUserService;
import com.weixin.service.WeiXinEventService;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by cheng on 2017/1/13.
 */
@Controller
public class WeiXinController {
    private static final Logger logger = LoggerFactory
            .getLogger(WeiXinController.class);

    @Resource
    WeiXinEventService weiXinEventService;
    @Resource
    HllUserService hllUserService;

    /**
     * ΢�Ž���ϵͳ
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/weixin", method = RequestMethod.GET)
    public void receive(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        connect(request, response);
    }

    @RequestMapping(value = "/weixin", method = RequestMethod.POST)
    public void wxPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            message(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����΢�ŷ�����
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void connect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("- weixin connect to the system...");
        AccessControl accessControl = new AccessControl();
        if (!accessControl.accessing(request, response)) {
            logger.error("- weixin connect to the system error...");
        }
        String echostr = accessControl.getEchostr();
        if (echostr != null && !"".equals(echostr)) {
            logger.info("- weixin connect to the system success...");
            response.getWriter().print(echostr);//����໥��֤
        }
    }

    /**
     <p>XML��װ���</p>
     * @return
     * @throws ServletException, IOException
     * @author wangsj
     */
    private void message(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream is = request.getInputStream();
        // ȡHTTP����������
        int size = request.getContentLength();
        // ���ڻ���ÿ�ζ�ȡ������
        byte[] buffer = new byte[size];
        // ���ڴ�Ž��������
        byte[] xmldataByte = new byte[size];
        int count = 0;
        int rbyte = 0;
        // ѭ����ȡ
        while (count < size) {
            // ÿ��ʵ�ʶ�ȡ���ȴ���rbyte��
            rbyte = is.read(buffer);
            for (int i = 0; i < rbyte; i++) {
                xmldataByte[count + i] = buffer[i];
            }
            count += rbyte;
        }
        is.close();
        String requestStr = new String(xmldataByte, "UTF-8");

        XMLSerializer xmlSerializer = new XMLSerializer();
        JSONObject jsonObject = (JSONObject) xmlSerializer.read(requestStr);
        jsonObject.put(WeiXinCst.JXLIFE_URL, get3WURL(request));
        logger.debug("����΢���û���" + jsonObject.getString("FromUserName") + "�����������ݸ�ʽ-" + requestStr);
        try {
            manageMessage(requestStr, jsonObject, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String get3WURL(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + path;
        return basePath;
    }

    /**<p>ҵ��ת�����</p>
     * @return
     * @throws ServletException, IOException
     * @throws SQLException
     * @author wangsj
     */
    private void manageMessage(String requestXML, JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String MsgType = StringUtils.trim(jsonObject.getString("MsgType"));
        String FromUserName = jsonObject.getString("FromUserName");
        if ("text".equals(MsgType)) {
            System.out.println("enter  text ........");
            manageTextMessage(requestXML, jsonObject, request, response);
            System.out.println("end text........");
        } else if ("event".equals(MsgType)) {
            System.out.println("enter Event  ....");
            manageEventMessage(requestXML, jsonObject, request, response);
            System.out.println("end Event....");
        }
    }

    synchronized private void manageTextMessage(String requestXML, JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        System.out.println("manageTextMessage....");
    }

    private void manageEventMessage(String requestXML, JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String FromUserName = jsonObject.getString("FromUserName");
        logger.debug("- FromUserName: {}", FromUserName);
        String event = jsonObject.getString("Event");
        if ("unsubscribe".equals(event)) {

        }
        if ("subscribe".equals(event)) {
            System.out.println("subscribe...");
            String revertXML = weiXinEventService.attention(jsonObject);
            OutputStream os = response.getOutputStream();
            os.write(revertXML.getBytes("UTF-8"));
        }
        if ("CLICK".equals(event)) {
            String responseStr;
            String bike = String.valueOf(Character.toChars(0x1F61A));
            jsonObject.put("Content", "�����ڴ���" + bike);
            try {
                responseStr = createRevertText(jsonObject);//����XML
                System.out.println("responseStr:" + responseStr);
                OutputStream os = response.getOutputStream();
                os.write(responseStr.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String eventKey = jsonObject.getString("EventKey");
            if ("404".equals(eventKey)) {  //���չ�
                ModelAndView mav = new ModelAndView();

                // ����΢��id��ȡ�󶨵��û���Ϣ
                HllUser user = hllUserService.getByWeiXinId(FromUserName);
                if (null == user) {
                    // δ��ѯ���û���δ�󶨣���ת��login���� TODO
                    logger.debug("- δ��ѯ���û���δ�󶨣���ת��login����");
                    mav.setViewName("login");
                } else {
                    logger.debug("- �û��Ѿ��󶨣���ֱ����ת����Ʒչʾ����");
                    mav.setViewName("goods");
                }
            }
        }
    }

    private String createRevertText(JSONObject jsonObject) {
        StringBuffer revert = new StringBuffer();
        revert.append("<xml>");
        revert.append("<ToUserName><![CDATA[" + jsonObject.get("FromUserName") + "]]></ToUserName>");//ToUserName,
        revert.append("<FromUserName><![CDATA[" + jsonObject.get("ToUserName") + "]]></FromUserName>");//FromUserName
        revert.append("<CreateTime>" + jsonObject.get("CreateTime") + "</CreateTime>");
        revert.append("<MsgType><![CDATA[text]]></MsgType>");
        revert.append("<Content><![CDATA[" + jsonObject.get("Content") + "]]></Content>");
        revert.append("<FuncFlag>0</FuncFlag>");
        revert.append("</xml>");
        return revert.toString();
    }
}
