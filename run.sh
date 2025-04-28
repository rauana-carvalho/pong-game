touch run.sh
chmod +x run.sh

set -e
find src -name '*.java' | xargs javac -d bin

java -cp bin:resources src.JogoPong