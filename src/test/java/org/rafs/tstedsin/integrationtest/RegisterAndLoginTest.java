package org.rafs.tstedsin.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.rafs.tstedsin.DTOs.Login.LoginRequestDTO;
import org.rafs.tstedsin.DTOs.Login.LoginResponseDTO;
import org.rafs.tstedsin.Enum.Role;
import org.rafs.tstedsin.Model.Client;
import org.rafs.tstedsin.config.TestConfig;
import org.rafs.tstedsin.testcontainers.AbstractIntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class RegisterAndLoginTest extends AbstractIntegrationTest {


    private static RequestSpecification specification;
    private static Client client;
    private static LoginRequestDTO loginClientRequestDTO;
    private static LoginRequestDTO loginAdminRequestDTO;

    @BeforeAll
    public static void setUp(){

        client = new Client("testeUsername", "senhateste**", "Cliente Teste", List.of());
        loginClientRequestDTO = new LoginRequestDTO(client.getUsername(), client.getPassword());
        loginAdminRequestDTO = new LoginRequestDTO("Leilaleila", "adminLeila**");


        specification = new RequestSpecBuilder()
                .setPort(TestConfig.SERVER_PORT)
                .setContentType(TestConfig.MEDIA_TYPE_JSON)
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ALLOWED_DOMAIN)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void createClientTestSuccess(){
        Client content = given()
                .spec(specification)
                .basePath(TestConfig.BASE_PATH_CLIENT)
                .body(client)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .as(Client.class);

        assertNotNull(content.getId());
        assertNotNull(content.getPassword());
        assertNotEquals(client.getPassword(), content.getPassword());
        assertEquals(Role.ROLE_CLIENT, content.getRole());
        assertEquals(client.getUsername(), content.getUsername());
    }

    @Test
    @Order(2)
    public void loginClientTestSuccess(){
        LoginResponseDTO content = given()
                .spec(specification)
                .basePath(TestConfig.BASE_PATH_LOGIN)
                .body(loginClientRequestDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(LoginResponseDTO.class);

        assertNotNull(content.token());
        assertNotNull(content.idUser());
        assertEquals(content.username(), client.getUsername());
        assertEquals(content.role(), Role.ROLE_CLIENT);
        assertEquals(content.name(), client.getName());
    }

    @Test
    @Order(3)
    public void loginAdminTestSuccess(){
        LoginResponseDTO content = given()
                .spec(specification)
                .basePath(TestConfig.BASE_PATH_LOGIN)
                .body(loginAdminRequestDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(LoginResponseDTO.class);

        assertNotNull(content.token());
        assertEquals(content.idUser(), 3);
        assertEquals(content.username(), "Leilaleila");
        assertEquals(content.role(), Role.ROLE_ADMIN);
        assertEquals(content.name(), "Leila");
    }

}
