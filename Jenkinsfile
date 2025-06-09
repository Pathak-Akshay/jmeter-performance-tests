pipeline {
    agent {
        label 'jmeter-agent'  // replace with your actual agent label
    }
    environment {
        JMETER_HOME = '/opt/apache-jmeter-5.6.3' // adjust path if different
    }
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Pathak-Akshay/jmeter-performance-tests.git'
            }
        }

        stage('Run JMeter Test') {
            steps {
                sh """
                    mkdir -p results
                    for file in jmeter/test-plans/*.jmx; do
                        name=\$(basename "\$file" .jmx)
                        result_file="results/\${name}_result.jtl"
                        report_dir="results/\${name}_report"
                        
                        ${JMETER_HOME}/bin/jmeter -n -t "\$file" -l "\$result_file"
                        ${JMETER_HOME}/bin/jmeter -g "\$result_file" -o "\$report_dir"
                    done
                """
            }
        }

        stage('Archive JTL Files') {
            steps {
                archiveArtifacts artifacts: 'results/*.jtl', fingerprint: true
            }
        }

        stage('Publish HTML Reports') {
            steps {
                script {
                    def testPlans = sh(
                        script: "ls jmeter/test-plans/*.jmx | xargs -n1 basename | sed 's/.jmx//g'",
                        returnStdout: true
                    ).trim().split('\n')

                    testPlans.each { name ->
                        publishHTML([
                            reportDir: "results/${name}_report",
                            reportFiles: 'index.html',
                            reportName: "JMeter Report - ${name}",
                            allowMissing: false,
                            keepAll: true,
                            alwaysLinkToLastBuild: true
                        ])
                    }
                }
            }
        }
    }
}
