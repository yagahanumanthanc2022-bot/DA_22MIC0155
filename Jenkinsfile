
pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 25'
    }

    environment {
        APP_NAME = 'employee-leave-management'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning source code from Git...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile -q'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Code Quality Check') {
            steps {
                bat 'mvn verify -DskipTests'
            }
        }
    }

    post {
        success {
            echo "PIPELINE SUCCESS - ${APP_NAME} built and tested."
        }
        failure {
            echo 'PIPELINE FAILED - Check the logs above.'
        }
        always {
            echo 'Pipeline execution completed.'
        }
    }
}
