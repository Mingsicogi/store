package mins.study.store.app.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicationException extends RuntimeException {

    public DuplicationException(String msg) {
        super(msg);
    }
}
