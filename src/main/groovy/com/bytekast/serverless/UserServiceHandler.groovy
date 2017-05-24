package com.bytekast.serverless

import com.bytekast.serverless.model.Request
import com.bytekast.serverless.service.UserService
import com.bytekast.serverless.vertx.DefaultVertxHandler
import io.vertx.core.Verticle

class UserServiceHandler extends DefaultVertxHandler {

  @Override
  protected List<Class<? extends Verticle>> getServices() {
    [UserService.class]
  }

  @Override
  protected String resolveUniqueRoute(Map input) {
    new Request(input).httpRoute()
  }
}
