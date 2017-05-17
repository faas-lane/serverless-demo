package com.bytekast.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.bytekast.serverless.trait.ToJson
import groovy.util.logging.Log4j
import org.codehaus.groovy.runtime.StackTraceUtils


@Log4j
class SlackServiceHandler implements ToJson {

  Map handler(Map input, Context context) {

    try {
      log.info "Slack request input: ${toJson(input)}"
      def payload = input?.body?.split('&')?.collectEntries {
        param -> param.split('=').collect { URLDecoder.decode(it, "UTF-8") }
      }
      log.info "Transformed slack request payload: ${toJson(payload)}"

      verifyRequest(payload?.token)

      def cli = new CliBuilder()
      def stringWriter = new StringWriter()
      cli.usage = '/serverless'
      cli.writer = new PrintWriter(stringWriter)
      cli.c(longOpt: 'command', args: 1, argName: 'name', 'command name')
      cli.usage()

      // TODO
      // def options = cli.parse(payload?.text?.split())

      if (payload?.text?.split()?.first() == 'help') {
        return [body: toJson([text: stringWriter.toString()])]
      } else {
        return [body: toJson([text: payload?.user_name])]
      }
    } catch (e) {
      log.warn e.message
      ['error': StackTraceUtils.extractRootCause(e).message]
    }
  }

  void verifyRequest(String token) {
    def slackCommandToken = System.getenv('SLACK_COMMAND_VERIFICATION_TOKEN')
    if (!slackCommandToken?.trim() || token?.trim() != slackCommandToken) {
      throw new Exception('Invalid or missing slack command token')
    }
  }
}
