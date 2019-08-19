pipeline {
  agent any
  stages {
    stage('build') {
      parallel {
        stage('order-service') {
          steps {
            sh 'mvn -B -DskipTests -f order-service clean package'
          }
        }
        stage('resource-service') {
          steps {
            sh 'mvn -B -DskipTests -f resource-service clean package'
          }
        }
        stage('system-service') {
          steps {
            sh 'mvn -B -DskipTests -f systemr-service clean package'
          }
        }
      }
    }
    stage('test') {
      steps {
        echo 'unit-test'
      }
    }
    stage('deploy') {
      steps {
        echo 'deployment'
      }
    }
  }
}