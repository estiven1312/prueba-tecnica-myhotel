package pe.com.peruapps.vehiclesmaintenanceapi.shared.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginatedFilter<T> {
  T filter;
  int page;
  int size;
  String sortBy;
  Order order;

  public enum Order {
    ASC,
    DESC
  }
}
