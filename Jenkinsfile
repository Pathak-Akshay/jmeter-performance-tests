pipeline {
    agent {
        label 'jmeter-agent'  
    }
    environment {
        JMETER_HOME = '/opt/apache-jmeter-5.6.3' 
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
                
                        echo "Running JMeter test for: \$file"
                        ${JMETER_HOME}/bin/jmeter -n -t "\$file" -l "\$result_file -Jjmeter.save.saveservice.output_format=xml"

                        echo "Checking content of \$result_file:"
                        cat "\$result_file"

                        if grep -q '<sample' "\$result_file"; then
                           echo "Generating HTML report for: \$file"
                           ${JMETER_HOME}/bin/jmeter -g "\$result_file" -o "\$report_dir"
                        else
                           echo "WARNING: No valid samples in \$result_file, skipping report generation."
                         fi
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
