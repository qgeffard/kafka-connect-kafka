package com.github.qgeff.connect.kafka.sink;

import com.github.qgeff.connect.kafka.sink.config.KafkaSinkTaskConfig;
import com.github.qgeff.connect.kafka.source.config.KafkaSourceTaskConfig;
import com.github.qgeff.connect.kafka.util.Version;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public class KafkaSinkTask<K,V> extends SinkTask {
    private KafkaProducer<K,V> producer;
    private KafkaSinkTaskConfig config;

    private Properties getKafkaProducerConfig(KafkaSinkTaskConfig config) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getString(KafkaSinkTaskConfig.BOOTSTRAP_SERVER));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, config.getString(KafkaSinkTaskConfig.GROUP_ID));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }

    @Override
    public String version() {
        return Version.getVersion();
    }

    @Override
    public void start(Map<String, String> map) {
        config = new KafkaSinkTaskConfig(map);
        producer = new KafkaProducer<>(getKafkaProducerConfig(config));
    }

    @Override
    public void put(Collection<SinkRecord> collection) {
        collection.stream().forEach(sinkRecord -> producer.send(new ProducerRecord<>(config.getString(KafkaSinkTaskConfig.TOPIC_SINK), (V) sinkRecord.value())));
    }

    @Override
    public void stop() {

    }
}
