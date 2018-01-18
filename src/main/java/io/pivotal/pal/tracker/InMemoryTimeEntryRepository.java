package io.pivotal.pal.tracker;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private HashMap<Long, TimeEntry > repo = new HashMap<Long,TimeEntry>();

    public TimeEntry create(TimeEntry timeEntry) {


        if (timeEntry.getId() == 0l) {
            timeEntry.setId(repo.size() + 1);
        }
        repo.put(timeEntry.getId(),timeEntry);
        return timeEntry;
    }
    public TimeEntry find(long l) {
        return repo.get(l);
    }

    public List<TimeEntry> list() {
        List<TimeEntry> lst = new ArrayList<TimeEntry>();
        Collection<TimeEntry> co = repo.values();
        for (TimeEntry t : co) {
            lst.add(t);
        }

        return lst;
    }

    public TimeEntry update(long id, TimeEntry any) {
        any.setId(id);
        repo.put(id,any);
        return repo.get(id);
    }

    public void delete(long l) {
        TimeEntry any = repo.get(l);
        repo.remove(l);
    }
}
