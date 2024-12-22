package ma.ac.uir.javaprojectv4.Service;

import ma.ac.uir.javaprojectv4.DAO.EvaluationRepository;
import ma.ac.uir.javaprojectv4.Entity.Evaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    public void saveEvaluation(Evaluation evaluation) {
        if (evaluation.getNote() < 0 || evaluation.getNote() > 5) {
            throw new IllegalArgumentException("Note between 0 and 5.");
        }
        evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getEvaluationsByDeveloper(Long userId) {
        return evaluationRepository.findByUserId(userId);
    }
}

