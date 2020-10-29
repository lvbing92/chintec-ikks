pipeline {
    agent any
    environment {
        SERVER_IP = "${env.GIT_BRANCH == "182.92.65.228"}"
    }
    tools{
        maven  "maven"
    }
    stages {
        stage('pull') {
                steps {
                    git branch: "master", credentialsId: 'ce5a2f05-3707-4534-9cfa-82718a59273b', url: 'https://github.com/lvbing92/chintec-ikks.git'

                }
         }
        stage('mvn') {
            steps {
                      sh "mvn clean package -Dmaven.test.skip=true"
            }
        }
         stage('docker') {
            steps {
                      sh encoding : 'UTF-8',script: 'chmod 777 dockersh.sh'
                      sh encoding : 'UTF-8',script: './dockersh.sh chinte-ikks latest develop'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}