package br.com.sonda.aeronave.repository;

import br.com.sonda.aeronave.domain.model.Aeronave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AeronaveRepository extends JpaRepository<Aeronave, Long> {
}
