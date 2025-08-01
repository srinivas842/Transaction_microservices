package in.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeneficiaryDoesNotExistException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public BeneficiaryDoesNotExistException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

