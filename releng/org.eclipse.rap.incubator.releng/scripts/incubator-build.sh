#!/bin/bash

SCRIPTS_DIR=$(dirname $(readlink -nm $0))
. $SCRIPTS_DIR/build-environment.sh

if [ "$BUILD_TYPE" == "S" ]; then
  sign=true
else
  sign=false
fi

# Cleanup workspace dir
test -n "$WORKSPACE" -a -d "$WORKSPACE" && rm -rf "$WORKSPACE"/*

######################################################################
# Execute Build

cd "$WORKSPACE/org.eclipse.rap.incubator.releng/releng/org.eclipse.rap.incubator.releng"
echo "Running maven on $PWD, sign=$sign"
$MVN -e clean package -Dsign=$sign || exit 1

VERSION=$(ls incubator-repository/target/repository/features/org.eclipse.rap.incubator.supplemental.fileupload.feature_*.jar | sed 's/.*_\([0-9.-]\+\)\..*\.jar/\1/')
TIMESTAMP=$(ls incubator-repository/target/repository/features/org.eclipse.rap.incubator.supplemental.fileupload.feature_*.jar | sed 's/.*\.\([0-9-]\+\)\.jar/\1/')
echo "Version is '$VERSION'"
echo "Timestamp is '$TIMESTAMP'"
test -n "$VERSION" || exit 1
test -n "$TIMESTAMP" || exit 1

# Example: rap-incubator-1.5.0-N-20110814-2110.zip
zipFileName=rap-incubator-$VERSION-$BUILD_TYPE-$TIMESTAMP.zip
if [ -d incubator-repository/target/fixedSigned ]; then
  cd incubator-repository/target/fixedSigned
  zip -r "$WORKSPACE/$zipFileName" .
  zip -d "$WORKSPACE/$zipFileName" "META-INF/*"
  cd -
else
  mv incubator-repository/target/*.zip "$WORKSPACE/$zipFileName"
fi

repoZipFileName=rap-incubator-repo-$VERSION-$BUILD_TYPE-$TIMESTAMP.zip
if [ -d incubator-repository/target/fixedPacked ]; then
  cd incubator-repository/target/fixedPacked
  zip -r "$WORKSPACE/$repoZipFileName" .
  zip -d "$WORKSPACE/$repoZipFileName" "META-INF/*"
  cd -
fi

######################################################################
# Include legal files in zip

cd "$WORKSPACE"
cp -f source/releng/org.eclipse.rap.releng/legal/notice.html .
cp -f source/releng/org.eclipse.rap.releng/legal/epl-v10.html .
zip "$zipFileName" notice.html epl-v10.html
