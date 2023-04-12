package microservices.project.test_your_math.operation.controller;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class implements a REST API for our Multiplication application.
 */
@RestController
@RequestMapping("/multiplications")
final class OperationController {

    private final OperationService operationService;

    @Autowired
    public OperationController(final OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/random")
    Operation getRandomMultiplication() {
        return operationService.createRandomOperation();
    }

}
