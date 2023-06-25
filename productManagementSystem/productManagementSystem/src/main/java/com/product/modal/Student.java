package com.product.modal;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.logging.log4j.message.Message;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int sid;


    @NotBlank(message = "name should not blank" )
    String name;
    @Email(regexp = "[0-9a-zA-Z]",message = "invalid email address")
    String email;
    @Pattern(regexp = "^(male|female|others)")
    String gender;

    String password;



    @OneToOne(cascade = CascadeType.ALL,mappedBy = "student")
    @JoinColumn(referencedColumnName = "sid")
    Address address;
}