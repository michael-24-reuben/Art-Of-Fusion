package org.artoffusion.agentportal.config.beans.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;
import org.artoffusion.agentportal.config.beans.model.ollama.InitOllamaBeans;
import org.artoffusion.agentportal.config.beans.model.openai.InitOpenAiBeans;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "model.init")
public class ModelInitializer {
    private InitOllamaBeans ollama;
    private InitOpenAiBeans openAi;

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper objectMapper = mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error generating JSON", e);
        }
    }
}

