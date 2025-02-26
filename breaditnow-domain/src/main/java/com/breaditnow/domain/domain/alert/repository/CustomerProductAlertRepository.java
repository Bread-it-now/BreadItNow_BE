package com.breaditnow.domain.domain.alert.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;

public interface CustomerProductAlertRepository extends JpaRepository<CustomerProductAlert, Long> {

}
