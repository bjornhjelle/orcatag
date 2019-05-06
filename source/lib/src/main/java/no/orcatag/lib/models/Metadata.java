package no.orcatag.lib.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by bjorn on 12/10/2018.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Metadata {
    private Double latitude;
    private Double longitude;
    private String area;
    private String license;
}
