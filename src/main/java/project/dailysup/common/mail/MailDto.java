package project.dailysup.common.mail;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;



}
