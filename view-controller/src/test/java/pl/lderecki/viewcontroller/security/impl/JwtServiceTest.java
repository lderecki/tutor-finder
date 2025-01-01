package pl.lderecki.viewcontroller.security.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lderecki.viewcontroller.config.PropertiesManager;
import pl.lderecki.viewcontroller.model.Role;

import java.util.List;

import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService testClass;

    @Mock
    private PropertiesManager propertiesManager;

    @Test
    void generateToken() {
        when(propertiesManager.getSecretKey()).thenReturn("m9sN9kRkJhEFlVCOp02sAh3G09IWY8krveQxnfyaHjY=");
        when(propertiesManager.getMinutesValid()).thenReturn(5L);
        var token = testClass.generateToken("test", List.of(new Role(1,"test")));

        System.out.println(token);
    }
}