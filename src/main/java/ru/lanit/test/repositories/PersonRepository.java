package ru.lanit.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.lanit.test.models.PersonModel;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {
}
