include .env

build-java:
	mvn clean package -DskipTests=true

build-docker:
	docker compose build

build: build-java build-docker

test-java:
	mvn clean test jacoco:report-aggregate

test: test-java

sonarqube-docker:
	cd ../
	docker run --rm --network apps_default --platform linux/x86_64 -v "./question-and-answer-tool:/usr/src" \
	  sonarsource/sonar-scanner-cli \
	  -Dsonar.projectKey=question-and-answer-tool \
	  -Dsonar.sources=. \
	  -Dsonar.host.url=http://sonarqube:9000 \
	  -Dsonar.token=${SONARQUBE_TOKEN} \
	  -Dsonar.coverage.jacoco.xmlReportPaths=./apps/code-coverage-java/target/site/jacoco-aggregate/jacoco.xml

sonarqube:
	mvn sonar:sonar \
	  -Dsonar.host.url=http://localhost:9000 \
	  -Dsonar.token=${SONARQUBE_TOKEN} \
	  -Dsonar.login=${SONARQUBE_USER} -Dsonar.password=${SONARQUBE_PASSWORD}
