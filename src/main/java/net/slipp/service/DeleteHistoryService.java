package net.slipp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import net.slipp.domain.DeleteHistory;
import net.slipp.domain.DeleteHistoryRepository;

@Service("deleteHistoryService")
public class DeleteHistoryService {
    @Resource(name = "deleteHistoryRepository")
    private DeleteHistoryRepository deleteHistoryRepository;
    
    @Transactional
    public void saveAll(List<DeleteHistory> deleteHistories) {
        for (DeleteHistory deleteHistory : deleteHistories) {
            deleteHistoryRepository.save(deleteHistory);
        }
    }
}
