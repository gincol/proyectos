package es.bcn.imi.framework.vigia.orquestrador.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import es.bcn.vigia.fmw.libcommons.business.dto.async.KafkaElement;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;

@Service(ServicesConstants.KAFKA_PRODUCER_INPUT)
public class KafkaProducerInput {

	private Log logger = LogFactory.getLog(this.getClass());
	private static final String TOPIC = "inventari-kafka";
	
	@Autowired
	private KafkaTemplate<String, KafkaElement> kafkaTemplate;
	
	public void sendMessage(KafkaElement kafkaElement) {
		logger.info(String.format("### -> Producing message -> %s", kafkaElement));
		this.kafkaTemplate.send(TOPIC, kafkaElement);
	}
	
	
}
