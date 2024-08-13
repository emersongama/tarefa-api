package com.cesmac.tarefa.api.configuration.jwt;

import com.cesmac.tarefa.api.configuration.TarefaApiProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtHelper {

    private final TarefaApiProperties tarefaApiProperties;

    public JwtHelper(TarefaApiProperties tarefaApiProperties) {
        this.tarefaApiProperties = tarefaApiProperties;
    }

    public SecretKey obterSecretKey() {
        return Keys.hmacShaKeyFor(obterBytesSegredoBase64());
    }

    public Claims obterClaims(String token) {
        return Jwts.parser()
                .setSigningKey(obterSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] obterBytesSegredoBase64() {
        return Decoders.BASE64.decode(tarefaApiProperties.getSegredoBase64());
    }
}
