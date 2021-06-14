package com.arjen0203.codex.core.rabbit.objects;

import java.io.Serializable;

import com.arjen0203.codex.core.rabbit.exceptions.InvalidDataException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.server.ResponseStatusException;

/** Response that gets returned by a Request-Reply Kafka Message. */
@Data
@NoArgsConstructor
public class Response implements Serializable {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private boolean success;
  private String data;
  private ResponseStatusException exception;

  /**
   * Creates a Response object.
   *
   * @param success boolean stating if the Response is successful
   * @param data object which contains the result of the request
   * @param exception exception that got thrown trying to process the Kafka request
   */
  public Response(boolean success, Object data, ResponseStatusException exception) {
    this.success = success;
    this.exception = exception;

    try {
      this.data = OBJECT_MAPPER.writeValueAsString(data);
    } catch (JsonProcessingException ex) {
      throw new InvalidDataException(data);
    }
  }

  /**
   * Creates a Successful Response object.
   *
   * @param data object which contains the result of the request
   * @return the Response object
   */
  public static Response success(Object data) {
    return new Response(true, data, null);
  }

  /**
   * Creates a Successful Response object.
   *
   * @return the Response object
   */
  public static Response success() {
    return new Response(true, true, null);
  }

  /**
   * Creates an Unsuccessful Response object.
   *
   * @param exception object which contains the result of the request
   * @return the Response object
   */
  public static Response error(ResponseStatusException exception) {
    return new Response(false, null, exception);
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
