#!/bin/bash
#
# General build script for all RAP Incubator builds
#
#set -x

######################################################################
# initial argument checks
if [ $# -lt 1 ]
then
  echo "Usage: `basename $0` COMPONENT_NAME [M|S]"
  exit 1
fi
export BUILD_TYPE=${3:-"M"}

######################################################################
# setup and initialization

SCRIPTS_DIR=$(dirname $(readlink -nm $0))
test -f ${SCRIPTS_DIR}/init-functions.sh && . ${SCRIPTS_DIR}/init-functions.sh

COMPONENT_NAME=${1}
REPOSITORY_NAME="org.eclipse.rap.incubator.${COMPONENT_NAME}"
BUILD_PROJECT_PATH="releng/org.eclipse.rap.${COMPONENT_NAME}.build"

if [ "S" == "${BUILD_TYPE}" ]; then
  SIGN=true
else
  SIGN=false
fi

######################################################################
# arbitrary checks and clean-ups
test -d ${WORKSPACE} || exit 1

######################################################################
# configuration check and debug
echo "***********************************************************************"
echo "Running `basename $0`"
echo "RAP component name: ${COMPONENT_NAME}"
echo "Repository name: ${REPOSITORY_NAME}"
echo "Build project path: ${BUILD_PROJECT_PATH}"
echo "Build type: ${BUILD_TYPE}"
echo "Signing enabled: ${SIGN}"

######################################################################
# git clone build repository
RELENG_REPOSITORY_NAME="org.eclipse.rap.incubator.releng"
REPOSITORY=${GIT_INCUBATOR_BASE}/${RELENG_REPOSITORY_NAME}
echo "Git clone of releng repository ${REPOSITORY} into ${WORKSPACE}"
cd ${WORKSPACE}
rm -rf ${WORKSPACE}/${RELENG_REPOSITORY_NAME:-"DUMMYDIRECTORY"}
git clone --branch=${GIT_BRANCH} ${REPOSITORY} ${RELENG_REPOSITORY_NAME}

######################################################################
# git clone
REPOSITORY=${GIT_INCUBATOR_BASE}/${REPOSITORY_NAME}
echo "Git clone of ${REPOSITORY} into ${WORKSPACE}"
cd ${WORKSPACE}
rm -rf ${WORKSPACE}/${REPOSITORY_NAME:-"DUMMYDIRECTORY"}
git clone --branch=${GIT_BRANCH} ${REPOSITORY} ${REPOSITORY_NAME}

######################################################################
# execute build
BUILD_DIRECTORY=${WORKSPACE}/${REPOSITORY_NAME}/${BUILD_PROJECT_PATH}
echo "Starting build in ${BUILD_DIRECTORY}"
cd ${BUILD_DIRECTORY}
${MVN} -e clean package -Dsign=$sign
EXITCODE=$?
if [ "$EXITCODE" != "0" ]; then
  echo "Maven exited with error code " + ${EXITCODE}
  exit ${EXITCODE}
fi

######################################################################
# extract build timestamp
## TODO mknauer Find a better way to determine the TIMESTAMP
REPOSITORY_DIRECTORY=${WORKSPACE}/${REPOSITORY_NAME}/${BUILD_PROJECT_PATH}/repository/target/repository
VERSION=$(ls ${REPOSITORY_DIRECTORY}/features/org.eclipse.rap.*.feature_*.jar | sed 's/.*_\([0-9.-]\+\)\..*\.jar/\1/')
TIMESTAMP=$(ls ${REPOSITORY_DIRECTORY}/features/org.eclipse.rap.*.feature_*.jar | sed 's/.*\.\([0-9-]\+\)\.jar/\1/')
echo "Version is ${VERSION}"
echo "Timestamp is ${TIMESTAMP}"
test -n "${VERSION}" || exit 1
test -n "${TIMESTAMP}" || exit 1

######################################################################
# copy repository to target location
COMPONENT_DIRECTORY=${REPOSITORY_BASE_PATH}/${COMPONENT_NAME}
echo "Copy new ${TIMESTAMP} repository of ${COMPONENT_NAME} to ${COMPONENT_DIRECTORY}" 
mkdir -p ${COMPONENT_DIRECTORY}
cd ${COMPONENT_DIRECTORY}
cp -a ${REPOSITORY_DIRECTORY} ${COMPONENT_DIRECTORY}/${TIMESTAMP}

######################################################################
# clean-up target location
echo "Removing old repositories from ${COMPONENT_DIRECTORY}, keeping the ${NUM_TO_KEEP} most recent"
cd ${COMPONENT_DIRECTORY}
while [ $(find . -maxdepth 1 -type d | grep '.*[0-9]$' | wc -l) -gt ${NUM_TO_KEEP} ]
do
  TO_DELETE=`ls -ldtr --time-style=long-iso *[0-9] | grep '^d' | head -1 | awk '{print $8}'`
  echo "Deleting ${COMPONENT_DIRECTORY}/${TO_DELETE}"
  rm -rf ${TO_DELETE}
done

######################################################################
# create final p2 repository
echo "Creating p2 repository in ${COMPONENT_DIRECTORY}"
cd ${COMPONENT_DIRECTORY}
rm -rf content.jar artifact.jar plugins/ features/
for II in `ls -dtr [0-9]*-[0-9]*`; do
  echo "Adding data from ${II}"
  p2AddContent ${COMPONENT_DIRECTORY}/${II} ${COMPONENT_DIRECTORY} ${COMPONENT_NAME}
done

######################################################################
# build done
echo "Build ${TIMESTAMP} of ${COMPONENT_NAME} ${VERSION} done."
echo "***********************************************************************"

