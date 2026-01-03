#!/bin/bash
set -e

echo "=========================================="
echo "      STARTING CI PIPELINE AUTOMATION     "
echo "=========================================="

# 1. Code Quality
echo ""
echo ">>> STEP 1: Code Quality Checks (Ktlint & Detekt)"
./gradlew ktlintCheck detekt
if [ $? -eq 0 ]; then
    echo "✅ Code Quality Checks Passed"
else
    echo "❌ Code Quality Checks Failed"
    exit 1
fi

# 2. Smoke Test (Lint & Build Debug)
echo ""
echo ">>> STEP 2: Smoke Test (Lint & Compile)"
./gradlew lintDebug assembleDebug
if [ $? -eq 0 ]; then
    echo "✅ Smoke Test Passed"
else
    echo "❌ Smoke Test Failed"
    exit 1
fi

# 3. Unit Tests
echo ""
echo ">>> STEP 3: Unit Tests"
./gradlew testDebugUnitTest
if [ $? -eq 0 ]; then
    echo "✅ Unit Tests Passed"
else
    echo "❌ Unit Tests Failed"
    exit 1
fi

# 4. Packaging
echo ""
echo ">>> STEP 4: Packaging (Assemble Release)"
./gradlew assembleRelease
if [ $? -eq 0 ]; then
    echo "✅ Packaging Successful"
else
    echo "❌ Packaging Failed"
    exit 1
fi

# 5. Deployment
echo ""
echo ">>> STEP 5: Deployment"
# Simulating deployment by moving the artifact to a deploy folder.
# In a real environment, this would use gradle-play-publisher or firebase-app-distribution.
DEPLOY_DIR="deploy_output"
mkdir -p $DEPLOY_DIR
cp app/build/outputs/apk/release/app-release-unsigned.apk $DEPLOY_DIR/app-release.apk

if [ -f "$DEPLOY_DIR/app-release.apk" ]; then
    echo "✅ Deployment Successful (Artifact staged in $DEPLOY_DIR)"
else
    echo "❌ Deployment Failed"
    exit 1
fi

echo ""
echo "=========================================="
echo "      PIPELINE COMPLETED SUCCESSFULLY     "
echo "=========================================="
