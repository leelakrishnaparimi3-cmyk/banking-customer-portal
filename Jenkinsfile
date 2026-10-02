pipeline {

    agent any

    environment {
        IMAGE_NAME = "customer-portal"
        CONTAINER_NAME = "customer-portal-test"
        HOST_PORT = "8081"
        CONTAINER_PORT = "8080"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/leelakrishnaparimi3-cmyk/banking-customer-portal.git',
                        credentialsId: 'github-credentials'
                    ]]
                ])
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:build-%BUILD_NUMBER% .'
            }
        }

        stage('Container Verification') {
            steps {

                bat 'docker rm -f %CONTAINER_NAME% >nul 2>&1 || exit /b 0'

                bat 'docker run -d --name %CONTAINER_NAME% -p %HOST_PORT%:%CONTAINER_PORT% %IMAGE_NAME%:build-%BUILD_NUMBER%'

                bat '''
                powershell -Command "$ok=$false; for($i=0; $i -lt 30; $i++){ try { $r=Invoke-WebRequest -UseBasicParsing http://localhost:%HOST_PORT%/health -TimeoutSec 3; if($r.StatusCode -eq 200 -and $r.Content.Trim() -eq 'UP'){ $ok=$true; break } } catch {} Start-Sleep -Seconds 2 }; if(-not $ok){ docker logs %CONTAINER_NAME%; exit 1 }"
                '''
            }
        }

        stage('Cleanup') {
            steps {
                bat 'docker rm -f %CONTAINER_NAME% >nul 2>&1 || exit /b 0'
            }
        }
    }

    post {
        always {
            bat 'docker rm -f %CONTAINER_NAME% >nul 2>&1 || exit /b 0'
        }
    }
}