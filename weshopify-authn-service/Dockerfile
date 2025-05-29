FROM openjdk:17

ARG WORKING_DIR=/opt/authn-service
ARG SERVICE_PORT=5022
ARG SERVICE_NAME=weshopify-authn-service-0.0.1-SNAPSHOT.jar

RUN mkdir ${WORKING_DIR}

WORKDIR ${WORKING_DIR}

COPY target/${SERVICE_NAME} .

EXPOSE ${SERVICE_PORT}

ENTRYPOINT [ "java","-jar","weshopify-authn-service-0.0.1-SNAPSHOT.jar" ]