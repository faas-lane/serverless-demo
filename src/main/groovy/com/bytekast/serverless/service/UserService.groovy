package com.bytekast.serverless.service

import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import groovy.util.logging.Log4j
import io.vertx.core.AbstractVerticle

@Log4j
class UserService extends AbstractVerticle {

  @Override
  void start() throws Exception {

    final eventBus = vertx.eventBus()

    eventBus.consumer('GET:/users') { message ->
      log.info(JsonOutput.prettyPrint(JsonOutput.toJson(message.body())))
      message.reply([statusCode: 200, body: 'Received GET:/users'])
    }

    eventBus.consumer('POST:/users') { message ->
      log.info(JsonOutput.prettyPrint(JsonOutput.toJson(message.body())))
      message.reply([statusCode: 201, body: 'Received POST:/users'])
    }

    eventBus.consumer('GET:/users/{id}') { message ->
      log.info(JsonOutput.prettyPrint(JsonOutput.toJson(message.body())))
      message.reply([statusCode: 200, body: 'Received GET:/users/{id}'])
    }

    eventBus.consumer('PUT:/users/{id}') { message ->
      log.info(JsonOutput.prettyPrint(JsonOutput.toJson(message.body())))
      message.reply([statusCode: 200, body: 'Received PUT:/users/{id}'])
    }

    eventBus.consumer('DELETE:/users/{id}') { message ->
      log.info(JsonOutput.prettyPrint(JsonOutput.toJson(message.body())))
      message.reply([statusCode: 200, body: 'Received DELETE:/users/{id}'])
    }
  }
}
