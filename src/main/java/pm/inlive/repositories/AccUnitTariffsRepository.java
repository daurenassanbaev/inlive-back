package pm.inlive.repositories;

import pm.inlive.entities.AccUnitTariffs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccUnitTariffsRepository extends JpaRepository<AccUnitTariffs, Long> {
}

