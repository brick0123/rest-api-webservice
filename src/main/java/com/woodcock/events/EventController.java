package com.woodcock.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

  private final EventRepository eventRepository;

  @PostMapping
  public ResponseEntity<Object> createEvent(@RequestBody Event event) {
    Event saveEvent = eventRepository.save(event);

    URI createdUri = linkTo(EventController.class).slash(saveEvent.getId()).toUri();
    return ResponseEntity.created(createdUri).body(event);
  }

}
