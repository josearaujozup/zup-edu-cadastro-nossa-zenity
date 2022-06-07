package br.com.zup.edu.nossozenity.zupper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class ListarKudosRecebidosDoZupperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ZupperRepository zupperRepository;

    @Autowired
    private KudoRepository kudoRepository;

    private Zupper zupper;

    @BeforeEach
    void setUp() {
        kudoRepository.deleteAll();
        zupperRepository.deleteAll();
        this.zupper = new Zupper("Denes","Developer II", LocalDate.of(2022,03,07),
                "denes@email.com");
        zupperRepository.save(this.zupper);
    }

    @Test
    @DisplayName("Deve listar os kudos recebidos de um zupper cadastrado")
    void test1() throws Exception {
        Zupper zupperQueEnvia = new Zupper("Thiago", "Developer II", LocalDate.of(2022, 03, 07),
                "thiago@email.com");

        Kudo agradecimento = new Kudo(TipoKudo.AGRADECIMENTO, zupperQueEnvia, this.zupper);
        Kudo trabalhoEmEquipe = new Kudo(TipoKudo.TRABALHO_EM_EQUIPE, zupperQueEnvia, this.zupper);

        zupperRepository.save(zupperQueEnvia);
        kudoRepository.saveAll(List.of(agradecimento,trabalhoEmEquipe));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/zupper/{id}/kudos/recebidos",
                this.zupper.getId()).contentType(MediaType.APPLICATION_JSON);

        String payload = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<KudoResponse> kudos = mapper.readValue(payload, typeFactory.constructCollectionType(
                List.class,
                KudoResponse.class
        ));

        assertEquals(2,kudos.size());
        assertEquals(agradecimento.getNome().name().toLowerCase(),kudos.get(0).getNome());
        assertEquals(trabalhoEmEquipe.getNome().name().toLowerCase(),kudos.get(1).getNome());

        //implementação resposta do especialista
//        assertThat(response)
//                .hasSize(2)
//                .extracting("nome", "enviadoPor")
//                .contains(
//                        new Tuple(TipoKudo.ALEM_DAS_EXPECTATIVAS.name().toLowerCase(Locale.ROOT), jordi.getNome()),
//                        new Tuple(TipoKudo.EXCELENTE_MENTOR.name().toLowerCase(Locale.ROOT), jordi.getNome())
//                );

    }

    @Test
    @DisplayName("Deve devolver uma lista vazia caso o zupper não tenha kudos recebidos")
    void test2() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/zupper/{id}/kudos/recebidos",
                this.zupper.getId()).contentType(MediaType.APPLICATION_JSON);

        String payload = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        TypeFactory typeFactory = mapper.getTypeFactory();

        List<KudoResponse> kudos = mapper.readValue(payload, typeFactory.constructCollectionType(
                List.class,
                KudoResponse.class
        ));

        assertTrue(kudos.isEmpty());
    }

    @Test
    @DisplayName("Não deve listar os kudos de um zupper não cadastrado")
    void test3() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/zupper/{id}/kudos/recebidos",
                Integer.MAX_VALUE).contentType(MediaType.APPLICATION_JSON);

        Exception resolvedException = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                )
                .andReturn().getResolvedException();

        assertNotNull(resolvedException.getClass());
        assertEquals(ResponseStatusException.class,resolvedException.getClass());
        assertEquals("Zupper nao cadastrado", ((ResponseStatusException) resolvedException).getReason());
    }
}