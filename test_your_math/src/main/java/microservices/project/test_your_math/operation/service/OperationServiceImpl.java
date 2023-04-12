package microservices.project.test_your_math.operation.service;

import microservices.book.multiplication.domain.Badges;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.repository.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static microservices.book.multiplication.domain.Multiplication.getRandomOperator;

@Service
class MultiplicationServiceImpl implements MultiplicationService {

    private RandomGeneratorService randomGeneratorService;
    private MultiplicationResultAttemptRepository attemptRepository;
    private UserRepository userRepository;

    @Autowired
    public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
                                     final MultiplicationResultAttemptRepository attemptRepository,
                                     final UserRepository userRepository) {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        char operation = getRandomOperator();
        return new Multiplication(factorA, factorB, operation);
    }

    @Transactional
    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        // Check if the user already exists for that alias
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        // Avoids 'hack' attempts
        //Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct!!");

        int result;

        switch (attempt.getMultiplication().getOperator()) {
            case '+':
                result = attempt.getMultiplication().getFactorA() + attempt.getMultiplication().getFactorB();
                break;
            case '-':
                result = attempt.getMultiplication().getFactorA() - attempt.getMultiplication().getFactorB();
                break;
            case '*':
                result = attempt.getMultiplication().getFactorA() * attempt.getMultiplication().getFactorB();
                break;
            case '/':
                if (attempt.getMultiplication().getFactorB() != 0) {
                    result = (int) (attempt.getMultiplication().getFactorA() / attempt.getMultiplication().getFactorB());
                } else {
                    result = 0;
                }
                break;
            default:
                result = 0;
        }

        // Check if the attempt is correct
        boolean isCorrect = result == attempt.getResultAttempt();

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                user.orElse(attempt.getUser()),
                attempt.getMultiplication(),
                attempt.getResultAttempt(),
                isCorrect
        );

        if(isCorrect && user.isPresent()) {
            switch (attempt.getMultiplication().getOperator()) {
                case '+':
                    user.get().setPoints(user.get().getPoints() + 10);
                    break;
                case '-':
                    user.get().setPoints(user.get().getPoints() + 20);
                    break;
                case '*':
                    user.get().setPoints(user.get().getPoints() + 100);
                    break;
                case '/':
                    if (attempt.getMultiplication().getFactorB() != 0) {
                        user.get().setPoints(user.get().getPoints() + 150);
                    }
                    break;
            }
        }

        if(isCorrect && user.isPresent()){
            int numberOfPoints = user.get().getPoints();

            if(numberOfPoints >= 100 && numberOfPoints < 500 ){
                user.get().setMedalii(Badges.BRONZE);
            }
            else if(numberOfPoints >= 500 && numberOfPoints < 1000 ){
                user.get().setMedalii(Badges.SILVER);
            }
            else if(numberOfPoints >= 1000 && numberOfPoints < 2500 ){
                user.get().setMedalii(Badges.GOLD);
            }
            else if(numberOfPoints >= 2500 && numberOfPoints < 5000 ){
                user.get().setMedalii(Badges.PLATINUM);
            }
            else if(user.get().getPoints() >= 5000 && numberOfPoints < 10000 ){
                user.get().setMedalii(Badges.DIAMOND);
            }
            else if(user.get().getPoints() >= 10000 && numberOfPoints < 15000 ){
                user.get().setMedalii(Badges.MathCONNOISSEUR);
            }
            else if(user.get().getPoints() >= 15000 ) {
                user.get().setMedalii(Badges.DOMINATOROFTHEUNIVERSE);
            }
        }

        // Stores the attempt
        attemptRepository.save(checkedAttempt);

        return isCorrect;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }

    @Override
    public List<User> getLeaderboardForUsers() {
        return userRepository.findTop5ByOrderByPointsDesc();
    }



}