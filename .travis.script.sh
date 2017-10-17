#!/usr/bin/env bash

# TRAVIS_PULL_REQUEST == "false" for a normal branch commit, the PR number for a PR
# TRAVIS_BRANCH == target of normal commit or target of PR
# TRAVIS_SECURE_ENV_VARS == true if encrypted variables, e.g. TESTBENCH_LICENSE is available
# TRAVIS_REPO_SLUG == the repository, e.g. vaadin/vaadin


#SI ES UN PULL REQUEST
#SI ES UN PULL REQUEST
if [ "$TRAVIS_PULL_REQUEST" != "false" ] && [ "${TRAVIS_BRANCH}" == "master" ] && [ "$TRAVIS_SECURE_ENV_VARS" == "true" ]
then
    mvn  mvn clean test #SOLO UNIT TESTS PARA ACELERAR PROCESO
    #SI NO ES UN PULL REQUEST Y ES EN MASTER(una vez mergeado)
#elif [ "$TRAVIS_PULL_REQUEST" != "true" ] && [ "${TRAVIS_BRANCH}" == "master" ] && [ "$TRAVIS_SECURE_ENV_VARS" == "true" ]
#then # TEST DE INTEGRACION Y REPORTES COMPLETOS
  #  mvn -B -e -V -Dvaadin.testbench.developer.license=dfe12f22-5ae4-4414-97a9-2e8474b8336c clean verify jacoco:report coveralls:report sonar:sonar
##SI NO ES UN PULL REQUEST Y NO ES EN MASTER (cualquier otro commit a otra branch)
#elif [ "$TRAVIS_PULL_REQUEST"  != "true" ] && [ "${TRAVIS_BRANCH}" != "master" ] && [ "$TRAVIS_SECURE_ENV_VARS" == "true" ]
#then #SOLO TEST DE INTEGRACION
#    mvn -B -e -V -Dvaadin.testbench.developer.license=dfe12f22-5ae4-4414-97a9-2e8474b8336c clean verify

else
     echo "No entra en ningun condicional analisis completo"
     mvn -B -e -V -Dvaadin.testbench.developer.license=dfe12f22-5ae4-4414-97a9-2e8474b8336c clean verify jacoco:report coveralls:report sonar:sonar
fi