#
#  Inmobi - Sistema de gestión Inmobiliaria
([Youtube Video](https://www.youtube.com/watch?v=tyroTfCwO0Q))
<a href="https://travis-ci.org/tpfinal-pp1/tp-final/branches" target="_blank"><img src="https://travis-ci.org/tpfinal-pp1/tp-final.svg?branch=master" alt="Build" /> <a href="https://coveralls.io/github/tpfinal-pp1/tp-final" target="_blank"><img src="https://coveralls.io/repos/github/tpfinal-pp1/tp-final/badge.svg" alt="Coverage Status" /></a>

See tests logs for Serial Key or go to Installer/KeyGen
![image](https://user-images.githubusercontent.com/15642727/33222962-340af11c-d13a-11e7-8af4-937d9c4c1cd9.png)

  - Travis(Integración Continua): https://travis-ci.org/tpfinal-pp1/tp-final
    
  -  SonarCloud(Calidad de Codigo): https://sonarcloud.io/dashboard?id=com.TpFinal%3ATpFinal
    
  -  Coveralls(Covertura): https://coveralls.io/github/tpfinal-pp1/tp-final


# Maven goal:

```
jetty:run
```

# CI-CD:
```bash
    #cleanup workspace
    rm -rf jetty.old
    mv jetty jetty.old
    # download jetty
    cp /config/workspace/Package/jetty.tar.gz jetty.tar.gz
    tar xfz jetty.tar.gz
    mv jetty-distribution-9.4.8.v20171121 jetty
    rm jetty.tar.gz
    mv jetty.old/Files jetty/Files
    #Kill jetty if running
    pkill -9 -f jetty.port=9012 || true
    sleep 5
    cd jetty
    cp /config/workspace/Package/target/Inmobi.war /config/workspace/Run/jetty/webapps/root.war
    #start jetty & prevent jenkins killing job after finish
    BUILD_ID=dontKillMe nohup java -jar start.jar jetty.port=9012 > ../jetty.log 2>&1 &
```











    

