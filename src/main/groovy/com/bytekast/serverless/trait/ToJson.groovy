package com.bytekast.serverless.trait

import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
trait ToJson {
    String toJson(obj) {
        return JsonOutput.prettyPrint(JsonOutput.toJson(obj))
    }
}