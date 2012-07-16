# Functions used in the RAP build

test -f ${SCRIPTS_DIR}/init-environment.sh && . ${SCRIPTS_DIR}/init-environment.sh

p2AddContent() {
  if [ $# -lt 2 ]
  then
    echo "Usage: `p2AddContent` SOURCE_REPO TARGET_REPO"
    exit 1
  fi

  SOURCE=file://${1}
  DESTINATION=file://${2}

  ${ECLIPSE_HOME}/eclipse -nosplash -verbose \
    -application org.eclipse.equinox.p2.metadata.repository.mirrorApplication \
    -source ${SOURCE} \
    -destination ${DESTINATION}
  ${ECLIPSE_HOME}/eclipse -nosplash -verbose \
    -application org.eclipse.equinox.p2.artifact.repository.mirrorApplication \
    -source ${SOURCE} \
    -destination ${DESTINATION}
}

