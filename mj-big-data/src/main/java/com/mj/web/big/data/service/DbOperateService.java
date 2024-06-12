package com.mj.web.big.data.service;

import com.mj.common.exception.exception.BizException;
import com.mj.web.big.data.config.SqlQuery;
import com.mj.web.big.data.domain.bo.DataSourceProperties;
import com.mj.web.big.data.domain.bo.JdbcConnection;
import com.mj.web.big.data.domain.dobj.BdDatasourceDO;
import com.mj.web.big.data.enums.DatasourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
@Service
public class DbOperateService {

    private final BdDatasourceService bdDatasourceService;

    private final SqlQuery sqlQuery;

    private static final Map<String, JdbcConnection> store = new ConcurrentHashMap<>();

    private Connection getConnection(DataSourceProperties dataSourceProperties) throws SQLException {
        return dataSourceProperties.createConnection();
    }

    public JdbcConnection getConnection(String id) throws Exception {
        JdbcConnection connection;
        if (store.containsKey(id)) {
            connection = store.get(id);
        } else {
            BdDatasourceDO byId = bdDatasourceService.getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            }
            connection = new JdbcConnection(DatasourceTypeEnum.lookup(byId.getType()), getConnection(byId.build()));
            store.put(id, connection);
        }
        return connection;
    }

    public void reconnect(String id) throws Exception {
        disconnect(id);
        getConnection(id);
    }

    public void disconnect(String id) throws SQLException {
        if (store.containsKey(id)) {
            store.get(id).getConnection().close();
        }
    }

    public List<String> showTables(String id) throws Exception {
        List<String> result = new ArrayList<>();
        String sql = sqlQuery.getListTables().get(getConnection(id).getDatasourceTypeEnum().name().toLowerCase());
        PreparedStatement showTables = getConnection(id).getConnection().prepareStatement(sql);
        ResultSet resultSet = showTables.executeQuery();
        while (resultSet.next()) {
            result.add(resultSet.getString(1));
        }
        return result;
    }

    @PreDestroy
    public void destroy() throws SQLException {
        for (String id : store.keySet()) {
            store.get(id).getConnection().close();
        }
    }
}
