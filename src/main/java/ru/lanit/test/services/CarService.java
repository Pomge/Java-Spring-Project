package ru.lanit.test.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.lanit.test.models.CarModel;
import ru.lanit.test.repositories.CarRepository;

@Service
@Transactional(readOnly = true)
public class CarService {
	private final CarRepository carRepository;

	@Autowired
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	public Optional<CarModel> findById(long id) {
		return carRepository.findById(id);
	}

	public long getCarCount() {
		return carRepository.count();
	}

	public long getDistinctVendorsCount() {
		return carRepository.countDistinctVendors();
	}

	@Transactional
	public void save(CarModel carModel) {
		carRepository.save(carModel);
	}

	@Transactional
	public void truncateTable() {
		carRepository.truncateTable();
	}
}
