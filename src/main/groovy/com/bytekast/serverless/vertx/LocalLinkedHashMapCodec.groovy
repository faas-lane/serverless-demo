package com.bytekast.serverless.vertx

import groovy.transform.CompileStatic
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec

@CompileStatic
class LocalLinkedHashMapCodec implements MessageCodec<LinkedHashMap, LinkedHashMap> {

  @Override
  void encodeToWire(Buffer buffer, LinkedHashMap map) {
    // Not applicable - only used locally (same JVM)
  }

  @Override
  LinkedHashMap decodeFromWire(int pos, Buffer buffer) {
    return null // Not applicable - only used locally (same JVM)
  }

  @Override
  LinkedHashMap transform(LinkedHashMap map) {
    return map
  }

  @Override
  String name() {
    return this.getClass().getSimpleName()
  }

  @Override
  byte systemCodecID() {
    return -1
  }
}
