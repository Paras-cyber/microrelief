pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build & Test') {
            steps {
                bat 'cd discovery-server && mvnw.cmd -B clean verify'
                bat 'cd config-server && mvnw.cmd -B clean verify'
                bat 'cd auth-service && mvnw.cmd -B clean verify'
                bat 'cd task-service && mvnw.cmd -B clean verify'
                bat 'cd ai-service && mvnw.cmd -B clean verify'
                bat 'cd api-gateway && mvnw.cmd -B clean verify'
            }
        }
        stage('Docker Build') {
            steps { bat 'docker compose build' }
        }
        stage('Deploy') {
            steps { bat 'docker compose up -d' }
        }
    }
    post {
        success { echo 'Pipeline succeeded' }
        failure { echo 'Pipeline failed' }
    }
}