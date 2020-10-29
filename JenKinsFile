pipeline {
    agent any
    environment {
        SERVER_IP = "${env.GIT_BRANCH == "182.92.65.228"}"
    }

    stages {
        stage('Deploy') {
            steps {
                script {
                    withCredentials(
                        git branch: "master", CredentialsId: 'ce5a2f05-3707-4534-9cfa-82718a59273b', url: 'https://github.com/lvbing92/chintec-ikks.git'
                    ) {
                      sh """
                        SSH_OPTS="-i ${DEPLOY_KEY} -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null"
                        ssh \${SSH_OPTS} ${DEPLOY_KEY_USR}@${env.SERVER_IP} 'cd chintec-ikks/; docker run --rm -v \$(pwd):/data alpine:3.11 chmod -R 777 /data'
                        rsync -e "ssh \${SSH_OPTS}" -Pa --delete --delete-after --exclude ".git/" --exclude ".env" . ${DEPLOY_KEY_USR}@${env.SERVER_IP}:chintec-ikks/
                        ssh \${SSH_OPTS} ${DEPLOY_KEY_USR}@${env.SERVER_IP} 'cd chintec-ikks/; docker build --force-rm -q -t builder builder/; docker run --rm -v \$(pwd):/app -v maven-cache:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock builder'
                        ssh \${SSH_OPTS} ${DEPLOY_KEY_USR}@${env.SERVER_IP} "cd chintec-ikks/; docker-compose up -d"
                        ssh \${SSH_OPTS} ${DEPLOY_KEY_USR}@${env.SERVER_IP} "docker run --rm --network chintec-ikks_internal jeremygarigliet/waitfor -t 90 erp:80"
                        ssh \${SSH_OPTS} ${DEPLOY_KEY_USR}@${env.SERVER_IP} "cd chintec-ikks/; docker-compose kill -s HUP proxy"
                      """
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}