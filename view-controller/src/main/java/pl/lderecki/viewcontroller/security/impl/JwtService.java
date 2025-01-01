package pl.lderecki.viewcontroller.security.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.lderecki.viewcontroller.config.PropertiesManager;
import pl.lderecki.viewcontroller.exception.UnauthorizedException;
import pl.lderecki.viewcontroller.model.Role;
import pl.lderecki.viewcontroller.model.User;
import pl.lderecki.viewcontroller.security.TokenService;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService implements TokenService {

    private final PropertiesManager propertiesManager;

    public JwtService(PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    @Override
    public Mono<String> generateToken(String username, Collection<? extends GrantedAuthority> roles) {

            return Mono.just(Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setSubject(username)
                    .addClaims(Jwts.claims(Map.of("roles", roles.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * propertiesManager.getMinutesValid()))
                    .signWith(Keys.hmacShaKeyFor(propertiesManager.getSecretKey().getBytes()), SignatureAlgorithm.HS256)
                    .compact());
    }

    @Override
    public Mono<UserDetails> extractUserDetails(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(propertiesManager.getSecretKey().getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            if (username == null) {
                return Mono.error(new UnauthorizedException("Bad JWT token"));
            }

            List<String> roles = claims.get("roles", List.class);
            List<Role> authorities = roles.stream().map(r -> new Role(null, r)).toList();

            UserDetails userDetails = new User(null, null, null, null, username, null,
                    null, null, null, null, authorities);

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails,
                    token,
                    userDetails.getAuthorities());

            return Mono.just(userDetails);

        } catch (SignatureException e) {
            return Mono.error(new UnauthorizedException("Bad signature"));
        }
    }
}
