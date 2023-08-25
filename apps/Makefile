include .env

build-java:
	mvn clean install -DskipTests=true

build-docker:
	docker compose build

build: build-java build-docker

test-java:
	mvn clean test jacoco:report-aggregate

test: test-java

graph:
	mvn com.github.ferstl:depgraph-maven-plugin:4.0.2:aggregate -DgraphFormat=puml -DcreateImage=false -DreduceEdges=false -Dscope=compile "-Dincludes=com.qa.*:*"
	mv target/dependency-graph.puml ../design/dependency-graph/java.puml
	cat ../design/dependency-graph/java.puml | docker run --rm -i think/plantuml -tsvg > ../design/dependency-graph/java.svg

migrate:
	@read -p "This command execute DB migration but migration should be done by applications. This command is provided only for CI process. Are you realy want to migrate? [y,N]:" ans; \
	if [ "$$ans" = y ]; then  \
	  mvn flyway:migrate -pl qa-tool; \
	  mvn flyway:migrate -pl tool-manager; \
	fi

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
