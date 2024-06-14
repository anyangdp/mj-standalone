package com.mj.web.big.data;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.web.big.data.domain.bo.etl.EtlConfiguration;
import org.junit.jupiter.api.Test;

public class Test1 {
    @Test
    void testObjectMapper() throws JsonProcessingException {
//        String jsonString = "{\"origin\":{\"resourceId\":\"998299270039732224\",\"type\":\"MYSQL\",\"name\":\"Pw_Pump_PumpStationDetectionHistory\",\"condition\":\"\",\"column\":\"PumpStationDetectionHistoryOID,PumpStationID,DetectionTime,DetectionValue\"},\"target\":{\"resourceId\":\"998299569101996032\",\"type\":\"MYSQL\",\"name\":\"Pw_Pump_PumpStationDetectionHistory_bak\",\"condition\":\"\",\"column\":\"\",\"mode\":\"override\"}}";
        String jsonString = "{\"origin\":{\"resourceId\":\"998299270039732224\",\"type\":\"MYSQL\",\"name\":\"Pw_Pump_PumpStationDetectionHistory\",\"condition\":\"\",\"column\":\"PumpStationDetectionHistoryOID,PumpStationID,DetectionTime,DetectionValue\"},\"target\":{\"resourceId\":\"998299569101996032\",\"type\":\"REDIS\",\"name\":\"Pw_Pump_PumpStationDetectionHistory_bak\",\"condition\":\"\",\"column\":\"\",\"mode\":\"override\"}}";

        ObjectMapper mapper = new ObjectMapper();
        EtlConfiguration config = mapper.readValue(jsonString, EtlConfiguration.class);

        System.out.println(config.getOrigin().getClass().getName());
        System.out.println(config.getTarget().getClass().getName());
    }

    @Test
    void testFastJson() {
        String jsonString = "{\"origin\":{\"resourceId\":\"998299270039732224\",\"type\":\"MYSQL\",\"name\":\"Pw_Pump_PumpStationDetectionHistory\",\"condition\":\"\",\"column\":\"PumpStationDetectionHistoryOID,PumpStationID,DetectionTime,DetectionValue\"},\"target\":{\"resourceId\":\"998299569101996032\",\"type\":\"REDIS\",\"name\":\"Pw_Pump_PumpStationDetectionHistory_bak\",\"condition\":\"\",\"column\":\"\",\"mode\":\"override\"}}";
        EtlConfiguration config = JSON.to(EtlConfiguration.class, JSON.parseObject(jsonString));
        System.out.println(config.getOrigin().getClass().getName());
        System.out.println(config.getTarget().getClass().getName());
    }
}
