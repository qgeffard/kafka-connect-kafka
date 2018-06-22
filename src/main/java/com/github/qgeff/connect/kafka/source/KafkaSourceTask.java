/**
 * Copyright 2015 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 **/

package com.github.qgeff.connect.kafka.source;

import com.github.qgeff.connect.kafka.source.config.KafkaSourceTaskConfig;
import com.github.qgeff.connect.kafka.util.Version;
import java.io.Serializable;
import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.utils.SystemTime;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * KafkaSourceTask is a Kafka Connect SourceTask implementation that reads from Kafka topics and
 * generates Kafka Connect records.
 */
public class KafkaSourceTask<K extends Serializable, V extends Serializable> extends SourceTask {

  private static final Logger log = LoggerFactory.getLogger(KafkaSourceTask.class);
  private KafkaConsumer<K, V> consumer;
  private Time time;
  private KafkaSourceTaskConfig config;
  
/*  private static final String STRUCT_METADATA_FIELD = "metadata";
  private static final String ENTRY_DATA_UUID = "entrydatauuid";
  private static final String FIELD_RECORD_INGEST_TIME = "recordIngestTime";
  private static final String STRUCT_PAYLOAD_FIELD = "payload";
  private static final String DEFAULT_RECORD_SCHEMA_NAME = "SourceWrapper";
  
  private Schema metadataSchema = SchemaBuilder.struct()
      .name(STRUCT_METADATA_FIELD)
      .field(ENTRY_DATA_UUID, STRING_SCHEMA)
      .field(FIELD_RECORD_INGEST_TIME, STRING_SCHEMA)
      .build();

  private Schema sourceWrapperSchema = SchemaBuilder.struct()
      .name(DEFAULT_RECORD_SCHEMA_NAME)
      .field(STRUCT_METADATA_FIELD, metadataSchema)
      .field(STRUCT_PAYLOAD_FIELD, STRING_SCHEMA)
      .build();
*/

  public KafkaSourceTask() {
    this.time = new SystemTime();
  }

  public KafkaSourceTask(Time time) {
    this.time = time;
  }

  @Override
  public void start(Map<String, String> properties) {
    log.info("Starting KafkaSourceTask...");
    try {
      config = new KafkaSourceTaskConfig(properties);
    } catch (ConfigException e) {
      throw new ConnectException("Couldn't start KafkaSourceTask due to configuration error", e);
    }
    consumer = new KafkaConsumer<>(getKafkaConsumerConfig(config));
    consumer.subscribe(Collections.singletonList(config.getString(KafkaSourceTaskConfig.TOPIC_SOURCE)));
  }

  private Properties getKafkaConsumerConfig(KafkaSourceTaskConfig config) {
    Properties configs = new Properties();
    configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getString(KafkaSourceTaskConfig.BROKER_SOURCE));
    configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.getString(KafkaSourceTaskConfig.AUTO_OFFSET_RESET));
    configs.put(ConsumerConfig.GROUP_ID_CONFIG,config.getString(KafkaSourceTaskConfig.GROUP_ID));
    configs.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, config.getInt(KafkaSourceTaskConfig.MAX_PARTITION_FETCH_BYTES_CONFIG));
    configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringDeserializer");
    configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringDeserializer");
    return configs;
  }

  @Override
  public void stop() throws ConnectException {
  }

  @Override
  public List<SourceRecord> poll() throws InterruptedException {
    log.trace("{} Polling for new data");

    ConsumerRecords<K, V> poll = consumer.poll(1000);
    ArrayList<SourceRecord> sourceRecords = new ArrayList<>(poll.count());

    poll.iterator().forEachRemaining(record -> {

/*      Struct metadataStruct = new Struct(metadataSchema)
          .put(ENTRY_DATA_UUID, UUID.randomUUID().toString())
          .put(FIELD_RECORD_INGEST_TIME, String.valueOf(System.currentTimeMillis()));

      Struct sourceWrapperStruct = new Struct(sourceWrapperSchema)
          .put(STRUCT_METADATA_FIELD, metadataStruct)
          .put(STRUCT_PAYLOAD_FIELD, record.value());*/

      sourceRecords.add(
          new SourceRecord(Collections.singletonMap("sourceTopic", record.topic()), Collections.singletonMap("sourceOffest",record.offset()), this.config.getString(KafkaSourceTaskConfig.TOPIC_SINK), null,
              record.value()));
    });

    return sourceRecords;
  }

  @Override
  public String version() {
    return Version.getVersion();
  }
}
