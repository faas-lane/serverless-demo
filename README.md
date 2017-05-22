[![GitPitch](https://gitpitch.com/assets/badge.svg)](https://gitpitch.com/bytekast/serverless-demo/master?grs=github&t=white)

# serverless-demo

AWS Lambda and Serverless Framework Demo

### Local Development Prerequisites

> Install Gradle

`brew install gradle`
<br/>


> Install NPM and the Serverless CLI tools

`brew install node`

`npm install serverless -g`
<br/>


> Set up AWS credentials

Add your AWS credentials to the `~/.aws/credentials` file:

```
[default]
aws_access_key_id = XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
aws_secret_access_key = XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

> Set your default AWS region

Add default region to `~/.aws/config` file:

```
[default]
region = us-east-1

```

---

### Project Settings

- Rename `serverless.secrets-sample.yml` to `serverless-secrets.yml`
- Update secret values in `serverless-secrets.yml`

---

### Build and Deploy

> Build the project

`./gradlew clean build`

> Deploy to Serverless

`serverless deploy`

---
