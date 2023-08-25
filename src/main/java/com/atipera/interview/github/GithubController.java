package com.atipera.interview.github;

import com.atipera.interview.github.dtos.RepositoryWithBranchAndShaDTO;
import com.atipera.interview.github.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class GithubController {
    private final GithubService service;

    @GetMapping(value = "/repositories")
    Flux<RepositoryWithBranchAndShaDTO> getRepositories(@RequestParam String user,
                                                        @RequestHeader("Accept") String acceptHeader) {
        if (acceptHeader.contains(MediaType.APPLICATION_XML_VALUE)) {
            throw new NotAcceptableApplicationException("Invalid accept header");
        }

        return service.getRepositoriesWithoutFork(user);
    }
}
