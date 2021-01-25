package app.service;

import app.entity.GithubUser;
import app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public GithubUser getUserByLogin(String login) {
        return repo.findByLogin(login);
    }

    public void save(GithubUser user) {
        repo.saveAndFlush(user);
    }
}
