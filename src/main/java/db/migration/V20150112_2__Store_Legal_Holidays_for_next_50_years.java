package db.migration;

import com.realdolmen.flyway.demo.model.LegalHoliday;
import com.realdolmen.flyway.demo.util.DateCalculcationsUtil;
import com.realdolmen.flyway.demo.util.FetchLegalHolidaysViaWebserviceUtil;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KDAAU95 on 28/11/2014.
 */
public class V20150112_2__Store_Legal_Holidays_for_next_50_years implements JdbcMigration {

    private final static int YEARS = 50;
    private final static String SELECT_STATEMENT = "SELECT date FROM LEGAL_HOLIDAY where date = ?";
    private final static String INSERT_STATEMENT = "INSERT INTO legal_holiday(" +
            " date, description_nl, description_fr, description_de, description_en)" +
            " VALUES (?, ?, ?, ?, ?)";

    public void migrate(Connection connection) throws Exception {
        List<LegalHoliday> legalHolidays = lookupLegalHolidaysForNextYears();
        saveNonExistingHolidays(connection, legalHolidays);
    }

    private void saveNonExistingHolidays(Connection connection, List<LegalHoliday> legalHolidays) throws SQLException {
        for(LegalHoliday holiday : legalHolidays) {
            if( !isExistingHoliday(connection, holiday) ) {
                insertLegalHoliday(connection, holiday);
            }
        }
    }

    private void insertLegalHoliday(Connection connection, LegalHoliday holiday) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STATEMENT);
        preparedStatement.setDate(1, new Date(holiday.getDate().getTime()));
        preparedStatement.setString(2, holiday.getDescriptionNl());
        preparedStatement.setString(3, holiday.getDescriptionFr());
        preparedStatement.setString(4, holiday.getDescriptionDe());
        preparedStatement.setString(5, holiday.getDescriptionEn());
        preparedStatement.execute();
        preparedStatement.close();
    }

    private boolean isExistingHoliday(Connection connection, LegalHoliday holiday) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATEMENT);
        preparedStatement.setDate(1, new Date(holiday.getDate().getTime()));
        return preparedStatement.executeQuery() == null;
    }

    private List<LegalHoliday> lookupLegalHolidaysForNextYears() {
        int currentYear = DateCalculcationsUtil.getCurrentYear();
        List<LegalHoliday> legalHolidays = new ArrayList<LegalHoliday>();
        for( int y = currentYear; y<=currentYear + YEARS; y++) {
            lookupLegalHolidaysForYear(legalHolidays, y);
        }
        return legalHolidays;
    }

    private void lookupLegalHolidaysForYear(List<LegalHoliday> legalHolidays, int y) {
        FetchLegalHolidaysViaWebserviceUtil searchUtil = new FetchLegalHolidaysViaWebserviceUtil(y);
        searchUtil.fetchVacationsForYear();
        legalHolidays.addAll(searchUtil.getLegalHolidayList());
    }
}
