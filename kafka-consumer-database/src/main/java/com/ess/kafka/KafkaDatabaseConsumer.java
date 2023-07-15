package com.ess.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ess.kafka.entity.MediawikiData;
import com.ess.kafka.repository.MediawikiRepository;

@Service
public class KafkaDatabaseConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);
	
	private MediawikiRepository mediawikiRepository;
	
	public KafkaDatabaseConsumer(MediawikiRepository mediawikiRepository) {
		super();
		this.mediawikiRepository = mediawikiRepository;
	}



	@KafkaListener(topics = "mediawiki_recentchange", groupId = "myGroup")
	public void consume(String eventMessage) {
		
		LOGGER.info(String.format("Event Message received -> %s", eventMessage));
		
		MediawikiData mediawikiData = new MediawikiData();
		mediawikiData.setWikiEventData(eventMessage);
		mediawikiRepository.save(mediawikiData);
	}
	
}
