# Music Player Server

> A Web Music Player Backend built with Spring Boot 3
> 基于 Spring Boot 3 构建的网页音乐播放器后端

## 一、项目介绍
本项目是网页音乐播放器的核心后端服务，基于 Spring Boot 3 开发，提供用户注册/登录、用户信息管理、头像上传、音乐资源管理等核心 API，支撑前端 `music-player-client` 和 `music-player-admin` 模块的业务逻辑。

### 核心特性
- ✅ 用户管理：注册、登录、信息查询/修改/删除
- ✅ 头像上传：本地文件存储 + 静态资源映射
- ✅ 分页查询：支持用户列表分页、关键词模糊搜索
- ✅ 统一响应：JSON 格式返回，统一状态码规范
- ✅ 配置解耦：文件存储路径等配置抽离至 `application.yml`

### 技术栈
| 技术 / 框架           | 版本                 | 用途                          |
| ----------------- | ------------------ | --------------------------- |
| Spring Boot       | 3.1.6              | 核心开发框架                      |
| MyBatis-Plus      | 3.5.3.1            | 数据访问层增强（替代原生 MyBatis）       |
| MySQL             | 8.0+               | 关系型数据库                      |
| Lombok            | 1.18.x             | 简化实体类代码（getter/setter/ 构造器） |
| Jackson           | 内置（Spring Boot 依赖） | JSON 序列化 / 反序列化             |
| Spring Web        | 内置（Spring Boot 依赖） | Web 接口开发（RESTful API）       |
| MySQL Connector/J | 内置（runtime 依赖）     | MySQL 数据库连接驱动               |

## 二、快速开始

### 1. 环境要求
- JDK：17+（Spring Boot 3 最低要求）
- Maven：3.8+
- MySQL：8.0+
- 操作系统：Windows/macOS/Linux（macOS ARM 架构需注意依赖兼容性）

### 2. 部署步骤

#### 2.1 克隆代码
```bash
git clone https://github.com/yw002/music-player-server.git
cd music-player-server
```
#### 2.2 导入数据库
导入sql文件：sql/music_player.sql

## 三、许可证
本项目为个人学习 / 非商业用途项目，仅供参考交流，禁止用于商业盈利场景。