package microservices.project.test_your_math.operation.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Random;

/**
 * This class represents an Operation (a * b).
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class Operation {

    @Id
    @GeneratedValue
    @Column(name = "OPERATION_ID")
    private Long id;

    // Both factors
    private final int factorA;
    private final int factorB;

    // Operator (+, -,, /)
    private final char operator;

    // Empty constructor for JSON/JPA
    Operation() {
        this(0, 0, '+');
    }

    public static char getRandomOperator() {
        Random random = new Random();
        int operatorIndex = random.nextInt(4);
        char[] operators = {'+', '-', '*', '/'};
        return operators[operatorIndex];
    }
}
