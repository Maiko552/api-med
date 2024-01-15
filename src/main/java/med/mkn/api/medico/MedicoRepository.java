package med.mkn.api.medico;

import org.springframework.data.jpa.repository.JpaRepository;

//Métodos herdados de JpaRepository de JPA CRUD embutidos
public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
