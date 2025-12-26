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
 * Valida se determinada String é um CEP válido no formato brasileiro (8 dígitos, com ou sem
 * hífen)
 */
@Pattern(regexp = "^(\\d{5}-\\d{3}|\\d{8})$", message = "CEP inválido")
@Documented
@Constraint(validatedBy = {})
@Target({FIELD})
@Retention(RUNTIME)
public @interface CEP {
  String message() default "CEP inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}

