package es.bcn.imi.framework.vigia.inventari.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import es.bcn.vigia.fmw.libcommons.business.dto.async.KafkaElementResult;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;

@Service(ServicesConstants.KAFKA_PRODUCER_OUTPUT)
public class KafkaProducerOutput {

	private Log logger = LogFactory.getLog(this.getClass());
	private static final String TOPIC = "inventari-out-kafka";;
	
	@Autowired
	private KafkaTemplate<String, KafkaElementResult> kafkaTemplate;
	
	public void sendMessage(KafkaElementResult kafkaElementResult) {
		logger.info(String.format("### -> Producing message -> %s", kafkaElementResult));
		this.kafkaTemplate.send(TOPIC, kafkaElementResult);
	}
	
	
}
