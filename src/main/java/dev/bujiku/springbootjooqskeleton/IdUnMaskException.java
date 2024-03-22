package dev.bujiku.springbootjooqskeleton;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception that will be thrown if an ID cannot be unmasked
 *
 * @author Newton Bujiku
 * @since 2024
 */

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdUnMaskException extends RuntimeException {
}
