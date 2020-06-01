package com.nec.iudx.vertical;

import com.nec.iudx.util.ApiErrorResponse;
import com.nec.iudx.util.ConfigProperties;
import com.nec.iudx.util.RequestConversion;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @author Md.Adil
 *
 */
public class APIServerVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(APIServerVerticle.class);

  @SuppressWarnings("deprecation")
  @Override
  public void start() {

    /* Rest Api Router initialization */
    Router router = Router.router(vertx);
    router.get("/v1/add/:param1/:param2").handler(this::add);
    router.get("/v1/sub/:param1/:param2").handler(this::subtract);
    router.get("/v1/multiply/:param1/:param2").handler(this::multiply);
    router.get("/v1/divide/:param1/:param2").handler(this::divide);

    /* Creating Rest Server */
    vertx.createHttpServer().requestHandler(router::accept)
        .listen(Integer.parseInt(ConfigProperties.getProperties("server.port")));
  }

  public void add(RoutingContext routingContext) {

    log.info("Conveting the Request into JsonObject, request: "
        + routingContext.pathParams().toString());

    JsonObject requestParamData = RequestConversion.convert2json(routingContext);

    if (!requestParamData.isEmpty()) {

      log.info("Request data published");
      requestPublish("calculator.add", requestParamData, routingContext.response());
    } else {

      log.error("Error in publishing Request data");
      requestErrorUtil(routingContext);
    }

  }

  public void subtract(RoutingContext routingContext) {

    log.info("Conveting the Request into JsonObject, request: "
        + routingContext.pathParams().toString());

    JsonObject requestParamData = RequestConversion.convert2json(routingContext);

    if (!requestParamData.isEmpty()) {

      log.info("Request data published");
      requestPublish("calculator.subtract", requestParamData, routingContext.response());
    } else {

      log.error("Error in publishing Request data");
      requestErrorUtil(routingContext);
    }

  }

  public void multiply(RoutingContext routingContext) {

    log.info("Conveting the Request into JsonObject, request: "
        + routingContext.pathParams().toString());

    JsonObject requestParamData = RequestConversion.convert2json(routingContext);

    if (!requestParamData.isEmpty()) {

      log.info("Request data published");
      requestPublish("calculator.multiply", requestParamData, routingContext.response());
    } else {

      log.error("Error in publishing Request data");
      requestErrorUtil(routingContext);
    }

  }

  public void divide(RoutingContext routingContext) {

    log.info("Conveting the Request into JsonObject, request: "
        + routingContext.pathParams().toString());

    JsonObject requestParamData = RequestConversion.convert2json(routingContext);

    if (!requestParamData.isEmpty()) {

      log.info("Request data published");
      requestPublish("calculator.divide", requestParamData, routingContext.response());
    } else {

      log.error("Error in publishing Request data");
      requestErrorUtil(routingContext);
    }

  }

  private void requestPublish(String apiEvent, JsonObject requestParamData,
      HttpServerResponse httpServerResponse) {

    vertx.eventBus().request(apiEvent, requestParamData, new DeliveryOptions(), responseHandler -> {

      if (responseHandler.succeeded()) {
        String reply = responseHandler.result().body().toString();

        httpServerResponse.setStatusCode(200)
            .putHeader(HttpHeaders.CONTENT_TYPE.toString(), "application/json").end(reply);

      } else if (responseHandler.failed()) {

        String apiErrorResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
            .withMessage(responseHandler.cause().getMessage()).withError("HTTP 400 Bad Request")
            .withStatus(400).withTimeStamp().build();

        httpServerResponse.setStatusCode(400)
            .putHeader(HttpHeaders.CONTENT_TYPE.toString(), "application/json")
            .end(apiErrorResponse);
      }
    });
  }


  public void requestErrorUtil(RoutingContext routingContext) {

    String apiErrorResponse = new ApiErrorResponse.ApiErrorResponseBuilder()
        .withMessage("Bad Request").withError("Incorrect/invalid request query parameter")
        .withStatus(400).withTimeStamp().build();

    routingContext.response().putHeader("content-type", "application/json; charset=utf-8")
        .setStatusCode(400).end(apiErrorResponse);
  }

}
