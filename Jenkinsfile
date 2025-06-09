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
                git 'https://github.com/Pathak-Akshay/jmeter-performance-tests.git'
            }
        }

        stage('Run JMeter Test') {
            steps {
                sh """
                    mkdir -p results
                    ${JMETER_HOME}/bin/jmeter -n -t test-plans/RestfulBooker.jmx -l results/result.jtl -e -o results/html-report
                """
            }
        }

        stage('Archive Results') {
            steps {
                archiveArtifacts artifacts: 'results/result.jtl', fingerprint: true
                publishHTML([
                    reportDir: 'results/html-report',
                    reportFiles: 'index.html',
                    reportName: 'JMeter Test Report'
                ])
            }
        }
    }
}
