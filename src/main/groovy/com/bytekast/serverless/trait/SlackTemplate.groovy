package com.bytekast.serverless.trait

trait SlackTemplate extends ToJson {

  Map slackPayload(Map input) {
    input?.body?.split('&')?.collectEntries {
      param -> param.split('=').collect { URLDecoder.decode(it, "UTF-8") }
    }
  }

  void verifyRequest(String token) {
    def slackCommandToken = System.getenv('SLACK_COMMAND_VERIFICATION_TOKEN')
    if (!slackCommandToken?.trim() || token?.trim() != slackCommandToken) {
      throw new Exception('Invalid or missing slack command token')
    }
  }
}