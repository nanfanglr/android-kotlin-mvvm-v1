pipeline {
    agent any

    stages {
        stage('pull project') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/migrate_to_androidx']], extensions: [], userRemoteConfigs: [[credentialsId: '36bff5e1-b3cc-4e9a-8144-c71323d20b1f', url: 'git@github.com:nanfanglr/kotlin_mvvm.git']]])
            }
        }
        stage('build project') {
            steps {
                sh './gradlew clean :app:assembleDebug'
            }
        }
        stage('publish project'){
            steps{
                archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', followSymlinks: false
            }

        }
    }
}
