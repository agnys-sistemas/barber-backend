package br.com.barberbackend.compartilhado.validacao;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author jackson.mota Valida se determinada String é um número de telefone válido de 10 ou 11
 *     dígitos
 */
@Pattern(regexp = "^\\d{10}|\\d{11}$", message = "Número de telefone inválido")
@Documented
@Constraint(validatedBy = {})
@Target({FIELD})
@Retention(RUNTIME)
public @interface Telefone {
  String message() default "Número de telefone inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
