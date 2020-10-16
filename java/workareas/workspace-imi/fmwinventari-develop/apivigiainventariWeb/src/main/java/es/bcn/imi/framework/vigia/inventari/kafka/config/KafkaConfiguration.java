package es.bcn.imi.framework.vigia.inventari.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import es.bcn.vigia.fmw.libcommons.business.dto.async.KafkaElement;
import es.bcn.vigia.fmw.libcommons.business.dto.async.KafkaElementResult;

@EnableKafka
@Configuration
public class KafkaConfiguration {

	@Value("${kafka.server}")
	private String server;
	
	@Value("${kafka.groups.input}")
	private String groupInput;
	
	@Value("${kafka.groups.output}")
	private String groupOutput;

	@Bean
	public ConsumerFactory<String, KafkaElement> kafkaElementConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupInput);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		return new DefaultKafkaConsumerFactory<String, KafkaElement>(config, new StringDeserializer(),
				new JsonDeserializer<>(KafkaElement.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, KafkaElement> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, KafkaElement> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(kafkaElementConsumerFactory());
		return factory;
	}

	@Bean
	public ProducerFactory<String, KafkaElementResult> producerFactoryResult() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<String, KafkaElementResult>(config);
	}

	@Bean
	public KafkaTemplate<String, KafkaElementResult> kafkaTemplateResult() {
		return new KafkaTemplate<String, KafkaElementResult>(producerFactoryResult());
	}

	@Bean
	public ConsumerFactory<String, KafkaElementResult> kafkaElementConsumerFactoryResult() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupOutput);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		return new DefaultKafkaConsumerFactory<String, KafkaElementResult>(config, new StringDeserializer(),
				new JsonDeserializer<>(KafkaElementResult.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, KafkaElementResult> kafkaListenerContainerFactoryResult() {
		ConcurrentKafkaListenerContainerFactory<String, KafkaElementResult> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(kafkaElementConsumerFactoryResult());
		return factory;
	}
}
