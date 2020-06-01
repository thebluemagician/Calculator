package com.nec.iudx.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.JsonObject;

/**
 * Custom Response format for uniformity.
 * 
 * @author Md.Adil
 *
 */
public class ApiErrorResponse {

  @JsonProperty
  private String timestamp;

  @JsonProperty
  private int status;

  @JsonProperty
  private String error;

  @JsonProperty
  private String message;

  // getter and setters
  // Builder
  public static final class ApiErrorResponseBuilder {
    private int status;
    private String error;
    private String message;
    private String timestamp;

    public ApiErrorResponseBuilder() {}

    public static ApiErrorResponseBuilder anApiErrorResponse() {
      return new ApiErrorResponseBuilder();
    }

    public ApiErrorResponseBuilder withStatus(int status) {
      this.status = status;
      return this;
    }

    public ApiErrorResponseBuilder withError(String error) {
      this.error = error;
      return this;
    }

    public ApiErrorResponseBuilder withMessage(String message) {
      this.message = message;
      return this;
    }


    public ApiErrorResponseBuilder withTimeStamp() {
      LocalDateTime dateTime = LocalDateTime.now();
      this.timestamp = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

      return this;
    }

    public String build() {
      ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
      apiErrorResponse.status = this.status;
      apiErrorResponse.error = this.error;
      apiErrorResponse.message = this.message;
      apiErrorResponse.timestamp = this.timestamp;

      ObjectMapper mapper = new ObjectMapper();
      String str = null;
      try {
        str = mapper.writeValueAsString(apiErrorResponse);
      } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      JsonObject jsonObject = new JsonObject(str);
      return jsonObject.toString();
    }
  }

}

