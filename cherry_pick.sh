#!/bin/bash

REPOSITORY_DIR=/cygdrive/d/jwp-workspace/workspace/cherry-jwp-spring-boot

# BRANCHES=("step1-user" "step2-refactoring" "step3-session" "step4-loginuser-logging" "step5-orm" "step6-refactoring-entity" "step7-restful-api" "step8-rest-assured" "step9-oop-delete-question" "step10-delete-question-completed" "step11-spring-profile")
BRANCHES=("step5-orm" "step6-refactoring-entity" "step7-restful-api" "step8-rest-assured" "step9-oop-delete-question" "step10-delete-question-completed")
COMMIT_REVISION=210bb07

rm -rf $REPOSITORY_DIR

git clone https://github.com/slipp/jwp-spring-boot.git $REPOSITORY_DIR

cd $REPOSITORY_DIR
pwd

for BRANCH_NAME in "${BRANCHES[@]}"; do
	git cherry-pick --quit
    echo $BRANCH_NAME
    git checkout -b $BRANCH_NAME origin/$BRANCH_NAME
    git cherry-pick $COMMIT_REVISION
    git push origin $BRANCH_NAME 
done

echo "Finished."