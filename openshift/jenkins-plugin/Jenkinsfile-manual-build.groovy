try {

    timeout(time: 20, unit: 'MINUTES') {

        def project=""

        node {

            stage("initialize") {

                echo "initializing ..."

                project = env.PROJECT_NAME
            }
        }

        echo "project: ${project}"

        node("maven") {

            stage("checkout") {

                echo "checking out from ${GIT_SOURCE_URL} ..."

                git url: "${GIT_SOURCE_URL}", branch: "${GIT_SOURCE_REF}"

                echo "check out ok"
            }

            stage("build maven") {

                echo "building maven artifacts ..."

                sh "mvn clean package -Popenshift"

                echo "build ok"

                stash name:"war", includes:"target/ROOT.war"
            }
        }

        node {

            stage("build image") {

                echo "building image ..."

                unstash name:"war"

                sh "oc start-build ${appName}-docker --from-file=target/ROOT.war -n ${project}"

                openshiftVerifyBuild bldCfg: "${appName}-docker", namespace: project, waitTime: '20', waitUnit: 'min'
            }

            stage("Deploy") {

                openshiftDeploy deploymentConfig: appName, namespace: project
            }
        }
    }
}
catch (err) {

    echo "build failure: ${err}"
    currentBuild.result = 'FAILURE'
    throw err
}