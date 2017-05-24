package com.bytekast.serverless.model

import groovy.transform.ToString

@ToString(includePackage = false)
class Request {

  private final Map input

  Request(final Map input) {
    this.input = input
  }

  String resourcePath() {
    input?.resource ?: 'unknown'
  }

  String httpMethod() {
    input?.httpMethod ?: 'unknown'
  }

  String queryString(String name) {
    input?.queryStringParameters?."${name}"?.trim()
  }

  String pathParameter(String name) {
    input?.pathParameters?."${name}"?.trim()
  }

  String httpRoute() {
    "${httpMethod()}:${resourcePath()}"
  }
}