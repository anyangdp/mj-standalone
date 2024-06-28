# dockerhub hadoop官方版本-3.3.6
### 注意事项
- 本地客户端需要操作hdfs需要开放datanode端口：9864，9866，
- 修改本机dns，确保namenode以及datanode能够映射到目标服务ip地址
- java api操作配置conf.set("dfs.client.use.datanode.hostname", "true");
- 容器重启会导致datanode和namenode的clusterId不一致，导致datanode启动报错，需要重建容器，或者修改namenode中的VERSION或者datanode中的VERSION，保持两者一致