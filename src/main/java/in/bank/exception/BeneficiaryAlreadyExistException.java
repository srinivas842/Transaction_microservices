package in.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.CONFLICT)
public class BeneficiaryAlreadyExistException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    public BeneficiaryAlreadyExistException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

}
