package org.tbulens.sso.common.jwt

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import org.tbulens.sso.common.util.SsoCookieCreator
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

class JwtUtil {

    String generateToken(String signingKey, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, signingKey);

        return builder.compact();
    }

    String getSubject(HttpServletRequest request, String signingKey){
        Cookie cookie = WebUtils.getCookie(request, SsoCookieCreator.SSO_SESSION_ID)
        String jwtToken = cookie?.value;
        if (!jwtToken) return null;
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwtToken).getBody().getSubject();
    }
}
