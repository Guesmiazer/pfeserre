pipeline {
    agent any

    tools {
        jdk 'Java 17'  // Nom de la configuration JDK que vous avez ajoutée dans Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Cloner le référentiel depuis votre système de contrôle de version
                checkout scm
            }
        }
        stage('Git') {
            steps {
                // Vérifier que le référentiel est bien clonné à la branche spécifiée
                git branch: 'main', url: 'https://github.com/Guesmiazer/pfeserre.git'
            }
        }
        stage('Build') {
            steps {
                // Exécuter le processus de construction Maven
                sh 'mvn clean package'
            }
        }
        stage('Tests') {
            steps {
                // Exécuter les tests unitaires ou d'intégration
                sh 'mvn test'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                // Analyse de SonarQube
                withSonarQubeEnv('sonarserver') {
                    sh 'mvn sonar:sonar -Dsonar.java.binaries=target/classes'
                }
            }
        }
        // Stage optionnel pour le déploiement
        // stage('Deploy') {
        //     steps {
        //         // Déployer votre application sur un serveur ou une plateforme spécifique
        //         sh 'mvn deploy'
        //     }
        // }
    }
    post {
        success {
            script {
                emailext(
                    subject: "Pipeline Succeeded: ${currentBuild.fullDisplayName}",
                    body: "The pipeline has succeeded. You can view the results at ${BUILD_URL}",
                    to: 'azer1.guesmi@gmail.com',
                    attachLog: true
                )
            }
        }
        failure {
            script {
                emailext(
                    subject: "Pipeline Failed: ${currentBuild.fullDisplayName}",
                    body: "The pipeline has failed. Please check the console output for details.",
                    to: 'azer1.guesmi@gmail.com',
                    attachLog: true
                )
            }
        }
    }
}
