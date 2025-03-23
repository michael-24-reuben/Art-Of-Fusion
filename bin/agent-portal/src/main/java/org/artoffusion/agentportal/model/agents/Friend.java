package org.artoffusion.agentportal.model.agents;

import dev.langchain4j.service.SystemMessage;

interface Friend {
    @SystemMessage("You are a good friend of mine. Answer using slang.")
    String chat(String userMessage);
}