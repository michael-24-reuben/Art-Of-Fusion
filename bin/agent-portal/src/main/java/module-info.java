module org.artoffusion.agentportal {
    requires langchain4j;
    requires langchain4j.core;
    requires langchain4j.ollama;
    requires langchain4j.spring.boot.starter;
    requires org.slf4j;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires langchain4j.open.ai;
//    requires spring.ai.core;
    requires spring.core;
    requires spring.beans;
    requires org.artoffusion.core.neuralhub;
    requires com.fasterxml.jackson.databind;
    requires static lombok;

    exports org.artoffusion.agentportal;
    exports org.artoffusion.agentportal.controller;
    exports org.artoffusion.agentportal.model.parameters;
    exports org.artoffusion.agentportal.config.beans.model;
    exports org.artoffusion.agentportal.config.beans.model.ollama;
    exports org.artoffusion.agentportal.config.beans.model.openai;
    exports org.artoffusion.agentportal.config.beans.task;
    exports org.artoffusion.agentportal.config.utils;
    exports org.artoffusion.agentportal.model;
    exports org.artoffusion.agentportal.model.agents;

    opens org.artoffusion.agentportal;
    opens org.artoffusion.agentportal.services;
    opens org.artoffusion.agentportal.controller;
    opens org.artoffusion.agentportal.model.parameters;
    opens org.artoffusion.agentportal.config.beans.model;
    opens org.artoffusion.agentportal.config.beans.model.ollama;
    opens org.artoffusion.agentportal.config.beans.model.openai;
    opens org.artoffusion.agentportal.config.beans.task;
    opens org.artoffusion.agentportal.config.utils;
    opens org.artoffusion.agentportal.model;
}