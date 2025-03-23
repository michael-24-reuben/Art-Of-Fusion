package org.artoffusion.agentportal.config.beans.model.openai;

import lombok.Getter;
import lombok.Setter;
import org.artoffusion.agentportal.config.beans.model.MappedChatModel;

@Getter
@Setter
public class InitOpenAiBeans {
    private String apiKey;
    private MappedChatModel chat;
}
