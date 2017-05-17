package com.bytekast.serverless

import com.bytekast.serverless.service.UserService
import com.bytekast.serverless.vertx.DefaultVertxHandler
import io.vertx.core.Verticle

class UserServiceHandler extends DefaultVertxHandler {

  @Override
  protected List<Class<? extends Verticle>> getVerticles() {
    [UserService.class]
  }

  @Override
  protected String resolveUniqueRoute(Map input) {
    "${input.httpMethod}:${input.resource}"
  }
}
