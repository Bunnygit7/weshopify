/*package com.weshopify.platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.weshopify.platform.outbound.RoleMgmtClient;
import com.weshopify.platform.utils.SSLUtil;

public class IamRoleApiTest extends WeshopifyUserManagementServiceApplicationTests{
	
	@Autowired
	private RoleMgmtClient roleClient;
	
	@Autowired
	private SSLUtil sslUtil;
	
	@Test
	public void testFindAllRoles() throws Exception {
		sslUtil.disableSSLValidation();
		roleClient.findAllRoles();
	}
}
*/