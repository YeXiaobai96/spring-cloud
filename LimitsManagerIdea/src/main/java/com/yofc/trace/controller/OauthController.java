package com.yofc.trace.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yofc.trace.config.constant.AdminKey;
import com.yofc.trace.util.HttpClientUtil;
import com.yofc.trace.util.Md5TokenGenerator;
import com.yofc.trace.util.ResultUtil;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Filename: OauthController
 * @Author: wm
 * @Date: 2019/9/9 15:14
 * @Description:
 * @History:
 */
@Controller
@RequestMapping("/oauth")
public class OauthController {
    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String SIGN="uid=";
    private static final Integer SIGNNUM=4;
    @Value("${oauth2.server.url:http://config.iaas.biz}")
    private String gitlabServerUrl;
    @Value("${oauth2.server.url.authorize.path:/oauth/authorize}")
    private String authorizePath;
    @Value("${oauth2.server.url.token.path:/oauth/token}")
    private String tokenPath;

    @Value("${oauth2.client.id:f861fb0358f7945fea2ea2ee8aedfde309e5069e70664dfa795d513ed7e9f798}")
    private String clientId;
    @Value("${oauth2.client.secret:e752642d24cf1759f4937eeb5c04194131395d3b4b3f8f8d7f50c5ee5ac873a1}")
    private String clientSecret;
    @Value("${oauth2.client.callback.url:http://test.getway.biz:8282/tologin}")
    private String callbackUrl;

    @Autowired
    private StringRedisTemplate redisTemplate;

    OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());


    @PreDestroy
    public void cleanUp() {
        logger.info("close");
        oAuthClient.shutdown();
    }


    @ResponseBody
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public Object index() throws Throwable {

        logger.info("first login, build oauth request >..");
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(gitlabServerUrl + authorizePath)
                .setClientId(clientId)
                .setRedirectURI(callbackUrl)
                .setResponseType("code")
                .buildQueryMessage();

        String gitlabAuthUrl = request.getLocationUri();

        logger.info("redirect to : " + gitlabAuthUrl);
        return ResultUtil.success(gitlabAuthUrl);

    }


    @ResponseBody
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public Object callback(@RequestParam(value = "code", required = false) String code,
                           @RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "error_description", required = false) String errorDescription,
                           HttpServletResponse httpResponse) throws Throwable {

        if (StringUtils.hasLength(error)) {
            logger.error("authorization fails with error={} and error description={}", error, errorDescription);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ResultUtil.error(401, "no authority.");
        } else {
            if (org.apache.commons.lang3.StringUtils.isBlank(code)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return ResultUtil.error(401, "no authority.");
            }
            logger.info("callback request receives with code={}", code);

            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(gitlabServerUrl + tokenPath)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRedirectURI(callbackUrl)
                    //.setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setCode(code)
                    .buildQueryMessage();

            logger.info("build authorize request with code:{} and client secret", code);

            OAuthJSONAccessTokenResponse response = oAuthClient.accessToken(request);
            String accessToken = response.getAccessToken();
            logger.info("access token got: {}", accessToken);
            String userInfo = HttpClientUtil.getInstance().sendHttpGet("http://config.iaas.biz/api/v4/user?access_token=" + accessToken);
            JSONObject user = JSONObject.parseObject(userInfo);

            JSONObject nuser = new JSONObject();
            String id = user.getString("id");
            String name = user.getString("name");
            String externUid = null;
            try {
                externUid = JSONObject.parseObject(user.getJSONArray("identities").get(0).toString()).getString("extern_uid");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(StringUtils.isEmpty(externUid)){
                return ResultUtil.error(401,"用户信息违规");
            }
            Integer start = externUid.indexOf(SIGN) + SIGNNUM;
            Integer end=externUid.indexOf(",",start);
            String uid=externUid.substring(start,end);
            if(StringUtils.isEmpty(uid)){
                return ResultUtil.error(401,"用户信息违规");
            }
            nuser.put("id", id);
            nuser.put("uid", uid);
            nuser.put("name", name);
            nuser.put("menuJson", null);
            // 生成token
            String token = Md5TokenGenerator.generate(id, name);
            nuser.put("token", token);
            // 放入redis
            redisTemplate.opsForValue().set(AdminKey.USER_INFO.value() + token + ":", userInfo, 60 * 60 * 24, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(AdminKey.OAUTH_TOKEN.value() + token + ":", accessToken, 60 * 60 * 24, TimeUnit.SECONDS);
            return ResultUtil.success(nuser);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/listUser", method = RequestMethod.GET)
    public Object getUsers(HttpServletRequest request) {


        String token = getRequestToken(request);
        String accessToken = redisTemplate.opsForValue().get(AdminKey.OAUTH_TOKEN.value() + token + ":");
        String userInfo = HttpClientUtil.getInstance().sendHttpGet("http://config.iaas.biz/api/v4/users?access_token=" + accessToken);

        return ResultUtil.success(JSONArray.parseArray(userInfo));
    }

   /* @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logout(HttpServletRequest request) {
        String token = getRequestToken(request);
        redisTemplate.delete(AdminKey.OAUTH_TOKEN.value() + token + ":");
        redisTemplate.delete(AdminKey.USER_INFO.value() + token + ":");
        redisTemplate.delete(AdminKey.USER_MENU.value() + token + ":");
        return ResultUtil.success(null);
    }*/

    /**
     * 获取请求的token
     */
    protected String getRequestToken(HttpServletRequest httpRequest) {

        // 从header中获取token
        String token = httpRequest.getHeader(AdminKey.REQUEST_AUTH.value());

        // 如果header中不存在token，则从参数中获取token
        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(AdminKey.REQUEST_AUTH.value());
        }
        return token;
    }

    /*public static void main(String[] args) {
        String pw = "uid=uid10,ou=people,dc=test,dc=com";
        Integer ss1 = pw.indexOf(",");

        Integer ss3 = pw.indexOf("uid=") + 4;
        Integer ss2 = pw.indexOf(",", ss3);
        String ww=pw.substring(ss3,ss2);
        System.out.println(ss1);
        System.out.println(ss2);
        System.out.println(ss3);
        System.out.println(ww);

    }*/


}
