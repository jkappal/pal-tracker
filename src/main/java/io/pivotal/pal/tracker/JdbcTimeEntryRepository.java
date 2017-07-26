package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.*;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
        TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);
        jdbcTemplate.update(
                "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES ( ?, ?, ?, ?)",
                timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours()
        );
        platformTransactionManager.commit(status);


        Map<String, Object> foundEntry = jdbcTemplate.queryForMap("Select * from time_entries where project_id = " + timeEntry.getProjectId() + " and user_id =" + timeEntry.getUserId());

        timeEntry.setId((Long) foundEntry.get("id"));
        return timeEntry;
    }

    @Override
    public TimeEntry find(long l) {


        List<Map<String, Object>> list = jdbcTemplate.queryForList("Select * from time_entries where id = ?", l);
        TimeEntry timeEntry = new TimeEntry();
        if (list.isEmpty()) {
            return null;
        } else {
            Map<String, Object> row = list.get(0);
            timeEntry.setId(((Long) row.get("id")).intValue());
            timeEntry.setDate(((java.sql.Date) row.get("date")).toLocalDate());
            timeEntry.setHours(((Integer) row.get("hours")).intValue());
            timeEntry.setProjectId(((Long) row.get("project_id")).intValue());
            timeEntry.setUserId(((Long) row.get("user_id")).intValue());
        }

        return timeEntry;
    }

    @Override
    public List<TimeEntry> list() {

        ArrayList<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();

        List<Map<String, Object>> list = jdbcTemplate.queryForList("Select * from time_entries");
        for (Map<String, Object> row : list) {
            TimeEntry timeEntry = new TimeEntry();
            timeEntry.setId(((Long) row.get("id")).intValue());
            timeEntry.setDate(((java.sql.Date) row.get("date")).toLocalDate());
            timeEntry.setHours(((Integer) row.get("hours")).intValue());
            timeEntry.setProjectId(((Long) row.get("project_id")).intValue());
            timeEntry.setUserId(((Long) row.get("user_id")).intValue());

            timeEntryList.add(timeEntry);
        }

        return timeEntryList;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
        TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);
        jdbcTemplate.update(
                "UPDATE time_entries set project_id =  ?, user_id = ?, date = ?, hours = ? where id = ?",
                timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours(), id
        );
        platformTransactionManager.commit(status);


        Map<String, Object> foundEntry = jdbcTemplate.queryForMap("Select * from time_entries where project_id = " + timeEntry.getProjectId() + " and user_id =" + timeEntry.getUserId());

        timeEntry.setId((Long) foundEntry.get("id"));
        return timeEntry;
    }

    @Override
    public void delete(long l) {
        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
        TransactionStatus status = platformTransactionManager.getTransaction(paramTransactionDefinition);
        jdbcTemplate.update(
                "DELETE from time_entries where id = ?", l
        );
        platformTransactionManager.commit(status);

        return;
    }
}
