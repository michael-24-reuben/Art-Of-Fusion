package org.artoffusion.agentportal;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.output.Response;

import java.util.concurrent.CompletableFuture;

class ModelStreamingChatLocalModelTest {
    public static void main(String[] args) {
        /*ChatLanguageModel chatModel = OpenAiChatModel.builder() // see [1] below
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-4o-mini")
                .responseFormat("json_schema") // see [2] below
                .strictJsonSchema(true) // see [2] below
                .logRequests(true)
                .logResponses(true)
                .build();
// OR
        ChatLanguageModel chatModel2 = AzureOpenAiChatModel.builder() // see [1] below
                .endpoint(System.getenv("AZURE_OPENAI_URL"))
                .apiKey(System.getenv("AZURE_OPENAI_API_KEY"))
                .deploymentName("gpt-4o-mini")
                .strictJsonSchema(true)
                .supportedCapabilities(Set.of(RESPONSE_FORMAT_JSON_SCHEMA)) // see [3] below
                .logRequestsAndResponses(true)
                .build();
// OR
        ChatLanguageModel chatModel3 = GoogleAiGeminiChatModel.builder() // see [1] below
                .apiKey(System.getenv("GOOGLE_AI_GEMINI_API_KEY"))
                .modelName("gemini-1.5-flash")
                .responseFormat(ResponseFormat.JSON) // see [4] below
                .logRequestsAndResponses(true)
                .build();
// OR
        ChatLanguageModel chatModel4 = OllamaChatModel.builder() // see [1] below
                .baseUrl("http://localhost:11434")
                .modelName("llama3.1")
                .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA) // see [5] below
                .logRequests(true)
                .logResponses(true)
                .build();*/
    }

    static class OllamaStreamingChatLocalModelTest {
        static String MODEL_NAME = "llama3.2"; // try other local ollama model names
        static String BASE_URL = "http://localhost:11434"; // local ollama base url

        public static void main(String[] args) {
            StreamingChatLanguageModel model = OllamaStreamingChatModel.builder()
                    .baseUrl(BASE_URL)
                    .modelName(MODEL_NAME)
                    .temperature(0.0)
                    .build();
            String userMessage = "Write a 100-word poem about Java and AI";

            CompletableFuture<Response<AiMessage>> futureResponse = new CompletableFuture<>();
            model.generate(userMessage, new StreamingResponseHandler<>() {

                @Override
                public void onNext(String token) {
                    System.out.print(token);
                }

                @Override
                public void onComplete(Response<AiMessage> response) {
                    System.out.println("\n\nResponse Completed.");
                    futureResponse.complete(response);
                }

                @Override
                public void onError(Throwable error) {
                    futureResponse.completeExceptionally(error);
                }
            });

            futureResponse.join();
        }
    }

    static class OpenaiStreamingChatLocalModelTest {
        private static final String OPENAI_API_KEY = "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";
        static String MODEL_NAME = "gpt-4o-mini"; // try other local openai model names

        public static void main(String[] args) {
            StreamingChatLanguageModel model = OpenAiStreamingChatModel.builder()
                    .apiKey(OPENAI_API_KEY)
                    .modelName(MODEL_NAME)
                    .temperature(0.9)
                    .build();
            String userMessage = "Write a 100-word poem about Java and AI";

            CompletableFuture<Response<AiMessage>> futureResponse = new CompletableFuture<>();
            model.generate(userMessage, new StreamingResponseHandler<>() {

                @Override
                public void onNext(String token) {
                    System.out.print(token);
                }

                @Override
                public void onComplete(Response<AiMessage> response) {
                    System.out.println("\n\nResponse Completed.");
                    futureResponse.complete(response);
                }

                @Override
                public void onError(Throwable error) {
                    futureResponse.completeExceptionally(error);
                }
            });

            System.out.println("Joining...\n" + futureResponse.join());
        }
    }
}