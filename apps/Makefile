include .env

build-java:
	mvn clean package -DskipTests=true

build-docker:
	docker compose build

build: build-java build-docker

test-java:
	mvn clean test jacoco:report-aggregate

test: test-java

# sonarqube-docker:
# 	cd ../
# 	docker run --rm --network apps_default --platform linux/x86_64 -v "./question-and-answer-tool:/usr/src" \
# 	  sonarsource/sonar-scanner-cli \
# 	  -Dsonar.projectKey=question-and-answer-tool \
# 	  -Dsonar.sources=. \
# 	  -Dsonar.host.url=http://sonarqube:9000 \
# 	  -Dsonar.token=${SONARQUBE_TOKEN} \
# 	  -Dsonar.coverage.jacoco.xmlReportPaths=./apps/code-coverage-java/target/site/jacoco-aggregate/jacoco.xml

sonarqube: test
	mvn sonar:sonar \
	  -Dsonar.host.url=http://localhost:9000 \
	  -Dsonar.token=${SONARQUBE_LOCAL_TOKEN} \
	  -Dsonar.login=${SONARQUBE_LOCAL_USER} -Dsonar.password=${SONARQUBE_LOCAL_PASSWORD}

sonarcloud: test
	mvn sonar:sonar \
	  -Dsonar.host.url=https://sonarcloud.io \
	  -Dsonar.token=${SONAR_TOKEN} \
	  -Dsonar.organization=sueharae4 \
	  -Dsonar.projectKey=sUeharaE4_question-and-answer-tool \
	  -Dsonar.coverage.jacoco.xmlReportPaths=$(CURDIR)/code-coverage-java/target/site/jacoco-aggregate/jacoco.xml
