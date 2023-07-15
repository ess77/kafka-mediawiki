package com.ess.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ess.kafka.entity.MediawikiData;

public interface MediawikiRepository extends JpaRepository<MediawikiData, Long> {
	
	
}
