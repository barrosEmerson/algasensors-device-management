package com.barrositcompany.algasenrors.device.management.api.client;

import com.barrositcompany.algasenrors.device.management.api.model.SensorMonitoringOutputDTO;
import io.hypersistence.tsid.TSID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;

@HttpExchange("/api/sensors/{sensorId}/monitoring")
public interface SensorMonitoringClient {

    @PutExchange("/enable")
    void enableSensorMonitoring(@PathVariable TSID sensorId);
    @DeleteExchange("/enable")
    void disableSensorMonitoring(@PathVariable TSID sensorId);
    @GetExchange
    SensorMonitoringOutputDTO getDetail(@PathVariable TSID sensorId);
}
