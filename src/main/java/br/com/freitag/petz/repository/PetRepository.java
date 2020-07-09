package br.com.freitag.petz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.freitag.petz.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
