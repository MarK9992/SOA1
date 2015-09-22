echo "This script assumes that you have an apache-servicemix-6.0.0 folder in your home directory"
echo "It also assumes thaht servicemix is already started"
mvn clean package
cp target/volley-1.0.jar ~/apache-servicemix-6.0.0/deploy/
