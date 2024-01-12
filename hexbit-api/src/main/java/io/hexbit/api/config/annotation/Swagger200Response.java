package io.hexbit.api.config.annotation;

import io.hexbit.core.common.response.CustomResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Success", content = {
                @Content(
                        schema = @Schema(implementation = CustomResponse.class),
                        examples = {
                                @ExampleObject(value = "{\"status\":{\"number\":200,\"code\":\"OK\",\"message\":\"OK\"},\"data\":{}}")
                        }
                )
        })
})
public @interface Swagger200Response {
}
