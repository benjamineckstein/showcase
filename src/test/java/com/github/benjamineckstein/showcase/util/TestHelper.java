package com.github.benjamineckstein.showcase.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelper {

  public static <T> T getBody(ResponseEntity<T> response) {
    assertThat(response.getBody()).isNotNull();
    return response.getBody();
  }

  public static <T> T getBody(ResponseEntity<T> response, HttpStatus expectedStatus) {
    assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
    assertThat(response.getBody()).isNotNull();
    return response.getBody();
  }

  public static void clearDatabase(DataSource datasource) throws SQLException {
    Connection c = datasource.getConnection();
    Statement s = c.createStatement();

    // Disable FK
    s.execute("SET REFERENTIAL_INTEGRITY FALSE");

    // Find all tables and truncate them
    Set<String> tables = new HashSet<String>();
    ResultSet rs =
        s.executeQuery(
            "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='PUBLIC'");
    while (rs.next()) {
      tables.add(rs.getString(1));
    }
    rs.close();
    for (String table : tables) {
      s.executeUpdate("TRUNCATE TABLE " + table);
    }

    // Idem for sequences
    Set<String> sequences = new HashSet<String>();
    rs =
        s.executeQuery(
            "SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='PUBLIC'");
    while (rs.next()) {
      sequences.add(rs.getString(1));
    }
    rs.close();
    for (String seq : sequences) {
      s.executeUpdate("ALTER SEQUENCE " + seq + " RESTART WITH 1");
    }

    // Enable FK
    s.execute("SET REFERENTIAL_INTEGRITY TRUE");
    s.close();
    c.close();
  }
}
