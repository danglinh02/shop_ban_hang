package com.example.Library.service;

import com.example.Library.entity.City;
import com.example.Library.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{
    @Autowired
    private CityRepository cityRepository;
    @Override
    public List<City> getAll() {
        return this.cityRepository.findAll();
    }
}
