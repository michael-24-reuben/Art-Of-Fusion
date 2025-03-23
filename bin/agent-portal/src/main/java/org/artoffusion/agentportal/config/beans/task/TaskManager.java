package org.artoffusion.agentportal.config.beans.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "task")
public class TaskManager {
    private ChatSessionBeans chatSession;
    private EmbeddingsBeans embeddings;
}

