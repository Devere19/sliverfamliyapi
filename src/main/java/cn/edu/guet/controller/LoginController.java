package cn.edu.guet.controller;


import cn.edu.guet.bean.LoginBean;
import cn.edu.guet.bean.SysUser;
import cn.edu.guet.bean.User;
import cn.edu.guet.http.HttpResult;
import cn.edu.guet.http.ResultUtils;
import cn.edu.guet.security.JwtAuthenticationToken;
import cn.edu.guet.service.SysUserService;
import cn.edu.guet.service.UserService;
import cn.edu.guet.util.PasswordUtils;
import cn.edu.guet.util.SecurityUtils;
import cn.edu.guet.util.WechatUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
//    public HttpResult login(String username, String password, HttpServletRequest request) {
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        System.out.println(username);
        System.out.println(password);
        // 用户信息
        SysUser user = sysUserService.findByName(username);
        // 账号不存在、密码错误
        if (user == null) {
            return ResultUtils.error("账号不存在");
        }
        if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
            return ResultUtils.error("密码不正确");
        }
        // 账号锁定
        if (user.getStatus() == 0) {
            return ResultUtils.error("账号已被锁定,请联系管理员");
        }
        // 系统登录认证
        JwtAuthenticationToken token = SecurityUtils.login(request, username, password, authenticationManager);

        return ResultUtils.success("获取token", token);
    }

    /**
     * 微信小程序的
     *
     * @param code
     * @param rawData
     * @param signature
     * @return
     */

    @PostMapping("/wx/login")
    public HttpResult user_login(@RequestParam(value = "code", required = false) String code,
                                 @RequestParam(value = "rawData", required = false) String rawData,
                                 @RequestParam(value = "signature", required = false) String signature) {
        System.out.println(code);
        System.out.println(rawData);
        System.out.println(signature);
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        // String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        // if (!signature.equals(signature2)) {
        //     return ResultUtils.error("签名校验失败");
        // }

        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；
        LambdaQueryWrapper<User> lqw = Wrappers.lambdaQuery();
        lqw.eq(User::getOpenId, openid);
        User user = userService.getOne(lqw);

        if (user == null) {
            // 用户信息入库
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            user = new User();
            user.setOpenId(openid);
            user.setAvatar(avatarUrl);
            user.setNickName(nickName);
            userService.save(user);
        }
        user.setSessionKey(sessionKey);
        return ResultUtils.success("返回openId", user);
    }


    /**
     * 获取用户手机号
     *
     * @param encryptedData
     * @param iv
     * @param
     * @return
     */
    @PostMapping("/wx/getphone")
    public String getUserPhone(@RequestParam(value = "encryptedData", required = false) String encryptedData,
                               @RequestParam(value = "iv", required = false) String iv,
                               @RequestParam(value = "sessionKey", required = false) String sessionKey,
                               @RequestParam(value = "openId", required = false) String openId) {
        System.out.println(encryptedData);
        System.out.println(iv);
        System.out.println(sessionKey);
        String result = "";
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = new String(resultByte, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map maps = (Map) JSON.parse(result);
        //把手机号存到数据库
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getOpenId,openId);
        User one = userService.getOne(queryWrapper);
        one.setPhone((String) maps.get("phoneNumber"));
        userService.updateById(one);
        return result;
    }


}
