FROM openjdk:17

ARG WORKING_DIR=/opt/category-service
ARG SERVER_PORT=5016
ARG ARTIFACT_NAME=weshopify-category-service-0.0.1-SNAPSHOT.jar

RUN mkdir ${WORKING_DIR}
WORKDIR ${WORKING_DIR}

COPY target/${ARTIFACT_NAME} .

EXPOSE  ${SERVER_PORT}

ENTRYPOINT [ "java","-jar","weshopify-category-service-0.0.1-SNAPSHOT.jar" ]