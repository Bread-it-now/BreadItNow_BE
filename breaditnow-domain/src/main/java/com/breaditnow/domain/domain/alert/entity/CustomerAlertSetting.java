	package com.breaditnow.domain.domain.alert.entity;

	import static jakarta.persistence.GenerationType.*;
	import static lombok.AccessLevel.*;

	import com.breaditnow.domain.domain.customer.entity.Customer;
	import com.breaditnow.domain.global.entity.BaseEntity;

	import jakarta.persistence.*;
	import lombok.Builder;
	import lombok.Getter;
	import lombok.NoArgsConstructor;
	import lombok.Setter;

	import java.util.List;

	@Entity
	@Getter
	@NoArgsConstructor(access = PROTECTED)
	public class CustomerAlertSetting extends BaseEntity {
		@Id
		@GeneratedValue(strategy = IDENTITY)
		private Long id;

		@OneToOne
		@JoinColumn(name = "customer_id", nullable = false)
		private Customer customer;

		@Setter
		private boolean isActive = false;

		@Setter
		private String doNotDisturbStartTime;

		@Setter
		private String doNotDisturbEndTime;

		@Setter
		@ElementCollection(fetch = FetchType.EAGER)
		@CollectionTable(
				name = "alert_dnd_days",
				joinColumns = @JoinColumn(name = "alert_setting_id")
		)
		@Column(name = "day")
		private List<String> doNotDisturbDays;

		@Builder
		public CustomerAlertSetting(Customer customer, boolean isActive, String doNotDisturbStartTime, String doNotDisturbEndTime, List<String> doNotDisturbDays) {
			this.customer = customer;
			this.isActive = isActive;
			this.doNotDisturbStartTime = doNotDisturbStartTime;
			this.doNotDisturbEndTime = doNotDisturbEndTime;
			this.doNotDisturbDays = doNotDisturbDays;
		}
	}
