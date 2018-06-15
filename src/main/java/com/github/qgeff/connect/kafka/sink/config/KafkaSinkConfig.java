package com.github.qgeff.connect.kafka.sink.config;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

public class KafkaSinkConfig extends AbstractConfig {

  public static final ConfigDef CONFIG_DEF = new ConfigDef();

  public KafkaSinkConfig(ConfigDef definition, Map<?, ?> originals, boolean doLog) {
    super(definition, originals, doLog);
  }
}
