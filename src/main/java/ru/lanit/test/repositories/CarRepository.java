package ru.lanit.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.lanit.test.models.CarModel;

@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {
	@Query("SELECT COUNT(DISTINCT UPPER(LEFT(Car.model, STRPOS(Car.model, '-') - 1))) FROM CarModel AS Car")
	long countDistinctVendors();
}
