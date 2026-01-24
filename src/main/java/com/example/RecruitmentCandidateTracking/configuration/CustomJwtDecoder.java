package com.example.RecruitmentCandidateTracking.configuration;

import com.example.RecruitmentCandidateTracking.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    private final TokenValidator tokenValidator;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) {
        try {
            // Xác minh token hợp lệ, chưa bị thu hồi, chưa hết hạn
            tokenValidator.verifyToken(token, false);

            if (Objects.isNull(nimbusJwtDecoder)) {
                SecretKeySpec secretKey = new SecretKeySpec(JWT_SECRET.getBytes(), "HmacSHA512");
                nimbusJwtDecoder = NimbusJwtDecoder
                        .withSecretKey(secretKey)
                        .macAlgorithm(MacAlgorithm.HS512)
                        .build();
            }
            return nimbusJwtDecoder.decode(token);

        } catch (AppException e) {
            throw new BadCredentialsException("Invalid or revoked JWT: " + e.getErrorCode().getMessage());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid JWT " + e.getMessage());
        }


    }


}

