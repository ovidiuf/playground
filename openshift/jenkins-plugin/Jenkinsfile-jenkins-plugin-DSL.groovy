node {

    stage("build") {

        echo "build starting ..."

        openshiftBuild bldCfg: 's2i'

        openshiftVerifyBuild bldCfg: 's2i'

        echo "build done"
    }

    stage ("deploy") {

        echo "deployment starting ..."

        openshiftDeploy depCfg: 'test-app'

        openshiftVerifyDeployment depCfg: 'test-app', replicaCount: '1'

        echo "deployment done"
    }

    stage ("verify") {

        echo "service verification starting ..."

        openshiftVerifyService svcName: 'test-app'

        echo "service verification done"
    }
}
