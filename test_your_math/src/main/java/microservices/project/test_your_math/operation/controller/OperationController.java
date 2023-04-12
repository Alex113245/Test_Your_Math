package microservices.project.test_your_math.operation.controller;

import microservices.project.test_your_math.operation.domain.Operation;
import microservices.project.test_your_math.operation.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class implements a REST API for our Operation application.
 */
@RestController
@RequestMapping("/operations")
final class OperationController {

    private final OperationService operationService;

    @Autowired
    public OperationController(final OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/random")
    Operation getRandomOperation() {
        return operationService.createRandomOperation();
    }

}
