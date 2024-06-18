package com.mj.web.big.data.domain.bo.etl;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MysqlConfig.class, name = "MYSQL"),
        @JsonSubTypes.Type(value = PostgresqlConfig.class, name = "POSTGRESQL"),
        @JsonSubTypes.Type(value = HiveConfig.class, name = "HIVE"),
        @JsonSubTypes.Type(value = RedisConfig.class, name = "REDIS")
})
public abstract class Configuration implements DataSourceType{
    // 资源id
    private String resourceId;
    // 数据源类型
    private String type;
    // 表名/文件名
    private String name;
    // 条件：source中是过滤条件，target中则是前置操作条件
    private String condition;
    // 模式：override 重写，append 追加，errorIfExist 存在重复提示错误，ignoreIfExist 存在重复忽略
    private String mode;
}
