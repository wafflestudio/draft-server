def applicationName = "draft-server"
def ecrName = "405906814034.dkr.ecr.ap-northeast-2.amazonaws.com"
def ecrRepoName = "draft"
def bucketName = "draft-build"
def environmentName = "production"

pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh "export AWS_REGION=ap-northeast-2"
        sh "docker build -t ${applicationName} ."
      }
    }

    stage('Upload') {
      steps {
        sh "zip ${applicationName}.zip Dockerrun.aws.json"
        sh "aws s3 cp ${applicationName}.zip s3://${bucketName}/${applicationName}/deploy.zip --region ap-northeast-2"
        sh "docker tag ${applicationName}:latest ${ecrName}/${ecrRepoName}/${applicationName}:latest"
        sh "aws ecr describe-repositories --repository-names ${ecrRepoName}/${applicationName} || aws ecr create-repository --repository-name ${ecrRepoName}/${applicationName}"
        sh "docker push ${ecrName}/${repoName}/${applicationName}:latest"
      }
    }
  }
}
