package com.bytekast.serverless.trait

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

trait JsonMapper {

  String toJson(obj) {
    return JsonOutput.prettyPrint(JsonOutput.toJson(obj))
  }

  Object fromJson(String json) {
    JsonSlurper.newInstance().parseText(json)
  }
}