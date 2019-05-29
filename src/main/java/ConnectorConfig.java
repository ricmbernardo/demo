import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConnectorConfig {

//	@Bean
//	public TomcatServletWebServerFactory containerFactory() {
//		
//		return new TomcatServletWebServerFactory() {
//			
//			@Override
//			protected void postProcessContext(Context context) {
//				
//				SecurityCollection securityCollection = new SecurityCollection();
//				securityCollection.addPattern("/*");
//
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				securityConstraint.addCollection(securityCollection);
//				
//				context.addConstraint(securityConstraint);
//			}
//			
//		}
//		
//	}
	
}
