package com.castor.empleados.services;

import com.castor.empleados.entiities.Position;
import com.castor.empleados.repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService{

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public List<Position> list() {
        return (List<Position>) positionRepository.findAll();
    }
}
