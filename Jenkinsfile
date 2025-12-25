pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'your-dockerhub-username/devops-app'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        DOCKER_LATEST = "${DOCKER_IMAGE}:latest"
        DOCKER_VERSIONED = "${DOCKER_IMAGE}:${DOCKER_TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'ðŸ”„ Checking out source code...'
                checkout scm
            }
        }

        stage('Code Quality Check') {
            steps {
                echo 'ðŸ” Running code quality checks...'
                sh '''
                    cd java-app
                    # Run Maven compile to check for compilation errors
                    ./mvnw compile

                    # Check if Java files exist
                    find src -name "*.java" | head -5
                '''
            }
        }

        stage('Run Unit Tests') {
            steps {
                echo 'ðŸ§ª Running unit tests...'
                sh '''
                    cd java-app
                    ./mvnw test
                '''
            }
            post {
                always {
                    junit 'java-app/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build Application') {
            steps {
                echo 'ðŸ”¨ Building Java application...'
                sh '''
                    cd java-app
                    ./mvnw clean package -DskipTests
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'ðŸ—ï¸ Building Docker image...'
                sh '''
                    cd java-app
                    docker build -t ${DOCKER_VERSIONED} -t ${DOCKER_LATEST} .
                '''
            }
        }

        stage('Test Application') {
            steps {
                echo 'ðŸ§ª Testing application...'
                sh '''
                    # Start container for testing
                    docker run -d --name test-app -p 8081:8080 ${DOCKER_LATEST}

                    # Wait for app to start (Spring Boot takes longer)
                    sleep 30

                    # Test actuator health endpoint
                    curl -f http://localhost:8081/actuator/health || (docker logs test-app && exit 1)

                    # Cleanup
                    docker stop test-app
                    docker rm test-app
                '''
            }
        }

        stage('Push Docker Image') {
            steps {
                echo 'ðŸ“¤ Pushing Docker image to registry...'
                withDockerRegistry([credentialsId: 'dockerhub-creds', url: '']) {
                    sh '''
                        docker push ${DOCKER_VERSIONED}
                        docker push ${DOCKER_LATEST}
                    '''
                }
            }
        }
    }
}
