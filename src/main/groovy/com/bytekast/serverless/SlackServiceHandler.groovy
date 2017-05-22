package com.bytekast.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.bytekast.serverless.service.RestaurantService
import com.bytekast.serverless.trait.SlackTemplate
import groovy.util.logging.Log4j
import org.codehaus.groovy.runtime.StackTraceUtils

@Log4j
class SlackServiceHandler implements SlackTemplate {

  private final CliBuilder commandParser
  private final StringWriter helpUsage

  SlackServiceHandler() {
    commandParser = new CliBuilder()
    commandParser.c(longOpt: 'cuisines', args: 1, argName: 'cuisine names', 'comma separated cuisines names')
    commandParser.r(longOpt: 'radius', args: 1, argName: 'miles', 'radius in miles')

    helpUsage = new StringWriter()
    commandParser.usage = '/lunch'
    commandParser.writer = new PrintWriter(helpUsage)
    commandParser.usage()
  }

  Map handler(Map input, Context context) {

    try {
      log.info "Slack request input: ${toJson(input)}"
      def payload = slackPayload(input)
      log.info "Transformed slack request payload: ${toJson(payload)}"

      // Authenticate Request
      verifyRequest(payload?.token)

      // Return Help Usage
      if (!payload?.text || payload?.text?.trim()?.startsWith('help')) {
        return [body: toJson([text: helpUsage.toString()])]
      }

      // Parse command
      def options = commandParser.parse(payload?.text?.split())

      // Call Restaurant Service
      final int BOULDER_CITY_ID = 11184
      final int DENVER_CITY_ID = 305
      def cuisines = options.c ? options.c.toString()?.trim() : ''
      def radius = options.r ? options.r.toInteger() : 5
      def restaurants = RestaurantService.instance.search(BOULDER_CITY_ID, cuisines, radius)

      // Pick random restaurant
      Collections.shuffle(restaurants)
      def randomRestaurant = restaurants.first()
      log.info randomRestaurant

      return [body: toJson([text: randomRestaurant])]

    } catch (e) {
      log.warn e.message
      ['error': StackTraceUtils.extractRootCause(e).message]
    }
  }
}
