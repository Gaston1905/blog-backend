package blog.backend.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@Data
public class UserDto {

  private int id;

  @NotEmpty(message = "Introduzca un nombre")
  @Size(min = 4, message = "El nombre debe tener más de 4 caracteres.")
  private String name;

  @Email
  @NotEmpty
  private String email;

  @NotEmpty
  @Size(min = 8, max = 50, message = "Su password debe tener más de 8 caracteres.")
  @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,50}$", message = "La contraseña debe tener al menos una letra mayúscula y 1 carácter especial y 1 letra minúscula y 1 dígito")
  private String password;

  @Size(min = 4, message = "about debe tener más de 4 caracteres")
  @NotEmpty
  private String about;

  @JsonIgnore()
  public String getPassword() {
    return this.password;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }
}
