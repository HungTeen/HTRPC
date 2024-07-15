# HTRPC

[![Java](https://img.shields.io/badge/Java-21-informational)](http://openjdk.java.net/)
[![Spring](https://img.shields.io/badge/Spring-6.1.8-success)](https://spring.io/projects/spring-framework)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-success)](https://spring.io/projects/spring-boot)
[![Netty](https://img.shields.io/badge/Netty-4.1.100-purple)](https://netty.io)
[![Nacos](https://img.shields.io/badge/Nacos-2.3.0-%23267DF7)](https://github.com/alibaba/nacos)
[![Dubbo](https://img.shields.io/badge/Dubbo-3.2.4-red)](https://github.com/apache/dubbo)


[简体中文](./README.md) | English

## Features:
* Implemented a Simplified SPI Mechanism: Developed a custom SPI mechanism allowing configuration-based customization of components such as compression, serialization, and registry centers.
* High-Performance Network Communication with Netty: Utilized Netty, based on NIO, to achieve high-performance network communication, boosting the system's QPS from 200 to over 3500.
* Connection Management: Employed a heartbeat mechanism to maintain persistent connections between the server and client, minimizing the overhead of reconnections.
* Dynamic Proxy for Simplified Usage: Leveraged JDK dynamic proxies to abstract remote method calls and load balancing details, streamlining the framework's usability.
* Spring Integration for Dependency Management: Used Spring's IOC container to manage Beans, enabling service registration and service reference retrieval via annotations.
* Spring Boot Integration: Developed a Spring Boot Starter to facilitate the automatic initiation of RPC servers through annotations.

## Metrics
### HTRPC
<img src="metrics/htrpc/ht_1_qps_ttlb.png" width="240">
<img src="metrics/htrpc/ht_1_successrate.png" width="240">

### Dubbo

<img src="metrics/dubbo/ht_qps_ttlb.png" width="240">
<img src="metrics/dubbo/ht_successrate.png" width="240">