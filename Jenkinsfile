pipeline {
    agent any

    tools {
        maven 'Maven 3'   // matches the Maven installation name in Jenkins → Global Tool Config
        jdk   'JDK 17'    // matches the JDK installation name in Jenkins → Global Tool Config
    }

    environment {
        APP_NAME = 'employee-leave-management'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '📥 Cloning source code from Git...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Compiling the project...'
                sh 'mvn clean compile -q'
            }
        }

        stage('Unit Tests') {
            steps {
                echo '🧪 Running JUnit 5 tests...'
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results in Jenkins UI
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo '📦 Packaging application into JAR...'
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Code Quality Check') {
            steps {
                echo '🔍 Running checkstyle / static analysis...'
                sh 'mvn verify -DskipTests'
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline SUCCESS — ${APP_NAME} built and tested."
        }
        failure {
            echo "❌ Pipeline FAILED — check the logs above."
        }
        always {
            echo '🧹 Cleaning workspace...'
            cleanWs()
        }
    }
}
