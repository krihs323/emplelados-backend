package com.castor.empleados.repositories;

import com.castor.empleados.entiities.Position;
import org.springframework.data.repository.CrudRepository;

public interface PositionRepository extends CrudRepository<Position, Long> {
}
