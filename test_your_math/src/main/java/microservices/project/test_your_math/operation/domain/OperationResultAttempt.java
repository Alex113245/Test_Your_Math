package microservices.project.test_your_math.operation.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Identifies the attempt from a {@link User} to solve a
 * {@link Operation}.
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class OperationResultAttempt {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private final User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "OPERATION_ID")
    private final Operation operation;
    private final int resultAttempt;

    private final boolean correct;

    // Empty constructor for JSON/JPA
    OperationResultAttempt() {
        user = null;
        operation = null;
        resultAttempt = -1;
        correct = false;
    }

}
