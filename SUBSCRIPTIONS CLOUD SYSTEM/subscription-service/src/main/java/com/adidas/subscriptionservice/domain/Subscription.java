package com.adidas.subscriptionservice.domain;

import java.time.Year;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.adidas.subscriptionservice.persistence.PersistableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Subscription extends PersistableEntity {

    @NotBlank(message = "The subscription EMAIL must be defined.")
//    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "The EMAIL format must follow the standards EMAIL-10 or EMAIL-13.")
    private String email;

//    @NotBlank(message = "The firstname title must be defined.")
    private String firstname;

//    @NotBlank(message = "The subscription author must be defined.")
    private String gender;

    @PastOrPresent(message = "The subscription cannot have been published in the future.")
    private Year dateofbirth;

    @NotNull(message = "The subscription flagconsent must be defined.")
    //@Positive(message = "The subscription price must be greater than zero.")
    private Double flagconsent;

    private String idcampaign;
}
