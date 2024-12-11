### 实验任务 #2

![GitHub Classroom Workflow](../../workflows/GitHub%20Classroom%20Workflow/badge.svg?branch=master)

## 微服务

### 任务描述

在第二次实验中，_根据不同的任务选项_，要求实现一个由多个相互交互的服务组成的系统。

### 需求

1. 每个服务有自己的存储空间（如果需要）。为了学习目的，可以使用一个数据库实例，但每个服务仅与自己的逻辑数据库交互。服务间的数据库请求是_禁止_的。
2. 服务间的交互使用 HTTP（遵循 RESTful 规范）。
3. 设置 **Gateway Service** 作为唯一的入口点和服务间通信的中介。服务间的水平请求是_不允许_的。
4. 每个服务应有一个特殊的端点 `GET /manage/health`，返回 200 OK，用于检查服务的可用性（在 [Github Actions](.github/workflows/classroom.yml) 中，通过脚本检查所有服务的健康状态 [wait-script.sh](scripts/wait-script.sh)）。
   ```shell
   "$path"/wait-for.sh -t 120 "http://localhost:$port/manage/health" -- echo "Host localhost:$port is active"
   ```
5. 代码托管在 Github 上，构建使用 Github Actions。
6. Gateway Service 应该在端口 8080 上运行，其他服务应分别运行在 8050、8060、8070 端口。
7. 每个服务必须使用 Docker 容器化。
8. 在 [docker-compose.yml](docker-compose.yml) 中配置 Docker 容器的构建和启动。
9. 在 [classroom.yml](.github/workflows/classroom.yml) 中添加构建和单元测试的步骤。
10. 在 [autograding.json](.github/classroom/autograding.json) 和 [classroom.yml](.github/workflows/classroom.yml) 中，将 `<variant>` 替换为您的任务选项。

### 说明

1. 开发时可以使用 Docker 中的 Postgres，为此需要运行 `docker compose up -d`，该命令会启动一个 Postgres 13 容器，并根据您的任务选项（在文件 [schema-$VARIANT](postgres/scripts) 中描述）创建相应的数据库和用户 `program:test`。
2. 创建数据库时，需要在 [20-create-schemas.sh](postgres/20-create-databases.sh) 中指定您的任务选项。
3. Docker Compose 允许构建镜像，需要在配置文件中指定 `build` 块 ([详细文档](https://docs.docker.com/compose/compose-file/build/))。
4. 不允许服务间进行水平通信。
5. 可以在本地检查集成测试，方法是导入 Postman 集合 `<variant>/postman/collection.json` 和 `<variant>/postman/environment.json`。

![服务架构图](images/services.png)

假设我们的服务包括 `UserService`、`OrderService`、`WarehouseService` 和 `Gateway`：

* 用户 `Alex` 通过 `Gateway` 发起请求，购买产品 `productName: 'Lego Technic 42129'`。
* `Gateway` -> `UserService`，检查用户是否存在，并根据 `login: Alex` 获取 `userUid`。
* `Gateway` -> `WarehouseService`，根据 `productName` 获取 `itemUid`，并为订单预留该商品。
* `Gateway` -> `OrderService`，使用 `userUid` 和 `itemUid` 创建一个订单，并获得 `orderUid`。
* `Gateway` -> `WarehouseService`，使用 `orderUid`，将商品的状态从 `Reserved` 更改为 `Ordered`，并记录订单链接。

### 任务提交

1. 在接到任务后，您将创建该仓库的 fork，并关联到您的个人账户。
2. 当所有测试成功完成后，GitHub Classroom 的 Dashboard 会显示任务通过。

### 任务选项

4. [图书馆系统](v4/README.md)
