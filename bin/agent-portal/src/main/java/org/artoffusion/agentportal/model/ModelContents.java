package org.artoffusion.agentportal.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.artoffusion.agentportal.config.beans.model.ModelOptions;

import java.util.Arrays;

public class ModelContents {
    @Getter
    private final String modelName;
    @Getter
    private final String modelProvider;
    private Object model;
    @Getter
    @Setter
    private ModelContentOptions modelOptions;

    public ModelContents(String modelProvider, String modelName) {
        this.modelProvider = modelProvider;
        this.modelName = modelName;
    }

    public void setLanguageModel(ChatLanguageModel languageModel) {
        model = languageModel;
    }

    public void setLanguageModel(StreamingChatLanguageModel languageModel) {
        model = languageModel;
    }

    public boolean isStreamingModel() {
        return model.getClass().isAssignableFrom(StreamingChatLanguageModel.class);
    }

    public boolean isChatModel() {
        return model.getClass().isAssignableFrom(ChatLanguageModel.class);
    }

    public <V> V getLanguageModel() {
        return (V) model;
    }

    @Getter
    @Setter
    @Accessors(fluent = true, chain = true)
    public static class ModelContentOptions extends ModelOptions {
        private Boolean logRequests;
        private Boolean logResponses;
        private String timeout;
        private Integer maxRetries;
        private Integer numCtx;
        private String responseFormat;
        private Integer seed;
        private Integer numPredict;
        private Integer topK;
        private Double topP;
        private Double temperature;
        private Double repeatPenalty;
        private String[] stop;

        @Override
        public String toJson() {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            ObjectNode fields = objectMapper.createObjectNode();
            fields.put("logRequests", logRequests());
            fields.put("logResponses", logResponses());
            fields.put("timeout", timeout());
            fields.put("maxRetries", maxRetries());
            fields.put("numCtx", numCtx());
            fields.put("responseFormat", responseFormat());
            fields.put("seed", seed());
            fields.put("numPredict", numPredict());
            fields.put("topK", topK());
            fields.put("topP", topP());
            fields.put("temperature", temperature());
            fields.put("repeatPenalty", repeatPenalty());
            ArrayNode stopNodes = objectMapper.createArrayNode();
            if (getStop() != null)
                Arrays.stream(getStop()).toList().forEach(stopNodes::add);
            fields.set("stop", stopNodes);
            return fields.toPrettyString();
        }
/*
        @Override
        public String toJson() {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            try {
                return objectMapper.writeValueAsString(this);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error generating JSON", e);
            }
        }
*/
    }
}
