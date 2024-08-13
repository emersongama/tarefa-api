package com.cesmac.tarefa.api.configuration.jwt;

import com.cesmac.tarefa.api.configuration.exceptions.TokenException;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final JwtHelper jwtHelper;

    public TokenProvider(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    public boolean validarToken(String token) {
        try {
            this.jwtHelper.obterClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new TokenException("Assinatura do JWT inválida.");
        } catch (ExpiredJwtException e) {
            throw new TokenException("Token expirado");
        } catch (UnsupportedJwtException e) {
            throw new TokenException("Token não suportado.");
        } catch (IllegalArgumentException e) {
            throw new TokenException("Compactação do token inválida.");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.jwtHelper.obterClaims(token);
        Collection<? extends GrantedAuthority> authorities = obterPermissoesToken(claims);
        User principal = new User(claims.get("nome_usuario").toString(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private List<SimpleGrantedAuthority> obterPermissoesToken(Claims claims) {
        return Arrays.stream(claims.get("permissoes").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String criarToken(Authentication authentication) {
        return Jwts.builder()
                .setClaims(obterInformacoesToken(authentication))
                .signWith(this.jwtHelper.obterSecretKey(), SignatureAlgorithm.HS512)
                .setExpiration(obterTempoExpiracaoToken())
                .compact();
    }

    private Map<String, Object> obterInformacoesToken(Authentication authentication) {
        UsuarioSistema usuarioSistema = (UsuarioSistema) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id_usuario", usuarioSistema.getUsuario().getId());
        claims.put("nome_usuario", usuarioSistema.getUsuario().getNome());
        claims.put("login_usuario", usuarioSistema.getUsuario().getLogin());
        return claims;
    }

    private Date obterTempoExpiracaoToken() {
        long agora = (new Date()).getTime();
        long validadeTokenEmMilissegundos = 1000 * 86400;
        return new Date(agora + validadeTokenEmMilissegundos);
    }
}
