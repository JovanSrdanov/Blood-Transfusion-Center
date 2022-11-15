package groupJASS.ISA_2022.Repository;

import groupJASS.ISA_2022.Model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, UUID> {
}
