package microservices.project.test_your_math.operation.repository;

import microservices.project.test_your_math.operation.domain.OperationResultAttempt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * This interface allow us to store and retrieve attempts
 */
public interface OperationResultAttemptRepository
        extends CrudRepository<OperationResultAttempt, Long> {

    /**
     * @return the latest 5 attempts for a given user, identified by their alias.
     */
    List<OperationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
}