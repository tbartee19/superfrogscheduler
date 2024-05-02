package edu.tcu.cs.superfrogscheduler.system;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import edu.tcu.cs.superfrogscheduler.model.SpiritDirectorEvent;
import edu.tcu.cs.superfrogscheduler.repository.SpiritDirectorEventRepository;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;

@Service
@Transactional
public class SpiritDirectorEventService {
    private final SpiritDirectorEventRepository spiritDirectorEventRepository;

    public SpiritDirectorEventService(SpiritDirectorEventRepository spiritDirectorEventRepository) {
        this.spiritDirectorEventRepository = spiritDirectorEventRepository;
    }

    public List<SpiritDirectorEvent> findAll(){
        return this.spiritDirectorEventRepository.findAll();
    }

    public SpiritDirectorEvent save(SpiritDirectorEvent newEvent){
        return this.spiritDirectorEventRepository.save(newEvent);
    }

    public SpiritDirectorEvent update(Integer id, SpiritDirectorEvent update){
        return this.spiritDirectorEventRepository.findById(id)
                .map(oldEvent -> {
                    oldEvent.setEventTitle(update.getEventTitle());
                    oldEvent.setStartDate(update.getStartDate());
                    oldEvent.setStartTime(update.getStartTime());
                    oldEvent.setEndDate(update.getEndDate());
                    oldEvent.setEndTime(update.getEndTime());
                    oldEvent.setRecurrenceStart(update.getRecurrenceStart());
                    oldEvent.setRecurrenceEnd(update.getRecurrenceEnd());
                    return this.spiritDirectorEventRepository.save(oldEvent);
                })
                .orElseThrow(() -> new ObjectNotFoundException("spiritdirectorevent", id));
    }

    public void delete(Integer id) {
        this.spiritDirectorEventRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("spiritdirectorevent", id));
        this.spiritDirectorEventRepository.deleteById(id);
    }

    public SpiritDirectorEvent findById(int id) {
        return this.spiritDirectorEventRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("spiritdirectorevent", id));
    }
}