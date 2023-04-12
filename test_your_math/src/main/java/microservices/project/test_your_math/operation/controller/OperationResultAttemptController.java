package microservices.project.test_your_math.operation.controller;

import microservices.project.test_your_math.operation.domain.Badges;
import microservices.project.test_your_math.operation.domain.MultiplicationResultAttempt;
import microservices.project.test_your_math.operation.domain.User;
import microservices.project.test_your_math.operation.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class provides a REST API to POST the attempts from users.
 */
@RestController
@RequestMapping("/results")
final class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;

    @Autowired
    MultiplicationResultAttemptController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt multiplicationResultAttempt) {
        boolean isCorrect = multiplicationService.checkAttempt(multiplicationResultAttempt);
        MultiplicationResultAttempt attemptCopy = new MultiplicationResultAttempt(
                multiplicationResultAttempt.getUser(),
                multiplicationResultAttempt.getMultiplication(),
                multiplicationResultAttempt.getResultAttempt(),
                isCorrect
        );
        return ResponseEntity.ok(attemptCopy);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<MultiplicationResultAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(
                multiplicationService.getStatsForUser(alias)
        );
    }

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    ResponseEntity<List<User>> getLeaderboard() {
        return ResponseEntity.ok(
                multiplicationService.getLeaderboardForUsers()
        );
    }

}
