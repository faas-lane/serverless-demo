package com.bytekast.serverless.vertx

import io.vertx.core.DeploymentOptions
import io.vertx.core.Verticle
import io.vertx.core.Vertx

import java.util.concurrent.CountDownLatch

abstract class VertxHandler {

  final Vertx VERTX

  VertxHandler() {
    VERTX = init()
  }

  private Vertx init() {
    System.setProperty('vertx.disableFileCPResolving', 'true')
    def latch = new CountDownLatch(getServices().size())
    def listener = { ar -> latch.countDown() }

    final vertx = Vertx.vertx()
    registerDefaultCodecs(vertx)
    final options = DeploymentOptions.newInstance()
        .setWorker(true)
        .setMultiThreaded(true)
        .setInstances(Math.max(Runtime.currentRuntime.availableProcessors(), 4))
    getServices()?.each { Class c ->
      vertx.deployVerticle(c.name, options, listener)
    }
    latch.await() // Block until all Verticles are deployed and initialized
    vertx
  }

  private void registerDefaultCodecs(final Vertx vertx) {
    def eventBus = vertx.eventBus()
    eventBus.registerDefaultCodec(LinkedHashMap.class, LocalLinkedHashMapCodec.newInstance())
  }

  protected abstract List<Class<? extends Verticle>> getServices()
}
