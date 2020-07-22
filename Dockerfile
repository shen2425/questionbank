# 基础镜像镜像地址
FROM hub.baidubce.com/cnap-public/microservice-springcloud:probe1.5.0.2_centos7.1_java8
# 安装应用程序包,推荐/home/app/lib目录下
COPY ./target/cstm-user-1.0-SNAPSHOT.jar /home/app/lib/
# 应用启动命令
CMD ["java","-jar","/home/app/lib/cstm-user-1.0-SNAPSHOT.jar"]