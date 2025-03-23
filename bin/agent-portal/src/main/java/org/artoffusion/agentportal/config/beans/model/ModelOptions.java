package org.artoffusion.agentportal.config.beans.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;
import org.artoffusion.agentportal.model.parameters.ModelAccess;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "model.options")
public class ModelOptions {
    @ModelAccess({"ollama", "openai"})
    private Boolean logRequests;
    @ModelAccess({"ollama", "openai"})
    private Boolean logResponses;
    @ModelAccess({"ollama", "openai"})
    private String timeout;
    @ModelAccess({"ollama", "openai"})
    private Integer maxRetries;
    @ModelAccess({"ollama", "openai"})
    private Integer numCtx;// max_tokens
    @ModelAccess({"ollama", "openai"})
    private String responseFormat;
    @ModelAccess({"ollama"})
    private Integer seed;
    @ModelAccess({"ollama"})
    private Integer numPredict;
    @ModelAccess({"ollama"})
    private Integer topK;
    @ModelAccess({"ollama", "openai"})
    private Double topP;
    @ModelAccess({"ollama", "openai"})
    private Double temperature;
    @ModelAccess({"ollama"})
    private Double repeatPenalty;
    @ModelAccess({"ollama", "openai"})
    private String[] stop;

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error generating JSON", e);
        }
    }
}

/*
    @ModelAccess({"ollama"})
    private boolean numa;
    @ModelAccess({"ollama", "openai"})
    private int numCtx;// max_tokens
    @ModelAccess({"ollama"})
    private int numBatch;
    @ModelAccess({"ollama"})
    private int numGpu;
    @ModelAccess({"ollama"})
    private int mainGpu;
    @ModelAccess({"ollama"})
    private boolean lowVram;
    @ModelAccess({"ollama"})
    private boolean f16Kv;
    @ModelAccess({"ollama"})
    private String logitsAll;
    @ModelAccess({"ollama"})
    private String vocabOnly;
    @ModelAccess({"ollama"})
    private Boolean useMmap;
    @ModelAccess({"ollama"})
    private boolean useMlock;
    @ModelAccess({"ollama"})
    private int numThread;
    @ModelAccess({"ollama"})
    private int numKeep;
    @ModelAccess({"ollama"})
    private int seed;
    @ModelAccess({"ollama"})
    private int numPredict;
    @ModelAccess({"ollama"})
    private int topK;
    @ModelAccess({"ollama", "openai"})
    private double topP;
    @ModelAccess({"ollama"})
    private double tfsZ;
    @ModelAccess({"ollama"})
    private double typicalP;
    @ModelAccess({"ollama"})
    private int repeatLastN;
    @ModelAccess({"ollama", "openai"})
    private double temperature;
    @ModelAccess({"ollama"})
    private double repeatPenalty;
    @ModelAccess({"ollama", "openai"})
    private double presencePenalty;
    @ModelAccess({"ollama", "openai"})
    private double frequencyPenalty;
    @ModelAccess({"ollama"})
    private int mirostat;
    @ModelAccess({"ollama"})
    private double mirostatTau;
    @ModelAccess({"ollama"})
    private double mirostatEta;
    @ModelAccess({"ollama"})
    private boolean penalizeNewline;
    @ModelAccess({"ollama", "openai"})
    private String[] stop;
    @ModelAccess({"ollama"})
    private String functions;
    @ModelAccess({"ollama"})
    private boolean proxyToolCalls;
*/