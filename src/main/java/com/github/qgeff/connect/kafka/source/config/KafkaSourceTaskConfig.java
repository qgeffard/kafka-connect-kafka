/**
 * Copyright 2015 Confluent Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.github.qgeff.connect.kafka.source.config;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;

import java.util.Map;

/**
 * Configuration options for a single KafkaSourceTask. These are processed after all
 * Connector-level configs have been parsed.
 */
public class KafkaSourceTaskConfig extends AbstractConfig {

  public static final String BROKER_SOURCE = "broker.source";
  public static final String BROKER_SOURCE_DEFAULT = "localhost:9092";

  public static final String TOPIC_SOURCE = "topic.source";
  public static final String TOPIC_SOURCE_DEFAULT = "source";

  public static final String TOPIC_SINK = "topic.sink";
  public static final String TOPIC_SINK_DEFAULT = "sink";

  public static final String GROUP_ID = "consumer.group.id";
  public static final String GROUP_ID_DEFAULT = "default.group.id";

  public static final String AUTO_OFFSET_RESET = "auto.offset.reset";
  public static final String AUTO_OFFSET_RESET_DEFAULT = "earliest";

  public static final String MAX_PARTITION_FETCH_BYTES_CONFIG = "max.partition.fetch.bytes";
  public static final Integer MAX_PARTITION_FETCH_BYTES_CONFIG_DEFAULT = 1048576;


  static ConfigDef config = new ConfigDef()
          .define(BROKER_SOURCE, Type.STRING, BROKER_SOURCE_DEFAULT, Importance.HIGH,null)
          .define(TOPIC_SOURCE, Type.STRING, TOPIC_SOURCE_DEFAULT, Importance.HIGH,null)
          .define(TOPIC_SINK, Type.STRING, TOPIC_SINK_DEFAULT, Importance.HIGH,null)
          .define(GROUP_ID, Type.STRING, GROUP_ID_DEFAULT, Importance.HIGH,null)
          .define(AUTO_OFFSET_RESET, Type.STRING, AUTO_OFFSET_RESET_DEFAULT, Importance.HIGH,null)
          .define(MAX_PARTITION_FETCH_BYTES_CONFIG, Type.INT, MAX_PARTITION_FETCH_BYTES_CONFIG_DEFAULT, Importance.HIGH,null);

  public KafkaSourceTaskConfig(Map<String, String> props) {
    super(config, props);
  }

}
