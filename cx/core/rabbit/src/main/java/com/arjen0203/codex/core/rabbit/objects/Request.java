package com.arjen0203.codex.core.rabbit.objects;

import com.arjen0203.codex.core.rabbit.exceptions.InvalidDataException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@NoArgsConstructor
public class Request {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String data;

    public Request(Object data) {
        try {
            this.data = OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException ex) {
            throw new InvalidDataException(data);
        }
    }

    @JsonIgnore
    @SneakyThrows({JsonProcessingException.class})
    public <T> T getData(Class<T> type) {
        return OBJECT_MAPPER.readValue(data, type);
    }

    @JsonIgnore
    @SneakyThrows({JsonProcessingException.class})
    public <T> T getData(TypeReference<T> type) {
        return OBJECT_MAPPER.readValue(data, type);
    }
}
