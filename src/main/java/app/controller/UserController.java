package app.controller;

import app.dto.CalculationsDto;
import app.dto.GithubUserDto;
import app.entity.GithubUser;
import app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class UserController {

    private final UserService service;
    private final HttpClient client = HttpClient.newHttpClient();
    private static final String GITHUB_URL = "https://api.github.com/users/";

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users/{login}")
    public String getUser(@PathVariable("login") String login) throws IOException, InterruptedException {
        if (login.isEmpty()) return "";

        HttpRequest request = HttpRequest.newBuilder(
                URI.create(GITHUB_URL + login))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) return response.body().toString();

        ObjectMapper mapper = new ObjectMapper();
        GithubUserDto githubUserDto = mapper.readValue(response.body().toString(), GithubUserDto.class);
        CalculationsDto calculationsDto = mapper.readValue(response.body().toString(), CalculationsDto.class);
        double calculation = getUserCalculation(calculationsDto);
        githubUserDto.setCalculations(calculation);
        createOrUpdateUser(login);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(githubUserDto);
    }

    private double getUserCalculation(CalculationsDto calculationsDto) {
        if (calculationsDto.getFollowers().equals(0L)) return 0;
        double divide = (double) 6 / calculationsDto.getFollowers();
        long sum = 2 + calculationsDto.getPublicRepos();
        return divide * sum;
    }

    private void createOrUpdateUser(String login) {
        GithubUser user = service.getUserByLogin(login);
        if (user == null) {
            user = new GithubUser(login, 1);
        } else {
            user.incrementRequestCount();
        }
        service.save(user);
    }
}
