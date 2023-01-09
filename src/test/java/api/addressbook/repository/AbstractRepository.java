package api.addressbook.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractRepository {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    long  repositoryCount = 0;
}
