package org.artoffusion.agentportal.model.parameters;

import lombok.RequiredArgsConstructor;
import org.artoffusion.agentportal.config.beans.model.ModelOptions;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ModelAccessService {
    private final ModelOptions modelOptions;

    public Map<String, Object> getAccessibleFields(String modelName) {
        Map<String, Object> accessibleFields = new HashMap<>();
        for (Field field : ModelOptions.class.getDeclaredFields()) {
            ModelAccess annotation = field.getAnnotation(ModelAccess.class);
            if (annotation != null && List.of(annotation.value()).contains(modelName)) {
                field.setAccessible(true);
                try {
                    accessibleFields.put(field.getName(), field.get(modelOptions));
                } catch (IllegalAccessException ignored) {}
            }
        }
        return accessibleFields;
    }
}
