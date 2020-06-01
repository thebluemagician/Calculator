package com.nec.iudx.vertical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.nec.iudx.util.ConfigProperties;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

/**
 * Testing APIServer Rest Api
 * 
 * @author Md.Adil
 *
 */
@ExtendWith(VertxExtension.class)
public class APIServerVerticalTest {

  @BeforeEach
  public void setup(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new APIServerVerticle(), testContext.completing());
    vertx.deployVerticle(new CalculatorVerticle(), testContext.completing());
  }

  @Test
  @DisplayName("Testing Addition operation of Calculator 200")
  public void testAdd200(Vertx vertx, VertxTestContext testContext) {

    WebClient client = WebClient.create(vertx);
    client
        .get(Integer.parseInt(ConfigProperties.getProperties("server.port")), "localhost",
            "/v1/add/10/20")
        .as(BodyCodec.string()).send(testContext.succeeding(response -> testContext.verify(() -> {
          JsonObject res = new JsonObject(response.body());
          assertEquals(response.statusCode(), 200);
          assertEquals(res.getInteger("addition_result"), 30);
          testContext.completeNow();
        })));
  }

  @Test
  @DisplayName("Testing Addition operation of Calculator 404")
  public void testAdd404(Vertx vertx, VertxTestContext testContext) {

    WebClient client = WebClient.create(vertx);
    client
        .get(Integer.parseInt(ConfigProperties.getProperties("server.port")), "localhost",
            "/v1/add/10")
        .as(BodyCodec.string()).send(testContext.succeeding(response -> testContext.verify(() -> {
          assertEquals(response.statusCode(), 404);
          testContext.completeNow();
        })));

  }

  @Test
  @DisplayName("Testing Subtract operation of Calculator 200")
  public void testSub200(Vertx vertx, VertxTestContext testContext) {

    WebClient client = WebClient.create(vertx);
    client
        .get(Integer.parseInt(ConfigProperties.getProperties("server.port")), "localhost",
            "/v1/sub/10/5")
        .as(BodyCodec.string()).send(testContext.succeeding(response -> testContext.verify(() -> {
          JsonObject res = new JsonObject(response.body());
          assertEquals(response.statusCode(), 200);
          assertEquals(res.getInteger("subtract_result"), 5);
          testContext.completeNow();
        })));

  }

  @Test
  @DisplayName("Testing Multiply operation of Calculator 200")
  public void testMultiply200(Vertx vertx, VertxTestContext testContext) {

    WebClient client = WebClient.create(vertx);
    client
        .get(Integer.parseInt(ConfigProperties.getProperties("server.port")), "localhost",
            "/v1/multiply/10/5")
        .as(BodyCodec.string()).send(testContext.succeeding(response -> testContext.verify(() -> {
          JsonObject res = new JsonObject(response.body());
          assertEquals(response.statusCode(), 200);
          assertEquals(res.getInteger("multiply_result"), 50);
          testContext.completeNow();
        })));

  }

  @Test
  @DisplayName("Testing Division operation of Calculator 200")
  public void testDivide200(Vertx vertx, VertxTestContext testContext) {

    WebClient client = WebClient.create(vertx);
    client
        .get(Integer.parseInt(ConfigProperties.getProperties("server.port")), "localhost",
            "/v1/divide/10/5")
        .as(BodyCodec.string()).send(testContext.succeeding(response -> testContext.verify(() -> {
          JsonObject res = new JsonObject(response.body());
          assertEquals(response.statusCode(), 200);
          assertEquals(res.getInteger("divide_result"), 2);
          testContext.completeNow();
        })));

  }

  @Test
  @DisplayName("Testing Division operation of Calculator 400")
  public void testDivide400(Vertx vertx, VertxTestContext testContext) {

    WebClient client = WebClient.create(vertx);
    client
        .get(Integer.parseInt(ConfigProperties.getProperties("server.port")), "localhost",
            "/v1/divide/10/0")
        .as(BodyCodec.string()).send(testContext.succeeding(response -> testContext.verify(() -> {
          JsonObject res = new JsonObject(response.body());
          assertEquals(response.statusCode(), 400);
          assertEquals(res.getString("error"), "HTTP 400 Bad Request");
          testContext.completeNow();
        })));

  }



  @AfterEach
  public void tearDown(Vertx vertx) {
    vertx.close();
  }

}
