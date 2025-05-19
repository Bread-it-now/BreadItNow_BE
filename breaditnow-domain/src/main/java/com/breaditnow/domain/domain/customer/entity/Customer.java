package com.breaditnow.domain.domain.customer.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.breaditnow.domain.domain.alert.entity.CustomerAlertSetting;
import com.breaditnow.domain.domain.breadcategory.entity.BreadCategory;
import com.breaditnow.domain.domain.customer.enumerate.Provider;
import com.breaditnow.domain.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
@Table(name = "P_Customer")
public class Customer extends BaseEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Provider provider;

	private String oauth2Id;

	private String fcmToken;

	@Column(unique = true)
	private String email;

	private String password;

	@Column(unique = true)
	private String nickname;

	private String phone;

	private String profileImage;

	private boolean initialSetup;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private CustomerAlertSetting alertSetting;

	private LocalDateTime lastLoginAt;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomerBreadCategoryPreference> breadCategoryPreferences = new ArrayList<>();

	@Builder
	public Customer(Provider provider, String oauth2Id, String fcmToken, String email, String password, String nickname,
		String phone, String profileImage, CustomerAlertSetting alertSetting, LocalDateTime lastLoginAt) {
		this.provider = provider;
		this.oauth2Id = oauth2Id;
		this.fcmToken = fcmToken;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.phone = phone;
		this.profileImage = profileImage;
		this.alertSetting = alertSetting;
		this.lastLoginAt = lastLoginAt;
	}

	public void updateLastLoginAt() {
		this.lastLoginAt = LocalDateTime.now();
	}

	public boolean isFirstLogin() {
		return this.nickname == null;
	}

	public void changeFcmToken(String token) {
		this.fcmToken = token;
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateBreadCategoryPreferences(List<BreadCategory> breadCategories) {
		this.breadCategoryPreferences.clear();
		breadCategories.forEach(category ->
			this.breadCategoryPreferences.add(new CustomerBreadCategoryPreference(this, category))
		);
	}

	public void updatePhone(String phone) {
		this.phone = phone;
	}

	public void updateProfileImage(String profileImageUrl) {
		this.profileImage = profileImageUrl;
	}

	public void changePassword(String encodedPassword) {
		this.password = encodedPassword;
	}
}
