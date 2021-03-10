package com.woodcock.events;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class EventControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  EventRepository eventRepository;

  @Test
  @DisplayName("이벤트를 등록하고 응답을 확인한다.")
  void createEvent() throws Exception {
    Event event = Event.builder()
        .name("spring")
        .description("REST API Development with Spring")
        .beginEnrollmentDateTime(LocalDateTime.of(2021, 3, 10, 13, 21))
        .closeEnrollmentDateTime(LocalDateTime.of(2021, 3, 11, 13, 21))
        .beginEventDateTime(LocalDateTime.of(2021, 3, 12, 13, 21))
        .endEventDateTime(LocalDateTime.of(2021, 4, 13, 13, 21))
        .basePrice(100)
        .maxPrice(200)
        .location("서울숲")
        .build();
    event.setId(1L);
    when(eventRepository.save(any())).thenReturn(event);

    mockMvc.perform(post("/api/events")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaTypes.HAL_JSON)
          .content(objectMapper.writeValueAsString(event)))
        .andDo(print())
        .andExpect(jsonPath("id").exists())
        .andExpect(header().exists(HttpHeaders.LOCATION))
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        .andExpect(status().isCreated());
  }
}
