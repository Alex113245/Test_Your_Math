package microservices.project.test_your_math.operation.service;

import microservices.project.test_your_math.operation.domain.Operation;
import microservices.project.test_your_math.operation.domain.OperationResultAttempt;
import microservices.project.test_your_math.operation.domain.User;

import java.util.List;

public interface OperationService {

    /**
     * Creates an Operation object with two randomly-generated factors
     * between 11 and 99.
     *
     * @return an Operation object with random factors
     */
    Operation createRandomOperation();

    /**
     * @return true if the attempt matches the result of the
     *         operation, false otherwise.
     */
    boolean checkAttempt(final OperationResultAttempt resultAttempt);

    /**
     * Gets the statistics for a given user.
     *
     * @param userAlias the user's alias
     * @return a list of {@link OperationResultAttempt} objects, being the past attempts of the user.
     */
    List<OperationResultAttempt> getStatsForUser(final String userAlias);

    List<User> getLeaderboardForUsers();


}
