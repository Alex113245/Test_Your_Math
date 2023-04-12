package microservices.project.test_your_math.operation.controller;


import microservices.project.test_your_math.operation.domain.OperationResultAttempt;
import microservices.project.test_your_math.operation.domain.User;
import microservices.project.test_your_math.operation.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class provides a REST API to POST the attempts from users.
 */
@RestController
@RequestMapping("/results")
final class OperationResultAttemptController {

    private final OperationService operationService;


    @Autowired
    OperationResultAttemptController(final OperationService operationService){
        this.operationService=operationService;
    }
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<OperationResultAttempt> postResult(@RequestBody OperationResultAttempt operationResultAttempt) {
        boolean isCorrect = operationService.checkAttempt(operationResultAttempt);
        OperationResultAttempt attemptCopy = new OperationResultAttempt(
                operationResultAttempt.getUser(),
                operationResultAttempt.getOperation(),
                operationResultAttempt.getResultAttempt(),
                isCorrect
        );
        return ResponseEntity.ok(attemptCopy);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<OperationResultAttempt>> getStatistics(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(
                operationService.getStatsForUser(alias)
        );
    }

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    ResponseEntity<List<User>> getLeaderboard() {
        return ResponseEntity.ok(
                operationService.getLeaderboardForUsers()
        );
    }

}
