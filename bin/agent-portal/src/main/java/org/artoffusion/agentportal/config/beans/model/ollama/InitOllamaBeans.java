package org.artoffusion.agentportal.config.beans.model.ollama;

import lombok.Getter;
import lombok.Setter;
import org.artoffusion.agentportal.config.beans.model.MappedChatModel;

@Getter
@Setter
public class InitOllamaBeans {
    private String baseUrl;
    private MappedChatModel chat;
}
