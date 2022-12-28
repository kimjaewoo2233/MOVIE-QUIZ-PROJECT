pipeline{
    agent {
        kubernetes{
            yamlFile 'jenkins/front-agent-pod.yaml'
        }
    }

    tools{
        nodejs 'N16.17.0'
    }

    environment{
        dockerHubRegistry = 'uh2959/cucumber-front'
        gitToken = credentials('github-repo-access-token')
    }
    
    stages {
        stage('Start Notify'){
            steps{
                sh 'echo build start'
            }
            post{
                always{
                    slackSend(channel: "#front-end", token: "slack-token", color: "#0000FF",
                     message: ":bell: Front-End Build Started! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
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

                    slackSend(channel: "#front-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Front-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
                }
                success{
                    echo 'Repository clone success!'
                }
            }
        }
        
        stage('React Build'){
            steps{
                dir('front'){
                    sh '''
                        npm install
                        npm run build
                        ls -al
                        ls -al build
                    '''
                }
            }
            post{
                failure{
                    echo 'React build failure!'

                    slackSend(channel: "#front-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Front-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
                }
                success{
                    echo 'React build success!'
                }
            }
        }
        
        stage('Docker Image Build'){
            steps{
                container('docker'){
                    dir('front'){
                        sh "docker build . -t ${dockerHubRegistry}:${currentBuild.number}"
                        sh "docker build . -t ${dockerHubRegistry}:latest"
                    }
                }
            }
            post{
                failure{
                    echo 'Docker image build failure!'

                    slackSend(channel: "#front-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Front-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
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

                    slackSend(channel: "#front-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Front-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
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
                sleep 30

                sh "git pull origin main"
                sh "sed -i 's/cucumber-front:.*\$/cucumber-front:${currentBuild.number}/g' front-deployment.yaml"
                sh "git add front-deployment.yaml"
                sh "git commit -m '[Update] front-end ${currentBuild.number} image versioning'"
                
                sshagent(credentials: ['41e673ba-49ab-4968-823b-6f33640c5296']){
                    sh 'git remote set-url origin https://${gitToken}@github.com/Cucumber-web/movie-web-project-k8s.git'
                    sh "git push origin main"
                }
            }
            post{
                failure{
                    echo 'Kubernetes Manifest Update failure!'

                    slackSend(channel: "#front-end", token: "slack-token", color: "danger",
                     message: ":rotating_light: Front-End Build Failed! - ${env.JOB_NAME} (${env.BUILD_NUMBER})\n<${env.BUILD_URL}>")
                }
                success{
                    echo 'Kubernetes Manifest Update Success!'

                    slackSend(channel: "#front-end", token: "slack-token", color: "good",
                     message: ":white_check_mark: Front-End Build Success! - ${env.JOB_NAME} ${env.BUILD_NUMBER})")
                }
            }
        }
    }
}
