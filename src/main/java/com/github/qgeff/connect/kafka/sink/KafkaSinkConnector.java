package com.github.qgeff.connect.kafka.sink;

import com.github.qgeff.connect.kafka.sink.config.KafkaSinkConfig;
import com.github.qgeff.connect.kafka.util.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaSinkConnector extends SinkConnector {
  private static final Logger log = LoggerFactory.getLogger(KafkaSinkConnector.class);

  private Map<String, String> configProps;

  public Class<? extends Task> taskClass() {
    return KafkaSinkTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int maxTasks) {
    log.info("Setting task configurations for {} workers.", maxTasks);
    final List<Map<String, String>> configs = new ArrayList<>(maxTasks);
    for (int i = 0; i < maxTasks; ++i) {
      configs.add(configProps);
    }
    return configs;
  }

  @Override
  public void start(Map<String, String> props) {
    configProps = props;
  }

  @Override
  public void stop() {
  }

  @Override
  public ConfigDef config() {
    return KafkaSinkConfig.CONFIG_DEF;
  }

  @Override
  public Config validate(Map<String, String> connectorConfigs) {
    return super.validate(connectorConfigs);
  }

  @Override
  public String version() {
    return Version.getVersion();
  }

}
