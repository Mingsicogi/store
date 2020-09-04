package mins.study.store.app.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "member name is mandatory field")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
