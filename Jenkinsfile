pipeline {
    agent any

    stages {
        stage('copy Props') {
            steps {
                sh 'cp ~/settings.cfg.properties /home/ec2-user/.jenkins/workspace/DinoBackend/src/main/resources/Properties'
            }
        }
        stage('clean') {
            steps {
                sh 'mvn clean'
            }
        }
        
        stage('package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('deploy'){
        	steps{
        		sh 'cp /home/ec2-user/.jenkins/workspace/DinoBackend/target/P0Server.war /home/ec2-user/apache-tomcat-9.0.40/webapps'
        	}
        }
    }
}
