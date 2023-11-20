package ru.lanit.test.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.lanit.test.models.PersonModel;
import ru.lanit.test.repositories.PersonRepository;

@Service
@Transactional(readOnly = true)
public class PersonService {
	private final PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public Optional<PersonModel> findById(long id) {
		return personRepository.findById(id);
	}

	public long getPersonCount() {
		return personRepository.count();
	}

	@Transactional
	public void save(PersonModel personModel) {
		personRepository.save(personModel);
	}

	@Transactional
	public void truncateTable() {
		personRepository.truncateTable();
	}
}
