package app.repository;

import app.entity.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GithubUser, Long> {

    GithubUser findByLogin(String login);
}
