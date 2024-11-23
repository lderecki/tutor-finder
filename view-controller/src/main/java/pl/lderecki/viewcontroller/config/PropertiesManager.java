package pl.lderecki.viewcontroller.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertiesManager {

    @Value("${db.host}")
    private String dbHost;

    @Value("${db.port}")
    private int dbPort;

    @Value("${db.name}")
    private String dbName;

    @Value("${db.user}")
    private String dbUser;

    @Value("${db.password}")
    private String dbPassword;
}
