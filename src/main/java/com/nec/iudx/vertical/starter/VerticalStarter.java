package com.nec.iudx.vertical.starter;

import com.nec.iudx.vertical.APIServerVerticle;
import com.nec.iudx.vertical.CalculatorVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * 
 * @author Md.Adil
 *
 */
public class VerticalStarter extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(VerticalStarter.class);

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    /* Deploying the verticals */
    vertx.deployVerticle(new APIServerVerticle(), res -> {
      if (res.succeeded()) {
        log.info("Api Vertical deployed, id is " + res.result());
      } else {
        log.error("Api Vertical Deployment failed!" + res.result());
      }
    });

    vertx.deployVerticle(new CalculatorVerticle(), res -> {
      if (res.succeeded()) {
        log.info("Calculator Vertical deployed, id is " + res.result());
      } else {
        log.error("Calculator Vertical Deployment failed!" + res.result());
      }
    });



  }

}
