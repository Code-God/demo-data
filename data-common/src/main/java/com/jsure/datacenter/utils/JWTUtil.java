package com.jsure.datacenter.utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jsure.datacenter.constant.CustomConstant;
import com.jsure.datacenter.model.enums.CustomErrorEnum;
import com.jsure.datacenter.model.token.CheckResult;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt加密和解密的工具类
 */
public class JWTUtil {

	/**
	 * 创建jwt
	 * @param id
	 * @param subject
	 * @param ttlMillis 过期的时间长度
	 * @return
	 */
	public static String createJWT(String id, Integer roleId, String subject, long ttlMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
		long nowMillis = System.currentTimeMillis();//生成JWT的时间
		Date now = new Date(nowMillis);
		Map<String,Object> claims = new HashMap<>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
		claims.put("username", subject);
		claims.put("roleId", roleId);
		SecretKey secretKey = generalKey();//生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。

		//下面就是在为payload添加各种标准声明和私有声明了
		JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
				.setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
				.setId(id)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setIssuedAt(now)           //iat: jwt的签发时间
				.setSubject(subject)        //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
				.signWith(signatureAlgorithm, secretKey);//设置签名使用的签名算法和签名使用的秘钥
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);     //设置过期时间
		}
		return builder.compact();           //压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
	}

	/**
	 * 解密jwt
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception{
		SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
		Claims claims = Jwts.parser()  //得到DefaultJwtParser
				.setSigningKey(key)         //设置签名的秘钥
				.parseClaimsJws(jwt).getBody();//设置需要解析的jwt
		return claims;
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 验证JWT
	 * @param jwtStr
	 * @return
	 */
	public static CheckResult validateJWT(String jwtStr) {
		CheckResult checkResult = new CheckResult();
		try {
			Claims claims = parseJWT(jwtStr);
			checkResult.setSuccess(true);
			checkResult.setClaims(claims);
		} catch (ExpiredJwtException e) {
			checkResult.setErrCode(CustomErrorEnum.JWT_ERRCODE_EXPIRE.getErrorCode());
			checkResult.setErrMsg(CustomErrorEnum.JWT_ERRCODE_EXPIRE.getErrorDesc());
			checkResult.setSuccess(false);
		} catch (SignatureException e) {
			checkResult.setErrCode(CustomErrorEnum.JWT_ERRCODE_FAIL.getErrorCode());
			checkResult.setErrMsg(CustomErrorEnum.JWT_ERRCODE_FAIL.getErrorDesc());
			checkResult.setSuccess(false);
		} catch (Exception e) {
			checkResult.setErrCode(CustomErrorEnum.JWT_ERRCODE_INVALID.getErrorCode());
			checkResult.setErrMsg(CustomErrorEnum.JWT_ERRCODE_INVALID.getErrorDesc());
			checkResult.setSuccess(false);
		}
		return checkResult;
	}


	/**
	 * 生成secert密钥
	 * @return
	 */
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(CustomConstant.JWT_SECERT);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}


}
