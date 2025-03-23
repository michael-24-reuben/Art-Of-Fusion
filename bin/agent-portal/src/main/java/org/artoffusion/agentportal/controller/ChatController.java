package org.artoffusion.agentportal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.artoffusion.agentportal.config.beans.model.ModelInitializer;
import org.artoffusion.agentportal.config.beans.model.ModelOptions;
import org.artoffusion.agentportal.config.beans.model.ollama.InitOllamaBeans;
import org.artoffusion.agentportal.config.beans.model.openai.InitOpenAiBeans;
import org.artoffusion.agentportal.config.beans.task.ChatSessionBeans;
import org.artoffusion.agentportal.config.beans.task.EmbeddingsBeans;
import org.artoffusion.agentportal.config.beans.task.TaskManager;
import org.artoffusion.agentportal.model.ModelContents;
import org.artoffusion.core.neuralhub.session.RuntimeSession;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;

@RestController
public class ChatController implements RuntimeSession {

    private final ModelOptions mdl_options;
    private final ModelInitializer mdl_initializer;
    private final TaskManager taskManager;

    public ChatController(ModelInitializer initializer, ModelOptions options, TaskManager taskManager) {
        this.mdl_options = options;
        this.mdl_initializer = initializer;
        this.taskManager = taskManager;
    }

    public ChatLanguageModel loadTaskChatModel(ChatSessionBeans chatSession, String modelType) {
        return loadTaskChatModel(chatSession.getModelProvider(), modelType);
    }

    public ChatLanguageModel loadTaskChatModel(EmbeddingsBeans embeddings, String modelType) {
        return loadTaskChatModel(embeddings.getModelProvider(), modelType);
    }

    /**
     * @param modelProvider The pre-configured model's provider. i.e. {@code ollama}, {@code open-ai}, {@code vertexai}
     * @param modelCase     The pre-assigned case. i.e. {@code generate}, {@code vision}, {@code stream}, {@code embed}
     * @return
     */
    public ChatLanguageModel loadTaskChatModel(String modelProvider, String modelCase) {
        // Gets the model provider from bean property `task.?.model-provider`
        ObjectNode node = new ObjectMapper().convertValue(mdl_initializer.toJson(), ObjectNode.class);
        // Converts the modelInitializer object to json and traverses towards `model.init.${MODEL_PROVIDER}.chat.${MODEL_TYPE}-model-name`
        JsonNode modelCaseNode = node.get(modelProvider).get("chat").get(modelCase);
        String modelName = modelCaseNode != null && !modelCaseNode.isNull() && !modelCaseNode.isMissingNode() ? modelCaseNode.asText() : "modelName";

        return initializeChatModel(modelProvider, modelName);
    }

    public ChatLanguageModel initializeChatModel(String modelProvider, String modelName) {
        if (modelProvider == null) return null;

        modelProvider = modelProvider.toLowerCase().trim();
        return switch (modelProvider) {
            case "ollama" -> {
                InitOllamaBeans ollama = mdl_initializer.getOllama();
                String defModelName = ollama.getChat().getModelName();

                yield OllamaChatModel.builder()
                        .baseUrl(ollama.getBaseUrl())
                        .modelName(modelName == null ? defModelName : modelName)
                        .temperature(mdl_options.getTemperature())
                        .topK(mdl_options.getTopK())
                        .topP(mdl_options.getTopP())
                        .repeatPenalty(mdl_options.getRepeatPenalty())
                        .seed(mdl_options.getSeed())
                        .numPredict(mdl_options.getNumPredict())
                        .stop(Arrays.stream(mdl_options.getStop()).toList())
                        .format(mdl_options.getResponseFormat())
                        .timeout(Duration.parse(mdl_options.getTimeout()))
                        .maxRetries(mdl_options.getMaxRetries())
                        .numCtx(mdl_options.getNumCtx())
                        .logRequests(mdl_options.getLogRequests())
                        .logResponses(mdl_options.getLogResponses())
                        .build();
            }
            case "openai" -> {
                InitOpenAiBeans openAi = mdl_initializer.getOpenAi();
                String defModelName = openAi.getChat().getModelName();

                yield OllamaChatModel.builder()
                        .baseUrl(openAi.getApiKey())
                        .modelName(modelName == null ? defModelName : modelName)
                        .temperature(mdl_options.getTemperature())
                        .topK(mdl_options.getTopK())
                        .topP(mdl_options.getTopP())
                        .repeatPenalty(mdl_options.getRepeatPenalty())
                        .seed(mdl_options.getSeed())
                        .numPredict(mdl_options.getNumPredict())
                        .stop(Arrays.stream(mdl_options.getStop()).toList())
                        .format(mdl_options.getResponseFormat())
                        .timeout(Duration.parse(mdl_options.getTimeout()))
                        .maxRetries(mdl_options.getMaxRetries())
                        .numCtx(mdl_options.getNumCtx())
                        .logRequests(mdl_options.getLogRequests())
                        .logResponses(mdl_options.getLogResponses())
                        .build();
            }
            default -> null;
        };
    }

