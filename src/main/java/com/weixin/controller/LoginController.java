package com.weixin.controller;

/**
 * Created by 13072937 on 2017/1/3.
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.service.UserIphoneService;

/**
 * 登录系统,以及相关操作
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory
            .getLogger(LoginController.class);

    @Resource
    UserIphoneService userIphoneService;

    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        // 获取电话信息以及验证码信息
        String iphone_number = request.getParameter("iphone_number");
        String mask = request.getParameter("mask_number");
        if (StringUtils.isBlank(iphone_number) || StringUtils.isBlank(mask)) {
            model.addAttribute("message", "the parameter must exist.");
            logger.error("The iphone_number and mask_number must exist.");
            return "login";
        }
        // 根据电话号码来验证验证码

        logger.info("login success, so return goods.jsp");
        // 将产品列表返回到前台界面
/*		Map<String, String> productsMap1 = new HashMap<String, String>();
        productsMap1.put("fruits", "水果");
		productsMap1.put("vegetables", "蔬菜");
		
		Map<String, Map<String, String>> menugroups = new HashMap<String, Map<String,String>>();
		menugroups.put("menugroup1", productsMap1);
		menugroups.put("menugroup2", productsMap1);*/

        model.addAttribute("selected_group", "menugroup1");
        model.addAttribute("selected_product", "fruits");
        return "goods";
    }

    /**
     * 发送验证码
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws DocumentException
     */
    @RequestMapping(value = "/getmask.do", method = RequestMethod.GET)
    @ResponseBody
    public String getmask(HttpServletRequest request)
            throws UnsupportedEncodingException, DocumentException {
        String iphone_number = request.getParameter("iphone_number");
        if (StringUtils.isBlank(iphone_number)) {
            // 输入的电话为空
            logger.info("- The iphone number is null, so get mask error.");
            return "电话号码不能为空";
        }

        logger.info("- Get mask the iphone number, {}", iphone_number);

        // String httpUrl =
        // "http://sms.kingtto.com:9999/sms.aspx";//"http://apis.baidu.com/kingtto_media/106sms/106sms";
        int n1 = (int) (Math.random() * 9000 + 1000);
        String n2 = String.valueOf(n1);
        String text = "您的验证码:mask,请于2分钟内正确输入【佰仕捷好邻里】";
        text = text.replace("mask", n2);
        String httpArg = "action=send&userid=4045&account=wsjzsj&password=wsjzsj123321a&mobile=iphone_number&content=text";
        httpArg = httpArg.replace("iphone_number", iphone_number);
        httpArg = httpArg
                .replace("text", URLEncoder.encode(text + "", "UTF-8"));
        System.out.println(httpArg);
        // String jsonResult = request(httpUrl, httpArg);
        String jsonResult = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><returnsms><returnstatus>Success</returnstatus><message>ok</message></returnsms>";
        // System.out.println(jsonResult);
        SAXReader saxreader = new SAXReader();
        // Document doc = (Document) saxreader.read(jsonResult);
        Document doc = saxreader.read(new ByteArrayInputStream(jsonResult
                .getBytes("UTF-8")));
        Element root = doc.getRootElement();
        String returnValue = null;
        for (@SuppressWarnings("rawtypes") Iterator iter = root.elementIterator(); iter.hasNext(); ) {
            Element element = (Element) iter.next();
            System.out.println("1111111== " + element.getName());
            System.out.println("2222222== " + element.getStringValue());
            if (element.getName() == "returnstatus") {
                returnValue = element.getStringValue();
                break;
            }
        }
        if ("Success".equals(returnValue)) {
            userIphoneService.save(iphone_number, n1);
        }
        return "true";
    }

    /**
     * 发送验证码
     * @param httpUrl
     * @param httpArg
     * @return
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");//("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "0f5776397f76286167597acf32c7f35d");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
