package webChat.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



//Spring Boot 및 Apache Tomcat을 사용하여 웹 애플리케이션에서 SSL(HTTPS) 트래픽을 활성화하고 HTTP를 HTTPS로 리다이렉트하는 설정을 정의
@Configuration
public class SslConfig {

    @Bean
    public ServletWebServerFactory servletContainer() {
        // Enable SSL Trafic
        // TomcatServletWebServerFactory는 Tomcat을 위한 ServletWebServerFactory 구현
        // TomcatServletWebServerFactory의 인스턴스를 생성하고, 이 인스턴스의 postProcessContext(Context context) 메서드를 재정의하여 SSL을 활성화
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                // SecurityConstraint 객체를 만들고, 사용자 제약을 "CONFIDENTIAL"로 설정(모든 데이터가 SSL을 통해 전송되도록 함)

                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
                //이 부분은 새로운 SecurityCollection 객체를 만들고, 모든 요청("/*")을 이 컬렉션에 추가
                //그리고 이 컬렉션을 securityConstraint에 추가하며, 그런 다음 context에 이 securityConstraint를 추가
            }
        };

        // Add HTTP to HTTPS redirect : http 로 요청이 들어오면 https 로 리다이렉트
        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
        //http를 https로 리다이렉트하는 추가 커넥터를 tomcat 인스턴스에 추가//이를 위해 httpToHttpsRedirectConnector() 메서드가 호출


        return tomcat;
    }

    /*
        http 를 https 로 리다이렉트한다.
        즉 http://8080 으로 요청이 들어온 경우 리다이렉트를 통해서 https://8443 으로 변경해준다
     */
    private Connector httpToHttpsRedirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
//이 메서드는 http 요청을 https 요청으로 리다이렉트하는 커넥터를 생성


}
