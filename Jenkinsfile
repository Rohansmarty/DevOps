pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'YOUR_DOCKERHUB_USERNAME/devops-java-app'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        DOCKER_LATEST = "${DOCKER_IMAGE}:latest"
        DOCKER_VERSIONED = "${DOCKER_IMAGE}:${DOCKER_TAG}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling java-app...'
                sh '''
                    mvn -q -f java-app/pom.xml -DskipTests compile
                '''
            }
        }

        stage('Run Unit Tests') {
            steps {
                echo 'Running unit tests...'
                sh '''
                    mvn -q -f java-app/pom.xml test
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
                echo 'Building Java application...'
                sh '''
                    mvn -q -f java-app/pom.xml -DskipTests clean package
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                sh '''
                    docker build -t ${DOCKER_VERSIONED} -t ${DOCKER_LATEST} java-app
                '''
            }
        }

        stage('Test Application') {
            steps {
                echo 'Testing application...'
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
                echo 'Pushing Docker image to registry...'
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
