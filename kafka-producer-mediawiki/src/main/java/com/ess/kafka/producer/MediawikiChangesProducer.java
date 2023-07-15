package com.ess.kafka.producer;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;

@Service	
public class MediawikiChangesProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(MediawikiChangesProducer.class);
	
	private KafkaTemplate<String, String> kafkaTemplate;

	public MediawikiChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage() throws InterruptedException {
		
		String topic = "mediawiki_recentchange";
		
		//To read real time stream data from mediawiki, we use event source.
		EventHandler eventHandler = new MediawikiChangesHandler(kafkaTemplate, topic);
		String mediawikiEventUrl ="https://stream.wikimedia.org/v2/stream/recentchange";
		EventSource eventSource = new EventSource.Builder(eventHandler, URI.create(mediawikiEventUrl)).build();
		
		eventSource.start();
		TimeUnit.MINUTES.sleep(1);
	}
}
