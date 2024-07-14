package br.rafael.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.rafael.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
