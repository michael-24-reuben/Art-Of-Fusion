package org.artoffusion.agentportal;

import org.artoffusion.agentportal.config.beans.model.ModelInitializer;
import org.artoffusion.agentportal.config.beans.task.TaskManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.artoffusion.agentportal.config.beans.model.ModelOptions;

@SpringBootApplication
@EnableConfigurationProperties({ModelOptions.class, ModelInitializer.class, TaskManager.class})
public class AgentPortalApplication {
    private static volatile boolean running = false;
    private static volatile ApplicationContext context;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (running || context != null) stop();
        }));
    }

    public static synchronized void start() {
        if (!running) {
            running = true;
            context = SpringApplication.run(AgentPortalApplication.class);
        }
    }

    public static void startAsync() {
        if (!running) {
            running = true;
            new Thread(() -> context = SpringApplication.run(AgentPortalApplication.class)).start();
        }
    }

    public static int stop() {
        if (context instanceof ConfigurableApplicationContext configurableContext) {
            configurableContext.close(); // Ensures proper shutdown
            context = null;
            running = false;
            return 0;
        }
        return 1;
    }

    public boolean isRunning() {
        return running;
    }

    public static void forceStop() {
        System.exit(0);
    }

    public static ApplicationContext getContext() {
        if (context == null) {
            throw new IllegalStateException("Spring Boot is not running! Call start() first.");
        }
        return context;
    }

    public static void main(String[] args) {
        start();

//        ModelInitializer modelInitializer = getContext().getBean(ModelInitializer.class);
//        System.out.println("taskManager.getTest() = " + taskManager.getTest());
//        System.out.println("modelInitializer.toJson() = " + modelInitializer.toJson());
    }

}
