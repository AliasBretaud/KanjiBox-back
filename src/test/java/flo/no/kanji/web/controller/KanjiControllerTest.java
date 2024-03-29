package flo.no.kanji.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flo.no.kanji.business.model.Kanji;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("classpath:testapi.properties")
@AutoConfigureMockMvc
public class KanjiControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
    public void testSearchKanjiOk() throws Exception {
        mockMvc.perform(get("/kanjis?search=話"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].translations[0]", is("tale")));
    }

	@Test
	public void testGetKanjiByIdOk() throws Exception {
		mockMvc.perform(get("/kanjis/188"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("value", is("口")));
	}

	@Test
	public void testGetKanjiByIdKo() throws Exception {
		mockMvc.perform(get("/kanjis/-1"))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testPostKanjiOk1() throws Exception {
		var kanji = new Kanji("風");
		mockMvc.perform(post("/kanjis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(kanji)))
        	.andExpect(status().isOk());
	}
	
	@Test
	public void testPostKanjiOk2() throws Exception {
		var kanji = Kanji.builder()
				.value("古")
				.kunYomi(List.of("ふる.い"))
				.onYomi(List.of("コ"))
				.translations(List.of("Test"))
				.build();
		mockMvc.perform(post("/kanjis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(kanji)))
        	.andExpect(status().isOk());
	}
	
	@Test
	public void testPostKanjiKo1() throws Exception {
		mockMvc.perform(post("/kanjis")
				.content("")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void testPostKanjiKo2() throws Exception {
		var body = "{}";
		mockMvc.perform(post("/kanjis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testPostKanjiKo3() throws Exception {
		var kanji = Kanji.builder()
				.value("高")
				.kunYomi(List.of("tst"))
				.onYomi(List.of("コウ"))
				.translations(List.of("Test"))
				.build();
		mockMvc.perform(post("/kanjis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(kanji)))
        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testPostKanjiKo4() throws Exception {
		var kanji = Kanji.builder()
				.value("高")
				.kunYomi(List.of("たか.い"))
				.onYomi(List.of("tst"))
				.translations(List.of("Test"))
				.build();
		mockMvc.perform(post("/kanjis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(kanji)))
        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testPostKanjiKo5() throws Exception {
		var kanji = new Kanji("");
		mockMvc.perform(post("/kanjis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(kanji)))
        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testPostKanjiKo6() throws Exception {
		var kanji = Kanji.builder()
				.value("kou")
				.kunYomi(List.of("たかい"))
				.onYomi(List.of("コウ"))
				.translations(List.of("Test"))
				.build();
		mockMvc.perform(post("/kanjis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(kanji)))
        	.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testPatchKanji() throws Exception {
		var translationsPatch = "{\"translations\": [\"test patch\"]}";
		mockMvc.perform(patch("/kanjis/84")
						.contentType(MediaType.APPLICATION_JSON)
				.content(translationsPatch))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(84)))
		.andExpect(jsonPath("$.value", is("学")))
		.andExpect(jsonPath("$.translations[0]", is("test patch")));
	}
	
	@Test
	public void testPatchKanjiKo() throws Exception {
		var translationsPatch = "{\"id\": 85, \"\"translations\": [\"test patch\"]}";
		mockMvc.perform(patch("/kanjis/84")
				.contentType("application/json-patch+json")
				.content(translationsPatch))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testPatchKanjiKo2() throws Exception {
		var translationsPatch = "{\"translations\": [\"test patch\"]}";
		mockMvc.perform(patch("/kanjis/-1")
				.contentType("application/json-patch+json")
				.content(translationsPatch))
		.andExpect(status().isNotFound());
	}
}
