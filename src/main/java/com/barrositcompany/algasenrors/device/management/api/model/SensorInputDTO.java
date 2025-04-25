package com.barrositcompany.algasenrors.device.management.api.model;

import lombok.Data;

@Data
public class SensorInputDTO {

    private String name;
    private String ip;
    private String location;
    private String protocol;
    private String model;
}
