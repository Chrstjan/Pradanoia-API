package Pradanoia.payloads.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer id;

    @NotBlank
    @Size(min = 3, message = "category title must be at least 3 characters")
    private String title;
}
