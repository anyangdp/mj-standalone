package com.mj.web.big.data.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import com.mj.web.big.data.domain.bo.db.DataSourceProperties;
import com.mj.web.big.data.domain.bo.db.MysqlDataSourceProperties;
import com.mj.web.big.data.domain.bo.db.PostgresqlDataSourceProperties;
import com.mj.web.big.data.enums.DatasourceTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据源
 */
@TableName("bd_datasource")
@Accessors(chain = true)
@Data
public class BdDatasourceDO extends AbstractDO<String> {

    // 数据源类型
    private String type;
    // 驱动连接串
    private String driver;
    // 连接url
    private String url;
    // 主机
    private String host;
    // 端口
    private String port;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 数据源说明
    private String description;

    public DataSourceProperties build() {
        if (this.type.equalsIgnoreCase(DatasourceTypeEnum.MYSQL.name())) {
            return new MysqlDataSourceProperties(username, password, driver, url);
        } else if (this.type.equalsIgnoreCase(DatasourceTypeEnum.POSTGRESQL.name())) {
            return new PostgresqlDataSourceProperties(username, password, driver, url);
        } else {
            return null;
        }
    }
}
