package app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "github_user", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"login"})
})
public class GithubUser extends BaseEntity {

    @NotEmpty
    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private Integer requestCount;

    public GithubUser() {
    }

    public GithubUser(@NotEmpty String login, Integer requestCount) {
        this.login = login;
        this.requestCount = requestCount;
    }

    public void incrementRequestCount() {
        setRequestCount(requestCount + 1);
    }
}
