# **Art Of Fusion**  

**Art Of Fusion** is a modular Java-based framework designed to integrate various utilities, tools, and AI-driven components seamlessly. The project is still in its early stages of development, and several features are actively being implemented.  

## **Key Features**  

- **Core Application (`jfusion/`)** – A JavaFX-based framework supporting detachable plugin modules and API integrations.  
- **External Resources (`data/`, `resources/`)** – Stores assets such as icons, models (`ggml-base.en.bin`), and logs in an organized structure.  
- **Modular Design** – Includes multiple sub-projects such as `neural-hub` for global utilities and `agent-portal` for efficient LLM-based API calls.  
- **Library Management (`lib/`, `bin/`)** –  
  - `lib/` for injected dependencies (third-party libraries).  
  - `bin/` for custom-built utilities integrated directly into the framework.  
- **Customization & Plugin Support** – Allows controlled CSS styling, configurable plugins, and extendable application settings.  
- **STT Integration (`whisper-cpp4j/`)** – A speech-to-text module powered by `whisper.cpp`, enabling local audio transcription.  

---

## **Technology Stack**  
- **Java 22** (Core Development)  
- **JavaFX** (UI & Plugin System)  
- **Spring Boot** (LLM API Services)  
- **Maven** (Dependency Management)  
- **C++ (Whisper.cpp)** (STT Integration)  

---

## **Project Structure**  

Art Of Fusion follows a structured hierarchy for easier navigation and modular integration:  

```xml
<ProjectStructure name="Art Of Fusion" id="root">
    <Folder name="bin" id="bin">
        <Folder name="agent-portal" type="application/java-project"/>
        <Folder name="config">
            <Folder name="fxml"/>
            <Folder name="styles"/>
            <File name="project-structure.xml"/>
        </Folder>
        <Folder name="jfusion" type="application/java-project"/>
        <Folder name="lib"/>
        <Folder name="neural-hub" type="application/java-project"/>
        <Folder name="whisper-cpp4j" type="application/java-project"/>
    </Folder>
    <Folder name="data" id="data">
        <Folder name="models"/>
        <Folder name="temp"/>
    </Folder>
    <Folder name="docs"/>
    <Folder name="logs" id="logs"/>
    <Folder name="scripts"/>
    <Folder name="resources" id="resources">
        <Folder name="fonts"/>
        <Folder name="icons"/>
        <Folder name="images"/>
    </Folder>
    <File name="README.md"/>
    <File name="LICENSE"/>
    <File name="VERSION"/>
</ProjectStructure>
```

### **Project Breakdown**  

#### **Agent Portal (`bin/agent-portal/`)**  
A Spring Boot project dedicated to handling API calls for LLM interactions. By default, it integrates with **Ollama** but allows model configuration via properties files.  
Rather than making redundant API calls, `agent-portal` is injected as a dependency into `jfusion`, enabling direct communication and reducing latency.  

#### **JFusion (`bin/jfusion/`)**  
A **JavaFX-based framework** that manages the UI and plugin interactions.  
- Supports detachable plugins (similar to browser tabs).  
- Currently in development with features like **settings management**, **user profile handling**, and **plugin installation/removal**.  
- Provides an API integration layer for `agent-portal`.  

#### **Neural Hub (`bin/neural-hub/`)**  
The **core utility module** responsible for:  
- **Navigating the project structure** and managing dependencies.  
- **Providing NLP analysis**, custom JSON processing, and runtime global properties.  
- **Managing cross-module communication** without relying on `System.setProperty`, ensuring efficient data exchange.  

#### **Whisper-CPP4J (`bin/whisper-cpp4j/`)**  
A **speech-to-text (STT) module** built using `whisper.cpp`, enabling efficient voice transcription in Java via C++ integration.  
- Currently planned to be relocated to `/bin/config`, as it serves as a helper module rather than a core component.  
- Most of its codebase is derived from `whisper.cpp`, with additional Java classes for integration.  

---

## **Documentation & Contribution**  
Contributions are welcome! Check the documentation for details on module development, plugin integration, and AI model configurations.

## **Note that**
This project is in development
