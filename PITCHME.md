
## Serverless Microservices

![LOGO](http://share.rowellbelen.com/jZtq9/5oYz5U1L+)

An introduction to **AWS Lambda** and the **Serverless Framework**

---

## About Me

**Rowell Belen** / Software Engineer @ **Stedi**

- LinkedIn: https://linkedin.com/in/rowellbelen/
- GitHub: http://github.com/bytekast
- Twitter: [@bytekast](https://twitter.com/bytekast)

---

## What is Serverless Computing?

Cloud computing model that allows you to build and run applications and services without thinking about servers. The platform takes care of everything required to run and scale your code with high availability. <!-- .element: class="fragment" -->

---

## Evolution of Serverless

// TODO

---

## What is AWS Lambda?

![LOGO](http://share.rowellbelen.com/ywLZQ/3kOZpb1t+)

Amazon's Serverless compute platform for stateless code execution in response to events <!-- .element: class="fragment" -->

--- 

## Other Serverless Platform Providers
- [Microsoft Azure Functions](https://azure.microsoft.com/en-us/services/functions/)
- [Iron.io](https://www.iron.io)
- [Google Cloud Functions](https://cloud.google.com/functions/docs/)
- [IBM Open Whisk](https://developer.ibm.com/openwhisk/)
- [WebTask.io](https://webtask.io)
- [PubNub BLOCKS](https://www.pubnub.com/products/blocks/)

---


## Why AWS Lambda?
- Managed Infrastructure
- Growing Relevance
- Streamlined AWS Services Integrations
- Built-in versioning
- // TODO

---

## How Is Lambda Used?
- Stream data processing
- Easy & scalable APIs
- Photo processing
- Web applications
- Scheduled background jobs

---

## How are Lambda Functions Triggered?

- Event-driven (SNS, S3, API-Gateway, CloudWatch, Amazon Echo Skills, IoT, etc.)
- Direct Invocation (CLI, SDK, etc.)
- Scheduled Interval

---

## Supported Languages

- Node.js (Javascript)
- Python
- JVM ( Java, Scala, Groovy, Kotlin, Clojure, etc. )
- C#

---

## Pricing

| Memory (MB) | Free tier seconds per month | Price per 100ms ($) |
| :---: | :---: | :---: |
|
| 128 | 3,200,000 | 0.000000208 |
| 256 | 1,600,000 | 0.000000417 |
| 512 | 800,000 | 0.000000834 |
| 1024 | 400,000 | 0.000001667 |
| 1536 | 266,667 | 0.000002501 |

---

## Benefits
- Cost and Utilization
- Managed machines
- Service Integrations
- Scaling

---

## Drawbacks

- Fewer language support
- Debugging
- Less Control (instrumentation, operating systems, etc.)
- Cutting edge quirks

---

## What is the Serverless Framework?

![LOGO](http://share.rowellbelen.com/ZLeCL/5Sbuaaym+)

Development toolkit for building, managing and deploying Serverless applications and resources <!-- .element: class="fragment" -->

---

## Serverless Framework CLI

```bash

npm install serverless -g

mkdir my-api && cd my-api

serverless create --template aws-groovy-gradle

serverless deploy --stage dev

serverless invoke --function my-function --log

```

---

## Serverless vs Traditional Architecture

- Lower barriers to entry |
- Reduced cost            |
- Increased distribution  |

---







# Cloud Scale DevOps

- Immutable Infrastructure
- Infrastructure needs to be reproducible from a definition under source control


---

## Immutable Infrastructure

- Servers (or application runtimes) are built for every version of your code
- Servers are ephemeral entities that are easily created and destroyed 
- Ability to roll back a bad deployment
- Horizontal Scaling
 

### Horizontal Scaling

Scale Out
- More servers of the current version should be able to take traffic when needed
 
Scale In
- Servers can then be removed from traffic rotation and destroyed as traffic decreases
 
---
 
## How do we build an immutable infrastructure?

---
 
![LOGO](http://share.rowellbelen.com/VKZZI/3QQBgjDs+)

---

## Designing Environments

- Each environment is a logically isolated configuration for your infrastructure
- Environments should not be connected or otherwise intercommunicate
- Promote immutable application artifacts from the lowest environment to the highest
- Environments should be identical in their form and function.
- Environment configuration should be reproducible and placed under source control


---

## Enough chit-chat, let's get into the details

---

# Demo slack commands

// TODO

---

#### Lambda Function - Send Events/Metrics to Datadog

```groovy
class DatadogHandler implements AmazonSNSTemplate {

  final rest = new RESTClient('https://app.datadoghq.com/')

  void handleRequest(Map input, Context context) {

    def jsonObj = snsJsonBody(input) // Extract Payload
    def type = jsonObj?.'alert_type' ? 'events' : 'series'
    def query = ['api_key': System.getenv('DATADOG_API_KEY')]
    rest.post(
        path: "api/v1/${type}",
        body: jsonObj,
        requestContentType: ContentType.JSON,
        query: query)
  }
}
```

---

#### Lambda Function - Custom JWT Token Authorizer

```groovy
class AuthorizerHandler {

  Map handleRequest(Map input, Context context) {

    // extract token and resource from input
    def token = input.authorizationToken?.minus('Bearer')?.trim()
    def resource = input.methodArn

    // authenticate
    def claims = verifyToken(token)

    // return policy
    createPolicy(claims, resource)
  }
}
```

+++

##### Verify Token

```groovy
Map verifyToken(final String token) {
  def algorithm = Algorithm.HMAC256(AUTH0_CLIENT_SECRET)
  def verifier = JWT.require(algorithm).build()
  def decodedJwt = verifier.verify(token)
  decodedJwt.getClaims()
}
```

+++

##### Create Security Policy with Claims

```groovy
Map createPolicy(final Map claims, final String resource) {
  [
      principalId   : claims?.user_id,
      policyDocument: [
          Version  : '2012-10-17',
          Statement: [
              Action  : 'execute-api:Invoke',
              Effect  : claims ? 'Allow' : 'Deny',
              Resource: resource
          ]
      ],
      context       : claims
  ]
}
```

---

## Logging

Inspecting CloudWatch Logs is PAINFUL!!!

---

## Solution - Centralized Logging

// TODO - Cloudwatch + Elastic Search Service + Kibana

---

## Metrics

// TODO - CloudWatch Metrics + Datadog

- Better Dashboards
- Custom Metrics and Events


## Things to Consider

- Functions are recycled about every 4 hours
- Container instances idle for about 5 minutes are destroyed
- Cold starts can cause delay in response times
- 50 MB max deployment package size
- 5 minute running time limit
- 1000 default concurrent function executions


## Tips and Tricks
- Use recursion for long running tasks
