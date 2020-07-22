#!/bin/bash
trap "{ stop; sleep 0.5; exit 255; }" EXIT
export EM_APP_HOME=${APP_HOME}
export PID_FILE=${EM_APP_HOME}/log/instance.pid
cd ${EM_APP_HOME}
function start_app(){
    mkdir -p ${EM_APP_HOME}/log/
    $JAVA_HOME/bin/java -jar ${EM_APP_HOME}/lib/${JAR_NAME} &
    echo $! > ${PID_FILE}
}

function stop(){
    echo "start to kill user process"
    if [ -f ${EM_APP_HOME}/log/instance.pid ]; then
        ps aux | awk -vpid=$(cat ${PID_FILE}) '$2==pid {print}' | grep $(cat ${PID_FILE}) &> /dev/null
        if [ $? -ne 0 ]; then
            echo "already stopped!"
            return
        fi
        echo  "stopping process "
        kill $(cat ${PID_FILE})

        for ((i=0;i<40;i++)); do
            if [ $i -eq 30 ]; then
                kill -9 $(cat ${PID_FILE})
            fi
            ps aux | awk -vpid=$(cat ${PID_FILE}) '$2==pid {print}' | grep $(cat ${PID_FILE}) &> /dev/null
            if [ $? -eq 0 ]; then
                echo -n .
                sleep 0.5
            else
                echo " [done]!"
                rm ${EM_APP_HOME}/log/instance.pid
                break
            fi
        done
    fi
}
start_app
cd ${EM_APP_HOME}/../emcprobe
bash ./bin/start.sh