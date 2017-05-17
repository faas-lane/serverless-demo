package com.bytekast.serverless.vertx

import com.amazonaws.services.lambda.runtime.Context
import io.vertx.core.AsyncResult
import io.vertx.core.eventbus.Message

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

abstract class DefaultVertxHandler extends VertxHandler {

  private static final Long BUFFER_MS = 2000

  protected abstract String resolveUniqueRoute(Map input)

  Map defaultHandler(Map input, Context context) {

    final future = new CompletableFuture<Map>()
    VERTX.eventBus().send(resolveUniqueRoute(input), input) { AsyncResult<Message<Map>> asyncResult ->
      if (asyncResult.succeeded()) {
        future.complete(asyncResult.result().body())
      } else {
        future.completeExceptionally(asyncResult.cause())
      }
    }

    future.get(context.getRemainingTimeInMillis() - BUFFER_MS, TimeUnit.MILLISECONDS)
  }
}
