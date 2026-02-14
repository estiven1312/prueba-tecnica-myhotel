package pe.com.peruapps.vehiclesmaintenanceapi.shared.mapper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pe.com.peruapps.vehiclesmaintenanceapi.shared.dto.PaginatedFilter;

@Component
public class PageRequestMapper {

  public <T> Pageable toPageRequest(PaginatedFilter<T> filter) {
    if (filter == null) {
      return PageRequest.of(0, 10);
    }

    int page = Math.max(0, filter.getPage());
    int size = filter.getSize() > 0 ? filter.getSize() : 10;

    if (filter.getSortBy() == null || filter.getSortBy().isBlank()) {
      return PageRequest.of(page, size);
    }

    Sort.Direction direction =
        filter.getOrder() == PaginatedFilter.Order.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;

    return PageRequest.of(page, size, Sort.by(direction, filter.getSortBy()));
  }
}
