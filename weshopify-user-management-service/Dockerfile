FROM openjdk:17

ARG ARTIFACT_NAME=weshopify-user-management-service-0.0.1-SNAPSHOT.jar
ARG WORKING_DIR=/opt/user-mgmt-service
ARG SERVICE_PORT=5014

RUN mkdir ${WORKING_DIR}
WORKDIR ${WORKING_DIR}

COPY target/${ARTIFACT_NAME} .

EXPOSE ${SERVICE_PORT}

ENTRYPOINT [ "java","-jar","weshopify-user-management-service-0.0.1-SNAPSHOT.jar" ]