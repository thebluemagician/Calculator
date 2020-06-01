package com.nec.iudx.util;

import java.util.Map;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Md.Adil
 *
 */
public class RequestConversion {

  /**
   * Converting the RoutingContest query parameters to JsonObject
   * 
   * @param routingContext
   * @return
   */
  public static JsonObject convert2json(RoutingContext routingContext) {

    JsonObject jsonRequestParams = new JsonObject();
    Map<String, String> requestParameters = routingContext.pathParams();

    requestParameters.entrySet()
        .forEach(entry -> jsonRequestParams.put(entry.getKey(), entry.getValue()));

    return jsonRequestParams;
  }
}
