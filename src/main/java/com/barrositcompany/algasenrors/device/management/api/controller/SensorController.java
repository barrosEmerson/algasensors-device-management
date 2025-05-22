package com.barrositcompany.algasenrors.device.management.api.controller;

import com.barrositcompany.algasenrors.device.management.api.client.SensorMonitoringClient;
import com.barrositcompany.algasenrors.device.management.api.model.SensorDetailOutputDTO;
import com.barrositcompany.algasenrors.device.management.api.model.SensorInputDTO;
import com.barrositcompany.algasenrors.device.management.api.model.SensorMonitoringOutputDTO;
import com.barrositcompany.algasenrors.device.management.api.model.SensorOutputDTO;
import com.barrositcompany.algasenrors.device.management.common.IdGenerator;
import com.barrositcompany.algasenrors.device.management.domain.model.Sensor;
import com.barrositcompany.algasenrors.device.management.domain.model.SensorId;
import com.barrositcompany.algasenrors.device.management.domain.repository.SensorRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorRepository sensorRepository;
    private final SensorMonitoringClient sensorMonitoringClient;



    @GetMapping
    public Page<SensorOutputDTO> search(Pageable pageable) {
        return sensorRepository.findAll(pageable)
                .map(this::convertToModel);
    }

    @GetMapping("/{sensorId}")
    public SensorOutputDTO getOne(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found") {
                    @Override
                    public String getMessage() {
                        return "Sensor with ID " + sensorId + " not found";
                    }
                });
        return convertToModel(sensor);

    }

    @GetMapping("/{sensorId}/detail")
    public SensorDetailOutputDTO getOneWithDetail(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found") {
                    @Override
                    public String getMessage() {
                        return "Sensor with ID " + sensorId + " not found";
                    }
                });

        SensorMonitoringOutputDTO detail = sensorMonitoringClient.getDetail(sensorId);
        SensorOutputDTO sensorOutputDTO = convertToModel(sensor);


        return SensorDetailOutputDTO.builder()
                .monitoring(detail)
                .sensor(sensorOutputDTO)
                .build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorOutputDTO create(@RequestBody SensorInputDTO input) {
        Sensor sensor = Sensor.builder()
                .id(new SensorId(IdGenerator.generateTSID()))
                .name(input.getName())
                .ip(input.getIp())
                .location(input.getLocation())
                .protocol(input.getProtocol())
                .model(input.getModel())
                .enabled(false)
                .build();
        sensor = sensorRepository.saveAndFlush(sensor);

        return convertToModel(sensor);
    }

    @PutMapping("/{sensorId}")
    public SensorOutputDTO update(@PathVariable TSID sensorId, @RequestBody SensorInputDTO input) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found") {
                    @Override
                    public String getMessage() {
                        return "Sensor with ID " + sensorId + " not found";
                    }
                });
        sensor.setName(input.getName());
        sensor.setIp(input.getIp());
        sensor.setLocation(input.getLocation());
        sensor.setProtocol(input.getProtocol());
        sensor.setModel(input.getModel());
        sensor.setEnabled(false);
        sensor = sensorRepository.saveAndFlush(sensor);
        return convertToModel(sensor);
    }

    @DeleteMapping("/{sensorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found") {
                    @Override
                    public String getMessage() {
                        return "Sensor with ID " + sensorId + " not found";
                    }
                });
        sensorRepository.delete(sensor);
        sensorMonitoringClient.disableSensorMonitoring(sensorId);
    }

    @PutMapping("/{sensorId}/enable")
    public SensorOutputDTO enable(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found") {
                    @Override
                    public String getMessage() {
                        return "Sensor with ID " + sensorId + " not found";
                    }
                });
        sensor.setEnabled(true);
        sensor = sensorRepository.saveAndFlush(sensor);
        sensorMonitoringClient.enableSensorMonitoring(sensorId);
        return convertToModel(sensor);
    }

    @DeleteMapping("/{sensorId}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable TSID sensorId) {
        Sensor sensor = sensorRepository.findById(new
                        SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found") {
                    @Override
                    public String getMessage() {
                        return "Sensor with ID " + sensorId + " not found";
                    }
                });
        sensor.setEnabled(false);
        sensorRepository.save(sensor);
        sensorMonitoringClient.disableSensorMonitoring(sensorId);
    }


    private SensorOutputDTO convertToModel(Sensor sensor) {
        return SensorOutputDTO.builder()
                .id(sensor.getId().getId())
                .name(sensor.getName())
                .ip(sensor.getIp())
                .location(sensor.getLocation())
                .protocol(sensor.getProtocol())
                .model(sensor.getModel())
                .enabled(sensor.getEnabled())
                .build();
    }
}
