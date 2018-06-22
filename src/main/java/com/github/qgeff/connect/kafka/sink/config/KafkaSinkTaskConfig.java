package com.github.qgeff.connect.kafka.sink.config;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class KafkaSinkTaskConfig extends AbstractConfig {

    public static final String BOOTSTRAP_SERVER = "bootstrap.servers";
    public static final String BOOTSTRAP_SERVER_DEFAULT = "localhost:9092";

    public static final String TOPIC_SINK = "topic.sink";
    public static final String TOPIC_SINK_DEFAULT = "sink";

    public static final String GROUP_ID = "producer.group.id";
    public static final String GROUP_ID_DEFAULT = "default.group.id";
    public static final ConfigDef CONFIG_DEF = new ConfigDef();
    static ConfigDef config = new ConfigDef()
            .define(BOOTSTRAP_SERVER, ConfigDef.Type.STRING, BOOTSTRAP_SERVER_DEFAULT, ConfigDef.Importance.HIGH, null)
            .define(TOPIC_SINK, ConfigDef.Type.STRING, TOPIC_SINK_DEFAULT, ConfigDef.Importance.HIGH, null)
            .define(GROUP_ID, ConfigDef.Type.STRING, GROUP_ID_DEFAULT, ConfigDef.Importance.HIGH, null);

    public KafkaSinkTaskConfig(Map<?, ?> originals) {
        super(config, originals);
    }
}
