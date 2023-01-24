package ExternalHospital.ExternalHospital.Utilities;

import org.modelmapper.ModelMapper;

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
