package org.artoffusion.agentportal.config.utils;

public enum Task {
    CHAT_SESSION("chat-session"),
    EMBEDDINGS("embeddings");

    final String name;
    Task(String v) {
        name = v;
    }

    public String getName() {
        return name;
    }
}
