.PHONY: init
init:
	mvn -N wrapper:wrapper

.PHONY: validate
validate:
	./mvnw validate

.PHONY: build
build:
	./mvnw clean install -f pom.xml

.PHONY: run
run:
	java -jar ./target/java-logger-0.0.1.jar
