package pe.com.peruapps.vehiclesmaintenanceapi.shared.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageResult<T> {
  private List<T> content;
  private long totalElements;
  private int totalPages;
}
