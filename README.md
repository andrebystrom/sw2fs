# sw2fs
Simple portable HTTP Server built with Java 11.
# Clone, build and run
clone the repo.

    git clone git@github.com:andrebystrom/sw2fs.git

cd into the directory

    cd cd ./sw2fs/

compile with maven

    mvn compile

cd into build dir

    cd ./target/classes/

run the program

    java com.andrebystrom.sw2fs.Sw2fs </path/to/web/root/dir/>

The webserver is now available on http://localhost:8080