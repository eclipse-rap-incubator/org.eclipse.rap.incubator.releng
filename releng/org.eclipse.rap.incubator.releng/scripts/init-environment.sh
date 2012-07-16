# This script sets up common environment variables for the RAP builds

export WORKSPACE=${WORKSPACE:-$PWD}
echo "Workspace location: ${WORKSPACE}"

export MVN=${MVN:-"/opt/public/common/apache-maven-3.0.3/bin/mvn"}
echo "Maven path: ${MVN}"

export ECLIPSE_HOME=${ECLIPSE_HOME:-"/shared/rt/rap/build-runtimes/eclipse"}
echo "Eclipse location: ${ECLIPSE_HOME}"

export SIGNING_LOCATION=${SIGNING_LOCATION:-"/opt/public/download-staging.priv/rt/rap"}
echo "Signing location: ${SIGNING_LOCATION}"

export GIT_BASE=${GIT_BASE:-"git://git.eclipse.org/gitroot/rap"}
echo "Git base URL: ${GIT_BASE}"

export GIT_INCUBATOR_BASE=${GIT_INCUBATOR_BASE:-"${GIT_BASE}/incubator"}
echo "Git incubator base URL: ${GIT_INCUBATOR_BASE}"

export GIT_BRANCH=${GIT_BRANCH:-"master"}
echo "Git branch: ${GIT_BRANCH}"

export REPOSITORY_BASE_PATH=${REPOSITORY_BASE_PATH:-"/shared/rt/rap/nightly/2.0/incubator"}
echo "p2 repository base path: ${REPOSITORY_BASE_PATH}"

export NUM_TO_KEEP=${NUM_TO_KEEP:-"5"}
echo "Number of p2 repositories to keep: ${NUM_TO_KEEP}"

