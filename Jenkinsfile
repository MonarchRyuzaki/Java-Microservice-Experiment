pipeline {
    agent any

    environment {
        // We will define environment variables here later
        DOCKER_IMAGE = "coin-sentinel-build"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                // Jenkins does this automatically for "Pipeline" jobs,
                // but explicit checkout is good practice in complex flows.
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Compiling and Running Tests...'
                // We use the Maven wrapper (mvnw) to ensure consistency
                // 'sh' runs a shell command
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Images') {
            steps {
                echo 'Building Docker Images...'
                // Since we mapped the Docker socket in Terraform,
                // Jenkins can run 'docker build' just like your laptop can!
                sh 'docker build -t market-data:latest ./market-data-service'
                sh 'docker build -t alert-service:latest ./alert-service'
                sh 'docker build -t notification-service:latest ./notification-service'
            }
        }
    }

    post {
        always {
            // Clean up workspace to save disk space
            cleanWs()
        }
        success {
            echo 'Pipeline Succeeded! Ready for deployment.'
        }
        failure {
            echo 'Pipeline Failed. Check logs.'
        }
    }
}