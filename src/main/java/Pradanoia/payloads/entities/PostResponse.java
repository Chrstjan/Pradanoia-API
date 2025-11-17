package Pradanoia.payloads.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
    private List<?> content;     //Temp <?>
    private int pageNum;
    private int pageSize;
    private long totalElements; //Records
    private int totalPages;
    private boolean lastPage;
}
