package com.chintec.ikks.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/7/2 11:03
 */
public class TokenUtils {
    private static final Logger log = LogManager.getLogger(TokenUtils.class);

    public static String token(String openId, String tokenSecret) {
        String token = "";
        try {
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            //设置头部信息
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带openId信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("openId", openId)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return token;
    }

    public static boolean verify(String token, String tokenSecret) {
        /**
         * @desc 验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

}
