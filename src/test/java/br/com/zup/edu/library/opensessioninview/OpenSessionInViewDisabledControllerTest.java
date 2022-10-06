package br.com.zup.edu.library.opensessioninview;

import br.com.zup.edu.library.base.SpringBootIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@TestPropertySource(properties = {
        "spring.jpa.open-in-view = false",
        "spring.datasource.hikari.maximum-pool-size = 1",
        "spring.datasource.hikari.connection-timeout = 500"
})
class OpenSessionInViewDisabledControllerTest extends SpringBootIntegrationTest {
    @Autowired
    private DataSource dataSource;


    @Test
    @DisplayName("Transactional Endpoint with data access")
    void name3() throws Exception {

        try (Connection connection = dataSource.getConnection()) {

            mockMvc.perform(
                            get("/transactional-endpoint-with-data-access")
                    )
                    .andExpectAll(
                            status().is5xxServerError(),
                            jsonPath(
                                    "$.detail",
                                    containsString("JDBCConnectionException: Unable to acquire JDBC Connection")
                            )
                    );
        }

    }

    @Test
    @DisplayName("Not Transactional Endpoint with data access")
    void n3() throws Exception {

        try (Connection connection = dataSource.getConnection()) {

            mockMvc.perform(
                            get("/not-transactional-endpoint-with-data-access")
                    )
                    .andExpectAll(
                            status().is5xxServerError(),
                            jsonPath(
                                    "$.detail",
                                    containsString("JDBCConnectionException: Unable to acquire JDBC Connection")
                            )
                    );
        }

    }

    @Test
    @DisplayName("Transactional Endpoint without data access")
    void name6() throws Exception {

        try (Connection connection = dataSource.getConnection()) {

            mockMvc.perform(
                    get("/transactional-endpoint-without-data-access")
            ).andExpectAll(
                    status().is5xxServerError(),
                    jsonPath(
                            "$.detail",
                            containsString("JDBCConnectionException: Unable to acquire JDBC Connection")
                    )
            );

        }
    }

    @Test
    @DisplayName("Transactional Read-Only Endpoint without data access")
    void n() throws Exception {

        try (Connection connection = dataSource.getConnection()) {

            mockMvc.perform(
                    get("/transactional-readonly-endpoint-without-data-access")
            ).andExpectAll(
                    status().is5xxServerError(),
                    jsonPath(
                            "$.detail",
                            containsString("JDBCConnectionException: Unable to acquire JDBC Connection")
                    )
            );

        }
    }


    @Test
    @DisplayName("Not Transactional Endpoint without data access")
    void name4() throws Exception {

        try (Connection connection = dataSource.getConnection()) {
            mockMvc.perform(
                            get("/not-transactional-endpoint-without-data-access")
                    )
                    .andExpect(
                            status().isOk()
                    );
        }
    }
}