    public ModelContents loadTaskStreamingChatModel(ChatSessionBeans chatSession, String modelType) {
        return loadTaskStreamingChatModel(chatSession.getModelProvider(), modelType);
    }

    public ModelContents loadTaskStreamingChatModel(EmbeddingsBeans embeddings, String modelType) {
        return loadTaskStreamingChatModel(embeddings.getModelProvider(), modelType);
    }

    public ModelContents loadTaskStreamingChatModel(String modelProvider, @Nullable String modelType) {
        modelType = modelType == null ? "modelName" : modelType + "ModelName";
        try {
            // Gets the model provider from bean property `task.?.model-provider`
            ObjectNode node = new ObjectMapper().readValue(mdl_initializer.toJson(), ObjectNode.class);
            // Converts the modelInitializer object to json and traverses towards `model.init.${MODEL_PROVIDER}.chat.${MODEL_TYPE}-model-name`
            JsonNode modelCase = node.get(modelProvider).get("chat").get(modelType);
            String modelName = modelCase != null && !modelCase.isNull() && !modelCase.isMissingNode() ? modelCase.asText() : null;

            return initializeStreamingChatModel(modelProvider, modelName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ModelContents initializeStreamingChatModel(String modelProvider, String modelName) {
        if (modelProvider == null) return null;

        ModelContents.ModelContentOptions options = new ModelContents.ModelContentOptions();
        modelProvider = modelProvider.toLowerCase().trim();

        Double temperature = options.temperature(mdl_options.getTemperature()).temperature();
        Integer topK = options.topK(mdl_options.getTopK()).topK();
        Double topP = options.topP(mdl_options.getTopP()).topP();
        Double repeatPenalty = options.repeatPenalty(mdl_options.getRepeatPenalty()).repeatPenalty();
        Integer seed = options.seed(mdl_options.getSeed()).seed();
        String[] stops = options.stop(mdl_options.getStop()).stop();
        String duration = options.timeout(mdl_options.getTimeout()).timeout();
        Boolean logRequests = options.logRequests(mdl_options.getLogRequests()).logRequests();
        Boolean logResponses = options.logResponses(mdl_options.getLogResponses()).logResponses();
        Integer numPredict = options.numPredict(mdl_options.getNumPredict()).numPredict();
        Integer numCtx = options.numCtx(mdl_options.getNumCtx()).numCtx();
        String format = options.responseFormat(mdl_options.getResponseFormat()).responseFormat();

        StreamingChatLanguageModel languageModel = switch (modelProvider) {
            case "ollama" -> {
                InitOllamaBeans ollama = mdl_initializer.getOllama();
                String defModelName = ollama.getChat().getModelName();
                modelName = modelName == null ? defModelName : modelName;

                OllamaStreamingChatModel.OllamaStreamingChatModelBuilder builder = OllamaStreamingChatModel.builder()
                        .baseUrl(ollama.getBaseUrl())
                        .modelName(modelName);

                if (temperature != null) builder.temperature(temperature);
                if (topK != null) builder.topK(topK);
                if (topP != null) builder.topP(topP);
                if (repeatPenalty != null) builder.repeatPenalty(repeatPenalty);
                if (seed != null) builder.seed(seed);
                if (numPredict != null) builder.numPredict(numPredict);
                if (stops != null) builder.stop(Arrays.stream(stops).toList());
                if (format != null) builder.format(format);
                if (duration != null) builder.timeout(Duration.parse(duration));
                if (numCtx != null) builder.numCtx(numCtx);
                if (logRequests != null) builder.logRequests(logRequests);
                if (logResponses != null) builder.logResponses(logResponses);

                yield builder.build();
            }
            case "openai" -> {
                InitOpenAiBeans openAi = mdl_initializer.getOpenAi();
                String defModelName = openAi.getChat().getModelName();
                modelName = modelName == null ? defModelName : modelName;

                OpenAiStreamingChatModel.OpenAiStreamingChatModelBuilder builder = OpenAiStreamingChatModel.builder()
                        .baseUrl(openAi.getApiKey())
                        .modelName(modelName);

                if (temperature != null) builder.temperature(temperature);
                if (topP != null) builder.topP(topP);
                if (seed != null) builder.seed(seed);
                if (stops != null) builder.stop(Arrays.stream(stops).toList());
                if (duration != null) builder.timeout(Duration.parse(duration));
                if (logRequests != null) builder.logRequests(logRequests);
                if (logResponses != null) builder.logResponses(logResponses);

                yield builder.build();
            }
            default -> null;
        };

        ModelContents modelContents = new ModelContents(modelProvider, modelName);
        modelContents.setModelOptions(options);
        modelContents.setLanguageModel(languageModel);
        return modelContents;
    }

//    @GetMapping("/chat")
//    public String model(@RequestParam(value = "message", defaultValue = "Hello") String message) {
//        return chatLanguageModel.generate(message);
//    }
}

/*
final double temperature = options.temperature(mdl_options.getTemperature()).temperature();
        int topK = options.topK(mdl_options.getTopK()).topK();
        final double topP = options.topP(mdl_options.getTopP()).topP();
        double repeatPenalty = options.repeatPenalty(mdl_options.getRepeatPenalty()).repeatPenalty();
        final int seed = options.seed(mdl_options.getSeed()).seed();
        final List<String> list = Arrays.stream(options.stop(mdl_options.getStop()).stop()).toList();
        final Duration duration = Duration.parse(options.timeout(mdl_options.getTimeout()).timeout());
        final boolean logRequests = options.logRequests(mdl_options.getLogRequests()).logRequests();
        boolean logResponses = options.logResponses(mdl_options.getLogResponses()).logResponses();
        int numPredict = options.numPredict(mdl_options.getNumPredict()).numPredict();
        int numCtx = options.numCtx(mdl_options.getNumCtx()).numCtx();
        String format = options.responseFormat(mdl_options.getResponseFormat()).responseFormat();
        StreamingChatLanguageModel languageModel = switch (modelProvider) {
            case "ollama" -> {
                InitOllamaBeans ollama = mdl_initializer.getOllama();
                String defModelName = ollama.getChat().getModelName();
                modelName = modelName == null ? defModelName : modelName;

                yield OllamaStreamingChatModel.builder()
                        .baseUrl(ollama.getBaseUrl())
                        .modelName(modelName)
                        .temperature(temperature)
                        .topK(topK)
                        .topP(topP)
                        .repeatPenalty(repeatPenalty)
                        .seed(seed)
                        .numPredict(numPredict)
                        .stop(list.isEmpty()? null : list)
                        .format(format)
                        .timeout(duration)
                        .numCtx(numCtx)
                        .logRequests(logRequests)
                        .logResponses(logResponses)
                        .build();
            }
            case "openai" -> {
                InitOpenAiBeans openAi = mdl_initializer.getOpenAi();
                String defModelName = openAi.getChat().getModelName();
                modelName = modelName == null ? defModelName : modelName;

                yield OpenAiStreamingChatModel.builder()
                        .baseUrl(openAi.getApiKey())
                        .modelName(modelName)
                        .temperature(temperature)
                        .topP(topP)
                        .seed(seed)
                        .stop(list)
                        .timeout(duration)
                        .logRequests(logRequests)
                        .logResponses(logResponses)
                        .build();
            }
            default -> null;
        };
*/