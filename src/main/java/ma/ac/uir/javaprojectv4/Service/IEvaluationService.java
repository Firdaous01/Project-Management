package ma.ac.uir.javaprojectv4.Service;

import ma.ac.uir.javaprojectv4.Entity.Evaluation;

import java.util.List;

public interface IEvaluationService {

    void saveEvaluation(Evaluation evaluation);
    List<Evaluation> getEvaluationsByDeveloper(Long userId);

}
