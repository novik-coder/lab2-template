# 使用 JDK 17 的 OpenJDK 镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件到容器中
COPY target/gateway-service-1.0-SNAPSHOT.jar gateway-service-1.0-SNAPSHOT.jar

# 启动 Gateway 应用
ENTRYPOINT ["java", "-jar", "gateway-service-1.0-SNAPSHOT.jar"]

# 开放端口 8080
EXPOSE 8080
