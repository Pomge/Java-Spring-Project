package ru.lanit.test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.lanit.test.models.PersonModel;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {
	Optional<PersonModel> findById(long id);
}
