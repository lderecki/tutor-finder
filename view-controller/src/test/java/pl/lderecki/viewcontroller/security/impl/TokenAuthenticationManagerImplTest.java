package pl.lderecki.viewcontroller.security.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import pl.lderecki.viewcontroller.config.PropertiesManager;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenAuthenticationManagerImplTest {

    @InjectMocks
    private TokenAuthenticationManagerImpl testClass;

    @Mock
    private Authentication auth;

    @Mock
    private PropertiesManager propertiesManager;

    @Test
    void authenticate() {
        when(auth.getCredentials()).thenReturn("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZXMiOlsidGVzdCJdLCJpYXQiOjE3MzQyODUyMDksImV4cCI6MTczNDI4NTUwOX0.u_aoyUzlYY744la1mmAlSCjsjK5a3XwppD0WXAYTQhI");
        when(propertiesManager.getSecretKey()).thenReturn("m9sN9kRkJhEFlVCOp02sAh3G09IWY8krveQxnfyaHjY=");
        var result = testClass.authenticate(auth).block();

        System.out.println(result);
    }
}