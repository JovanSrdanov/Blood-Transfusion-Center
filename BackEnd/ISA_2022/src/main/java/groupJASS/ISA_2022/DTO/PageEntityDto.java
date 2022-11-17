package groupJASS.ISA_2022.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PageEntityDto<T> {
    public T content;
    public int count;

    public PageEntityDto(T content, int count) {
        this.content = content;
        this.count = count;
    }
}
