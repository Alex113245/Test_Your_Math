package microservices.project.test_your_math.operation.repository;

import microservices.project.test_your_math.operation.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * This interface allows us to save and retrieve Users
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByAlias(final String alias);

    List<User> findTop5ByOrderByPointsDesc();


}

