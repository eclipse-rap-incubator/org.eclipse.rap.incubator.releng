# Functions used in the RAP build

test -f ${SCRIPTS_DIR}/init-environment.sh && . ${SCRIPTS_DIR}/init-environment.sh

echo "***********************************************************************"

p2AddContent() {
  if [ $# -lt 2 ]
  then
    echo "Usage: `p2AddContent` SOURCE_REPO TARGET_REPO [NAME]"
    exit 1
  fi

  SOURCE=file://${1}
  DESTINATION=file://${2}
  DESTINATION_NAME=${3:-"A RAP p2 Repository"}

  ${ECLIPSE_HOME}/eclipse -nosplash -verbose \
    -application org.eclipse.equinox.p2.metadata.repository.mirrorApplication \
    -source ${SOURCE} \
    -destination ${DESTINATION} \
    -destinationName ${DESTINATION_NAME}
  ${ECLIPSE_HOME}/eclipse -nosplash -verbose \
    -application org.eclipse.equinox.p2.artifact.repository.mirrorApplication \
    -source ${SOURCE} \
    -destination ${DESTINATION} \
    -destinationName ${DESTINATION_NAME}
}

