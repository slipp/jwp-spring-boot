package net.slipp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.slipp.domain.DeleteHistory;
import net.slipp.domain.DeleteHistoryRepository;

@Service("deleteHistoryService")
public class DeleteHistoryService {
    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(List<DeleteHistory> deleteHistories) {
        for (DeleteHistory deleteHistory : deleteHistories) {
            deleteHistoryRepository.save(deleteHistory);
        }
    }
}
