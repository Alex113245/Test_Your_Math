package microservices.project.test_your_math.operation.repository;

import microservices.project.test_your_math.operation.domain.Operation;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface allows us to save and retrieve the Operations.
 */
public interface OperationRepository extends CrudRepository<Operation, Long> {
}
