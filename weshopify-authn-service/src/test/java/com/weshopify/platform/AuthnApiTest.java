/*package com.weshopify.platform;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.weshopify.platform.model.WSO2UserAuthnBean;
import com.weshopify.platform.outbound.IamAuthnCommunicator;
import com.weshopify.platform.utils.SSLUtil;

public class AuthnApiTest extends WeshopifyAuthnServiceApplicationTests{
	
	@Autowired
	IamAuthnCommunicator authnCommunicator;
	
	@Autowired
	private SSLUtil sslUtil;
	
	
	@Test
	public void testFindAllRoles() throws Exception {
		WSO2UserAuthnBean authnBean=WSO2UserAuthnBean.builder().username("kim1").password("MyPa33w@rd").build();
		sslUtil.disableSSLValidation();
		authnCommunicator.authenticate(authnBean);
	}

}
*/