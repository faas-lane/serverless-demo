package com.bytekast.serverless

import com.amazonaws.services.lambda.runtime.Context
import groovy.util.logging.Log4j

@Log4j
class Handler {

  Map handleRequest(Map<String, Object> input, Context context) {
    // do something
    [:]
  }
}
