package com.barrositcompany.algasenrors.device.management.domain.repository;

import com.barrositcompany.algasenrors.device.management.domain.model.SensorId;
import com.barrositcompany.algasenrors.device.management.domain.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor,SensorId> {
}
