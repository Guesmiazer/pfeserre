pipeline {
    agent any

    stages {
        stage('checkout'){
            steps {
                checkout scm }
        }
        stage('git') {
            steps {
                // Cloner le référentiel depuis votre système de contrôle de version
                  git branch: 'main', url : 'https://github.com/Guesmiazer/pfeserre.git'
                  }
        }
        stage('Construction') {
            steps {
                // Exécuter votre processus de construction (par exemple, Maven, Gradle, etc.)
                sh 'mvn clean package'
            }
        }
        stage('Tests') {
            steps {
                // Exécuter vos tests unitaires ou tests d'intégration
                sh 'mvn test'
            }
        }
       stage('sonarqube') {
           steps {
           withSonarQubeEnv('sonarserver') {
                                      sh 'mvn sonar:sonar -Dsonar.java.binaries=target/classes'
           }
           }
       }
        //
      // stage('Déploiement') {
      //       steps {
      //           // Déployer votre application sur un serveur ou une plateforme spécifique
      //           sh 'mvn deploy'
      //       }
      //   }
    }
}
