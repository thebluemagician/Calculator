package com.nec.iudx.vertical;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * @author Md.Adil
 *
 */
public class CalculatorVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(CalculatorVerticle.class);

  @Override
  public void start() {

    vertx.eventBus().consumer("calculator.add", message -> {
      add(message);
    });

    vertx.eventBus().consumer("calculator.subtract", message -> {
      sub(message);
    });

    vertx.eventBus().consumer("calculator.multiply", message -> {
      multiply(message);
    });

    vertx.eventBus().consumer("calculator.divide", message -> {
      divide(message);
    });

  }

  /**
   * Addition of two query parameter values
   * 
   * @param message
   */
  private void add(Message<Object> message) {

    log.info("Addition operation");
    JsonObject requestData = (JsonObject) message.body();
    int parameter1 = Integer.parseInt(requestData.getString("param1"));
    int parameter2 = Integer.parseInt(requestData.getString("param2"));

    int operationResult = parameter1 + parameter2;

    JsonObject result = new JsonObject();
    result.put("addition_result", operationResult);

    message.reply(result);
  }

  /**
   * Subtract two query parameter values
   * 
   * @param message
   */
  private void sub(Message<Object> message) {

    log.info("Substraction operation");
    JsonObject requestData = (JsonObject) message.body();
    int parameter1 = Integer.parseInt(requestData.getString("param1"));
    int parameter2 = Integer.parseInt(requestData.getString("param2"));

    int operationResult = parameter1 - parameter2;

    JsonObject result = new JsonObject();
    result.put("subtract_result", operationResult);

    message.reply(result);
  }

  /**
   * Multiply two query parameter values
   * 
   * @param message
   */
  private void multiply(Message<Object> message) {

    log.info("Multiplication operation");
    JsonObject request = (JsonObject) message.body();
    int parameter1 = Integer.parseInt(request.getString("param1"));
    int parameter2 = Integer.parseInt(request.getString("param2"));

    int operationResult = parameter1 * parameter2;

    JsonObject result = new JsonObject();
    result.put("multiply_result", operationResult);

    message.reply(result);
  }

  /**
   * Divide two value
   * 
   * @param message
   */
  private void divide(Message<Object> message) {

    log.info("Division operation");
    JsonObject request = (JsonObject) message.body();
    int parameter1 = Integer.parseInt(request.getString("param1"));
    int parameter2 = Integer.parseInt(request.getString("param2"));

    if (parameter2 == 0) {
      message.fail(400, "Arithmatic error: Divide by zero");
    } else {
      int operationResult = parameter1 / parameter2;

      JsonObject result = new JsonObject();
      result.put("divide_result", operationResult);

      message.reply(result);
    }
  }

}
