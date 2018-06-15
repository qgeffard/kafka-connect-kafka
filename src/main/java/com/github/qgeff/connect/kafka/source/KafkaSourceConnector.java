package com.github.qgeff.connect.kafka.source;

import com.github.qgeff.connect.kafka.source.config.KafkaSourceConnectorConfig;
import com.github.qgeff.connect.kafka.util.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaSourceConnector extends SourceConnector {
  private static final Logger log = LoggerFactory.getLogger(KafkaSourceConnector.class);
  private KafkaSourceConnectorConfig config;

  @Override
  public String version() {
    return Version.getVersion();
  }

  @Override
  public void start(Map<String, String> properties) throws ConnectException {
    log.info("Starting KafkaSourceConnector...");
    try {
      config = new KafkaSourceConnectorConfig(properties);
    } catch (ConfigException e) {
      throw new ConnectException("Couldn't start KafkaSourceConnector due to configuration error",
          e);
    }
  }

  @Override
  public Class<? extends Task> taskClass() {
    return KafkaSourceTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    List<Map<String, String>> taskConfigs = new ArrayList<>();
    for(int i=0; i < maxTasks; i++) {
      taskConfigs.add(this.config.originalsStrings());
    }
    return taskConfigs;
  }

  @Override
  public void stop() throws ConnectException {
  }

  @Override
  public ConfigDef config() {
    return KafkaSourceConnectorConfig.CONFIG_DEF;
  }

}
