package microservices.project.test_your_math.operation.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Stores information to identify the user.
 */
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "_USER") // The "USER" name is reserved in H2 database
public final class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private final String alias;

    private int points;

    //@OneToMany(mappedBy = "user")
    @Enumerated(EnumType.ORDINAL)
    private Badges medalii;

    // Empty constructor for JSON/JPA
    protected User() {
        alias = null;
        points = 0;
        medalii = null;
    }
}