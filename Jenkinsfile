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
                        name=$(basename "$file" .jmx)
                        ${JMETER_HOME}/bin/jmeter -n -t "$file" -l "results/${name}_result.jtl"
                    done
                """
            }
        }

        stage('Archive Results') {
            steps {
                archiveArtifacts artifacts: 'results/result.jtl', fingerprint: true
                publishHTML(target: [
                    reportDir: 'results/html-report',
                    reportFiles: 'index.html',
                    reportName: 'JMeter Test Report',
                    allowMissing: false,
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }
    }
}
