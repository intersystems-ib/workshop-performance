ARG IMAGE=containers.intersystems.com/intersystems/iris-community:2023.1.0.229.0
FROM $IMAGE

USER root

WORKDIR /opt/irisapp
RUN chown -R irisowner:irisowner /opt/irisapp

RUN echo '' > /dump_Errors.log
RUN chmod +x /dump_Errors.log

RUN chown -R irisowner:irisowner /dump_Errors.log

USER irisowner

COPY --chown=$ISC_PACKAGE_MGRUSER:$ISC_PACKAGE_IRISGROUP /iris/irissession.sh /
RUN chmod +x /irissession.sh

COPY --chown=$ISC_PACKAGE_MGRUSER:$ISC_PACKAGE_IRISGROUP /iris/dump.txt /
RUN chmod +x /dump.txt


SHELL ["/irissession.sh"]

RUN \
zn "USER" \
DO $SYSTEM.SQL.Schema.ImportDDL("/dump.txt",,"IRIS")
# run iris.script
# RUN iris start IRIS \
#   && iris session IRIS < /opt/irisapp/iris.script \
#   && iris stop IRIS quietly