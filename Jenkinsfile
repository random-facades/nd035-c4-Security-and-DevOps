

pipeline {
    agent any
    stages {
        stage('Build') { 
            steps {
                dir('starter_code') {
                  sh 'mvn -B -DskipTests clean package' 
                }
            }
        }
    }
}

