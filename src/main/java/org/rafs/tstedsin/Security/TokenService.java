package org.rafs.tstedsin.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.rafs.tstedsin.Errors.TokenGenerationException;
import org.rafs.tstedsin.Errors.TokenIsInvalidException;
import org.rafs.tstedsin.Model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
public class TokenService {


    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Value("${security.jwt.expirationInMillisecond}")
    private long expirationInMillisecond;

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer("Teste-DSIN")
                    .withSubject(user.getUsername())
                    .withExpiresAt(Instant.now().plusSeconds(expirationInMillisecond))
                    .sign(algorithm);

        }catch (JWTCreationException e){
            throw new TokenGenerationException();
        }

    }

    public String validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.require(algorithm)
                    .withIssuer("Teste-DSIN")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){
            throw new TokenIsInvalidException();
        }


    }

}
