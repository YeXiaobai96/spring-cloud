package com.yofc.trace.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yofc.trace.config.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtHelper {
	private static Logger logger = LoggerFactory.getLogger(JwtHelper.class);
	public final static String Security = "kelin";

	public static JwtToken parseToken(Claims claims) {
		JwtToken token = JwtToken.builder().uid(claims.getId()).name((String) claims.get("name"))
				.openId((String) claims.get("openId")).tokenType((String) claims.get("tokenType"))
				.expiration(claims.getExpiration()).accessToken((String) claims.get("accessToken"))
				.refreshToken((String) claims.get("refreshToken")).scope((String) claims.get("scope"))
				.expiration(claims.getExpiration()).build();
		return token;
	}

	public static JwtToken parseToken(String jsonWebToken, String base64Security) {
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
				.parseClaimsJws(jsonWebToken).getBody();
		JwtToken token = JwtToken.builder().uid(claims.getId()).name((String) claims.get("name"))
				.openId((String) claims.get("openId")).tokenType((String) claims.get("tokenType"))
				.expiration(claims.getExpiration()).accessToken((String) claims.get("accessToken"))
				.refreshToken((String) claims.get("refreshToken")).scope((String) claims.get("scope"))
				.expiration(claims.getExpiration()).build();
		return token;
	}

	public static Object parseJWT(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
					.parseClaimsJws(jsonWebToken).getBody();
			JwtToken token = parseToken(claims);
			System.out.println(token.toString());

			System.out.println("------解析token----");
			System.out.println("ID: " + claims.getId());
			System.out.println("scope: " + claims.get("scope"));
			System.out.println("IssuerAt:   " + claims.getIssuedAt());
			System.out.println("Expiration: " + claims.getExpiration());
			System.out.println("accessToken: " + claims.get("accessToken"));//
			System.out.println("refreshToken: " + claims.get("refreshToken"));
			System.out.println(claims.toString());

			/*
			 * 检验token是或否即将过期，如快要过期，就提前更新token。如果已经过期的，会抛出ExpiredJwtException
			 * 的异常
			 * 
			 */
			Long exp = claims.getExpiration().getTime(); // 过期的时间
			long nowMillis = System.currentTimeMillis();// 现在的时间
			Date now = new Date(nowMillis);
			logger.info("currenTime:" + now);
			long seconds = exp - nowMillis;// 剩余的时间
											// ，若剩余的时间小与48小时，就返回一个新的token给APP
			long days = seconds / (1000 * 60 * 60 * 24);
			long hour = (seconds - days * 1000 * 60 * 60 * 24) / 3600000;
			long minutes = (seconds - days * 1000 * 60 * 60 * 24 - hour * 3600000) / 60000;
			long remainingSeconds = seconds % 60;
			logger.info(seconds + " seconds is " + days + " days " + hour + " hours " + minutes + " minutes and "
					+ remainingSeconds + " seconds");
			if (seconds <= 1000 * 60 * 60 * 24 * 6) {
				logger.info("token的有效期小于 6天，请更新token！");
				return "update";
			}
			return "success";

		} catch (ExpiredJwtException e) {// token过期
			// e.printStackTrace();
			return ExpiredJwtException.class.getName();
		} catch (SignatureException e1) {// 密钥错误
			e1.printStackTrace();
			return SignatureException.class.getName();
		} catch (MalformedJwtException e2) {
			e2.printStackTrace();
			return MalformedJwtException.class.getName();
		} catch (Exception e3) {
			e3.printStackTrace();
			return e3.getClass().getName();
		}

	}

	public static String createJWT(JwtToken token) {
		// 签名算法 ，将对token进行签名
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		// 生成签发时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		long TTLMillis = token.getTTLMillis();
		// 生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(token.getSerect());
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setId(token.getUid())
				.claim("name", token.getName()).claim("openId", token.getOpenId())
				.claim("accessToken", token.getAccessToken()).claim("refreshToken", token.getRefreshToken())
				.claim("scope", token.getScope()).claim("tokenType", token.getTokenType())
				.signWith(signatureAlgorithm, signingKey);
		// 添加Token过期时间
		if (TTLMillis >= 0) {
			long expMillis = nowMillis + TTLMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp).setNotBefore(now);
		}

		// 生成JWT
		return builder.compact();
	}

	public static String createJWT(String name, String userId, String issuer, long TTLMillis, String base64Security) {
		// 签名算法 ，将对token进行签名
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		// 生成签发时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// 生成签名密钥
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT").setSubject(name).setId(userId)
				.setIssuer(issuer).signWith(signatureAlgorithm, signingKey);
		// 添加Token过期时间
		if (TTLMillis >= 0) {
			long expMillis = nowMillis + TTLMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp).setNotBefore(now);
		}

		// 生成JWT
		return builder.compact();
	}

//	public static void main(String[] args) {
//		/*
//		 * String msg = createJWT("zhangsan", "1", "kaifa", 10000 * 60 * 60 * 24
//		 * * 2 + 60000, Security); System.out.println(msg.length()); // String
//		 * msg1 = createJWT("zhangsan", "1", "kaifa", 1000 * 60 * 60 * 24 // * 2
//		 * + 60000, "miyao"); String msg1 = createJWT("zhangsan", "1", "kaifa",
//		 * 100, Security); System.out.println(msg1);
//		 * System.out.println(msg.equals(msg1)); String cc = parseJWT(msg,
//		 * Security).toString(); String cc1 = parseJWT(msg1,
//		 * Security).toString(); System.out.println(cc);
//		 * System.out.println(cc1);
//		 */
//		// eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJ1bmlxdWVfbmFtZSI6InpoYW5nc2FuIiwidXNlcmlkIjoiMSIsImlzcyI6IjEyMyIsImF1ZCI6ImthaWZhIiwiZXhwIjoxNTM2NzE5ODM5LCJuYmYiOjE1MzY1NDY5Nzl9.V9MsMpIHi7jZOsNxyV9CBkpgNfqoEFvDsmlEpPDB2pM
//		JwtToken token = JwtToken.builder().uid("1").name("zhangsan").serect(Security).openId(null).accessToken(null)
//				.refreshToken(null).TTLMillis(1000 * 60*10).scope(null).tokenType("mobile").build();
//		String token1 = createJWT(token);
//		System.out.println(token1);
//		JwtToken token2 = JwtToken.builder().uid("4").name("zhangsan").serect(Security).openId(null).accessToken(null)
//				.refreshToken(null).TTLMillis(1000 * 60 * 60 * 24*7).scope(null).tokenType("mobile").build();
//		String token3 = createJWT(token2);
//		System.out.println(token3);
//		String c = parseJWT(token1, Security).toString();
//		String ccc = parseJWT(token3, Security).toString();
//		System.out.println(c);
//		System.out.println(ccc);
//
//	}
}
