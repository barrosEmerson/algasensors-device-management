//package com.barrositcompany.algasenrors.device.management.api.client.impl;
//
//import com.barrositcompany.algasenrors.device.management.api.client.RestClientFactory;
//import com.barrositcompany.algasenrors.device.management.api.client.SensorMonitoringClient;
//import com.barrositcompany.algasenrors.device.management.api.model.SensorMonitoringOutputDTO;
//import io.hypersistence.tsid.TSID;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestClient;
//
//@Component
//public class SensorMonitoringClientImpl implements SensorMonitoringClient {
//
//    private final RestClient restClient;
//
//    public SensorMonitoringClientImpl(RestClientFactory restClientFactory) {
//        this.restClient = restClientFactory.temperatureMonitoringRestClient();
//    }
//
//
//
//
//    @Override
//    public void enableSensorMonitoring(TSID sensorId) {
//        restClient.put()
//                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
//                .retrieve()
//                .toBodilessEntity();
//
//    }
//
//    @Override
//    public void disableSensorMonitoring(TSID sensorId) {
//        restClient.delete()
//                .uri("/api/sensors/{sensorId}/monitoring/enable", sensorId)
//                .retrieve()
//                .toBodilessEntity();
//    }
//
//    @Override
//    public SensorMonitoringOutputDTO getDetail(TSID sensorId) {
//        return restClient.get()
//                .uri("/api/sensors/{sensorId}/monitoring", sensorId)
//                .retrieve()
//                .body(SensorMonitoringOutputDTO.class);
//
//
//    }
//}
