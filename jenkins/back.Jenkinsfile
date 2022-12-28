pipeline{
    agent {
        kubernetes{
            yamlFile 'jenkins/back-agent-pod.yaml'
        }
    }
    tools{
        gradle 'G7.5.1'
    }

    environment{
        dockerHubRegistry = 'uh2959/cucumber-back'
        gitToken = credentials('github-repo-access-token')
    }
    
    stages {
        stage('Start Notify'){
            steps{
                sh 'echo build start'
            }
            post{
                always{
                    slackSend(channel: "#back-end", token: "slack-token", color: "#0000FF",
                     message: ":bell: Back-End Build Started! - ${env.JOB_NAME} (${env.BUILD_NUMBER})")
                    
                    /*
                    slackSend(channel: "#infra", token: "slack-token", color: "#0000FF",
                     message: "Build Started! - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)")
                    */
                }
            }
        }

        stage('Checkout'){
            steps{
                git branch: 'main',
                    url: 'https://github.com/Cucumber-web/movie-web-project.git',
                    credentialsId: 'github-repo-access-token'
            }
            post{
                failure{
                    echo 'Repository clone failure!'

                    slackSend(channel: "#back-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Back-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
                }
                success{
                    echo 'Repository clone success!'
                }
            }
        }
        
        stage('Gradle Jar Build'){
            steps{
                dir('back'){
                    sh '''
                        chmod +x gradlew
                        ./gradlew build --exclude-task test
                    '''
                }
            }
            post{
                failure{
                    echo 'Gradle jar build failure!'

                    slackSend(channel: "#back-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Back-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
                }
                success{
                    echo 'Gradle jar build success!'
                }
            }
        }

        stage('Docker Image Build'){
            steps{
                container('docker'){
                    /*
                    sh '''
                        pwd
                        ls -al
                        ls -al back
                        ls -al back/build
                        ls -al back/build/libs
                    '''
                    */
                    sh "cp back/build/libs/back-0.0.1-SNAPSHOT.jar ./"
                    sh "cp back/Dockerfile ./"
                    sh "docker build . -t ${dockerHubRegistry}:${currentBuild.number}"
                    sh "docker build . -t ${dockerHubRegistry}:latest"
                }
            }
            post{
                failure{
                    echo 'Docker image build failure!'

                    slackSend(channel: "#back-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Back-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
                }
                success{
                    echo 'Docker image build success!'
                }
            }
        }
        
        stage('Docker Image Push'){
            steps{
                container('docker'){
                    withDockerRegistry([credentialsId: 'docker-access-key', url: "" ]){
                        sh "docker push ${dockerHubRegistry}:${currentBuild.number}"
                        sh "docker push ${dockerHubRegistry}:latest"
                        
                        /* Wait Uploading */
                        sleep 10

                        sh "docker rmi ${dockerHubRegistry}:${currentBuild.number}"
                        sh "docker rmi ${dockerHubRegistry}:latest"
                    }
                }
            }
            post{
                failure{
                    echo 'Docker Image Push failure!'

                    slackSend(channel: "#back-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Back-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
                }
                success{
                    echo 'Docker Image Push Success!'
                }
            }
        }

        stage('Kubernetes Manifest Update'){
            steps{
                git branch: 'main',
                    url: 'https://github.com/Cucumber-web/movie-web-project-k8s.git',
                    credentialsId: 'github-repo-access-token'
                
                sh '''
                    git config --global user.email "uh9222959@gmail.com"
                    git config --global user.name "UhyeongJo"
                '''
                sh "git pull origin main"
                sh "sed -i 's/cucumber-back:.*\$/cucumber-back:${currentBuild.number}/g' back-deployment.yaml"
                sh "git add back-deployment.yaml"
                sh "git commit -m '[Update] back-end ${currentBuild.number} image versioning'"
                
                sshagent(credentials: ['41e673ba-49ab-4968-823b-6f33640c5296']){
                    sh 'git remote set-url origin https://${gitToken}@github.com/Cucumber-web/movie-web-project-k8s.git'
                    sh "git push origin main"
                }
            }
            post{
                failure{
                    echo 'Kubernetes Manifest Update failure!'

                    slackSend(channel: "#back-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Back-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
                }
                success{
                    echo 'Kubernetes Manifest Update Success!'

                    slackSend(channel: "#back-end", token: "slack-token", color: "good",
                     message: ":white_check_mark: Back-End Build Success! - ${env.JOB_NAME} (${env.BUILD_NUMBER})")
                }
            }
        }
        
    }
}
