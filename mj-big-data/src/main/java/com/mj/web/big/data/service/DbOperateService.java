package com.mj.web.big.data.service;

import com.mj.common.exception.exception.BizException;
import com.mj.web.big.data.domain.bo.DataSourceProperties;
import com.mj.web.big.data.domain.dobj.BdDatasourceDO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private static final Map<String, Connection> store = new ConcurrentHashMap<>();

    private Connection getConnection(DataSourceProperties dataSourceProperties) throws SQLException {
        return dataSourceProperties.createConnection();
    }

    public Connection getConnection(String id) throws Exception {
        Connection connection;
        if (store.containsKey(id)) {
            connection = store.get(id);
        } else {
            BdDatasourceDO byId = bdDatasourceService.getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            }
            connection = getConnection(byId.build());
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
            store.get(id).close();
        }
    }

    public List<String> showTables(String id) throws Exception {
        List<String> result = new ArrayList<>();
        PreparedStatement showTables = getConnection(id).prepareStatement("show tables");
        ResultSet resultSet = showTables.executeQuery();
        while (resultSet.next()) {
            result.add(resultSet.getString(1));
        }
        return result;
    }

    @PreDestroy
    public void destroy() throws SQLException {
        for (String id : store.keySet()) {
            store.get(id).close();
        }
    }
}
