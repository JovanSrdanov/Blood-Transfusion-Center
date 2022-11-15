package groupJASS.ISA_2022.Utilities;

import groupJASS.ISA_2022.DTO.BloodCenter.BloodCentarBasicInfoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtilities {
    public  static <S, T> List<T> mapList(List<S> source, Class<T> destClass, ModelMapper mapper)
    {
        return source
                .stream()
                .map(element -> mapper.map(element, destClass))
                .collect(Collectors.toList());
    }
}
