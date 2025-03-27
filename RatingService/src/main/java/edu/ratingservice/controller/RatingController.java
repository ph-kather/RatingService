package edu.ratingservice.controller;


import edu.ratingservice.entity.RatingRequest;
import edu.ratingservice.entity.RatingResponse;
import io.netty.util.internal.ThreadLocalRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @PostMapping
    public Mono<RatingResponse> submitRating(@RequestBody RatingRequest request) {
        if (request.getRating() < 0 || request.getRating() > 5) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating muss zwischen 0 und 5 sein."));
        }
        RatingResponse response = new RatingResponse(UUID.randomUUID(), request.getRating(), Date.from(
            Instant.now()));
        return Mono.just(response);
    }

    @GetMapping("/mostRecent")
    public Flux<RatingResponse> getMostRecent() {
        int count = ThreadLocalRandom.current().nextInt(3, 420);
        return Flux.range(1, count)
            .delayElements(Duration.ofMillis(10))// For some delay :)
            .map(index ->
                new RatingResponse(UUID.randomUUID(),
                    ThreadLocalRandom.current().nextInt(0, 6),
                    Date.from(Instant.now())));
    }

    @GetMapping("/{id}/getOriginalVersion")
    public Mono<RatingResponse> getOriginalersion(@PathVariable UUID id) {
        return Mono.just(new RatingResponse(id, ThreadLocalRandom.current().nextInt(0, 6), getRandomDate()));
    }

    @GetMapping("/latest")
    public Mono<RatingResponse> getLatest() {
        return Mono.just(new RatingResponse(UUID.randomUUID(), ThreadLocalRandom.current().nextInt(0, 6), Date.from(Instant.now())));
    }

    private Date getRandomDate() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 30);

        long startMillis = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endMillis = endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
        return Date.from(Instant.ofEpochMilli(randomMillis));
    }
}
