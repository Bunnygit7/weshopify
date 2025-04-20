package com.weshopify.platform.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.weshopify.platform.beans.UserBean;
import com.weshopify.platform.model.FullName;
import com.weshopify.platform.model.Wso2User;
import com.weshopify.platform.model.Wso2UserPhoneNumber;
import com.weshopify.platform.outbound.UserMgmtClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserMgmtServiceImpl implements UserMgmtService{

	@Autowired
	UserMgmtClient userMgmtClient;
	
	private UserBean mapWso2UserToUserBean(Wso2User wso2User) {
//		log.info(wso2User.getName().getFamilyName());
//		log.info(wso2User.getName().getGivenName());
//		log.info(wso2User.getUserName());
		UserBean userBean=UserBean
				.builder()
				.id(wso2User.getId())
				.firstName(wso2User.getName().getGivenName())
				.lastName(wso2User.getName().getFamilyName())
				.emails(wso2User.getEmails())
				.userId(wso2User.getUserName())
				.build();
		return userBean;
	}
	
	private Wso2User mapUserBeanToWso2User(UserBean userBean) {
		FullName fullName=FullName.builder().familyName(userBean.getLastName()).givenName(userBean.getFirstName()).build();
		Wso2UserPhoneNumber phoneNumber=Wso2UserPhoneNumber.builder().type("work").value(userBean.getMobile()).build();
		Wso2User wso2User=Wso2User
				.builder()
				.emails(userBean.getEmails())
				.password(userBean.getPassword())
				.schemas(new String[] {})
				.name(fullName)
				.phoneNumbers(Arrays.asList(phoneNumber))
				.userName(userBean.getUserId())
				.build();
		return wso2User;
	}

	@Override
	public List<UserBean> getAllUsers() {
		List<Wso2User> wso2UsersList=userMgmtClient.findAllUsers();
//		log.info(wso2UsersList.toString());
		if(!CollectionUtils.isEmpty(wso2UsersList)) {
			List<UserBean> usersList=new ArrayList<>();
			wso2UsersList.stream().forEach(wso2User->{
//				log.info(wso2User.getUserName());
				//Filtering the details to avoid the admin details while displaying by using admin id
				if(!wso2User.getId().equals("89ac8d24-9e4c-405d-a8d0-fef1e1610600")) {
					usersList.add(mapWso2UserToUserBean(wso2User));
				}
				
			});
			return usersList;
		}else {
			throw new RuntimeException("No Users Found");
		}
		
	}

	@Override
	public List<UserBean> createUser(UserBean userBean) {
		List<Wso2User> wso2Users=userMgmtClient.createUser(mapUserBeanToWso2User(userBean));
		if (!CollectionUtils.isEmpty(wso2Users)) {
			List<UserBean> usersBeanList=new ArrayList<>();
			wso2Users.parallelStream().forEach(wso2User->{
				usersBeanList.add(mapWso2UserToUserBean(wso2User));
			});
			
			return usersBeanList;
		}else {
			throw new RuntimeException( "No Users Found");
		}
	}

	
	@Override
	public UserBean getUser(String userId) {
		log.info(userId);
		UserBean user=userMgmtClient.findUser(userId);
		log.info(userId);

		return user;
	}

}
