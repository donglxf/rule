pipeline {
  agent any
  stages {
    stage('sonar') {
        tools {
            maven 'M3'
          }
      steps {
       sh "mvn clean package sonar:sonar -Dsonar.host.url=http://172.16.200.111:9000 -Dsonar.branch=${env.BRANCH_NAME}" 
        //tool(type: 'sonarqube scanner', name: 'sonarScanner1')
      }
    }
  }
  post {
        failure { 
            emailext attachLog: true, body: '$DEFAULT_CONTENT',  recipientProviders: [[$class: 'RequesterRecipientProvider'], [$class: 'DevelopersRecipientProvider']], subject: '$DEFAULT_SUBJECT'
             }
      }

  
}
