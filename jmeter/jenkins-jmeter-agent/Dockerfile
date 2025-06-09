FROM ubuntu:22.04

# Install required packages
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk openssh-server sudo wget unzip && \
    mkdir -p /var/run/sshd
	
# Install Git
RUN apt-get update && apt-get install -y git

# Add Jenkins user
RUN useradd -ms /bin/bash jenkins && echo "jenkins:jenkins" | chpasswd && adduser jenkins sudo

# SSH config
RUN echo "PermitRootLogin yes" >> /etc/ssh/sshd_config && \
    echo "PasswordAuthentication yes" >> /etc/ssh/sshd_config && \
    ssh-keygen -A

# Install JMeter
RUN wget https://downloads.apache.org/jmeter/binaries/apache-jmeter-5.6.3.tgz && \
    tar -xvzf apache-jmeter-5.6.3.tgz -C /opt && \
    ln -s /opt/apache-jmeter-5.6.3/bin/jmeter /usr/bin/jmeter && \
    rm apache-jmeter-5.6.3.tgz

# Set JAVA_HOME and update PATH
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]
