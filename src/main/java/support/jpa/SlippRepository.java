package support.jpa;

import java.io.Serializable;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SlippRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
    default T getOne(ID id) {
        T result = findOne(id);
        
        if (result == null) {
            throw new EmptyResultDataAccessException(1);
        }
        
        return result;
    }
}
