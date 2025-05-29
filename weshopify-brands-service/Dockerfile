FROM openjdk:17

ARG WORKING_DIR=/opt/brand-service
ARG SERVICE_PORT=5017
ARG ARTIFACT_NAME=weshopify-brands-service-0.0.1-SNAPSHOT.jar

RUN mkdir ${WORKING_DIR}
WORKDIR ${WORKING_DIR}

COPY target/${ARTIFACT_NAME} .

EXPOSE ${SERVICE_PORT}

ENTRYPOINT [ "java","-jar","weshopify-brands-service-0.0.1-SNAPSHOT.jar" ]