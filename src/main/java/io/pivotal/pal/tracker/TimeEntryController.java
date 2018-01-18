package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController ("/")
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private  CounterService counter;
    private  GaugeService gauge;


    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            CounterService counter,
            GaugeService gauge
    ) {
        this.timeEntryRepository = timeEntriesRepo;
        this.counter = counter;
        this.gauge = gauge;
    }



    @RequestMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry){
        timeEntryRepository.create(timeEntry);

        return new ResponseEntity<>(timeEntry,
                HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if (null!=timeEntry) {
            return new ResponseEntity<>(timeEntry,
                    HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(timeEntry,
                    HttpStatus.NOT_FOUND);

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<List<TimeEntry>> (timeEntryRepository.list(),
                HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(id, expected);
        if (timeEntry == null) {
            return new ResponseEntity( timeEntry, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity( timeEntry, HttpStatus.OK);

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        return new ResponseEntity( "calling delete result: ", HttpStatus.NO_CONTENT);
    }


}
