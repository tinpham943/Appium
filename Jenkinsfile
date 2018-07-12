pipeline {
    agent any 

    stages {
        stage('Build') { 
            steps { 
				echo 'Building...'
                withMaven(maven: 'maven_3_5_2')
				{
				   bat 'mvn clean compile'
				   /*bat 'mvn clean package'*/
				}
            }
        }
        stage('Test'){
            steps {
                echo 'Testing...'
                withMaven(maven: 'maven_3_5_2')
				{
				   bat 'mvn test'
				}
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
                /*withMaven(maven: 'maven_3_5_2')
				{
				   sh 'mvn deploy'
				}*/
            }
        }
    }
}