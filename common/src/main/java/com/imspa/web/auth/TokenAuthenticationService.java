package com.imspa.web.auth;

import com.imspa.web.util.WebConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-14 23:24
 */
public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 30 * 60 * 1000; //TODO

    public static String getAuthenticationToken(Map<String, Object> claims) {
        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, WebConstant.WEB_SECRET)
                .compact();
    }
}
