pipeline {
        environment{
            registry = 'keoffor/project-2'
            dockerHubCreds = 'Docker_hub'
            dockerImage = ''
            deploymentFile = 'k8s/api1.yml'
        }
      agent any
    stages {
        stage('Test') {
            when{
            branch 'Features'
            }
          steps {
            sh 'ls $WORKSPACE '
            dir("project2FrontEnd") {
            sh 'echo "Hello World"'
              withMaven {
                sh 'mvn test'
              }
            }
            }
          }
          stage('Build') {
               when {
                   branch 'main'
               }
               steps {
                    sh 'ls $WORKSPACE '
                            dir("project2FrontEnd") {
                            sh 'echo "Hello World"'
                   withMaven {
                       sh 'mvn clean package -DskipTests'
              }
            }
          }
        }
        stage('Docker Build') {
                   when {
                       branch 'main'
                   }
                   steps {
                        dir("project2FrontEnd") {
                       script {
                           echo "$registry:$currentBuild.number"
                           dockerImage = docker.build "$registry"
                       }
                   }
               }
             }
             stage('Docker Deliver') {
                     when {
                         branch 'main'
                     }
                     steps {
                             dir("project2FrontEnd") {
                         script {
                             docker.withRegistry('', dockerHubCreds) {
                                 dockerImage.push("$currentBuild.number")
                                 dockerImage.push("latest")

                  }
               }

            }
          }
        }
           stage('Deploy to GKE') {
                   when {
                       branch 'main'
                   }
                   steps{
                        echo "build deployment " + deploymentFile
                        dir("project2FrontEnd") {

                         sh 'sed -i "s/%TAG%/$BUILD_NUMBER/g" ./k8s/api1.yml'
                         sh 'cat ./k8s/api1.yml'
                       step([$class: 'KubernetesEngineBuilder',
                           projectId: 'macro-key-339512',
                           clusterName: 'macro-key-339512-gke',
                           zone: 'us-central1',
                           manifestPattern: 'k8s/',
                           credentialsId: 'macro-key-339512',
                           verifyDeployments: true
                       ])
          }
      }
    }
  }
}