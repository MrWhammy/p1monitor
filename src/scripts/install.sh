useradd -m -d /opt/p1processor/ p1processor
usermod -a -G dialout p1processor
tar -xzf ${project.artifactId}-${project.version}.tar.gz -C /opt/p1processor
cp p1processor.service /etc/systemd/system
chmod