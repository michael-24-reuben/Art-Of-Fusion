package org.artoffusion.agentportal.config.beans.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
public class MappedChatModel {
    private String modelName;
    private String generateModelName;
    private String streamModelName;
    private String visionModelName;
    private String embedModelName;
}
