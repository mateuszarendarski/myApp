package app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CalculationsDto {

    @JsonProperty("followers")
    private Long followers;

    @JsonProperty("public_repos")
    private Long publicRepos;

}
