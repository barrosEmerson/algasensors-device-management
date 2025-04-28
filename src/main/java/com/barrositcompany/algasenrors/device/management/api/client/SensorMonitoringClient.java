package com.barrositcompany.algasenrors.device.management.api.client;

import io.hypersistence.tsid.TSID;

public interface SensorMonitoringClient {

    void enableSensorMonitoring(TSID sensorId);
    void disableSensorMonitoring(TSID sensorId);
}
