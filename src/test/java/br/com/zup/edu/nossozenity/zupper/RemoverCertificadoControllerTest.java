package br.com.zup.edu.nossozenity.zupper;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class RemoverCertificadoControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private KudoRepository kudoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CertificadoRepository certificadoRepository;

    @Autowired
    private ZupperRepository zupperRepository;

    @BeforeEach
    void setUp() {
        certificadoRepository.deleteAll();
        zupperRepository.deleteAll();
    }

    @Test
    @DisplayName("deve remover um certificado")
    void deveRemoverUmCertificado() throws Exception {
        Zupper zupper = new Zupper(
                "Denes",
                "Dev Back-end",
                LocalDate.now(),
                "jose@zup.com.br");

        zupperRepository.save(zupper);

        Certificado certificado = new Certificado(
                "java",
                "ZupEdu",
                "htttp://www.curso.com.br",
                zupper,
                TipoCertificado.CERTIFICACAO_OFICIAL);

        certificadoRepository.save(certificado);

        MockHttpServletRequestBuilder request = delete("/certificados/{id}", certificado.getId()).
                contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(
                        status().isNoContent()
                );

        assertFalse(certificadoRepository.existsById(certificado.getId()),"não deve existir um registro para esse id");

    }

    @Test
    @DisplayName("não deve remover um certificado não cadastrado")
    void naoDeveRemoverUmCertificadoNaoCadastrado() throws Exception {
        MockHttpServletRequestBuilder request = delete("/certificados/{id}", Integer.MAX_VALUE).
                contentType(MediaType.APPLICATION_JSON);

        Exception resolvedException = mockMvc.perform(request)
                .andExpect(
                        status().isNotFound()
                )
                .andReturn().getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());

        ResponseStatusException exception = (ResponseStatusException) resolvedException;
        assertEquals("Certificado não cadastrado.", exception.getReason());
    }

}