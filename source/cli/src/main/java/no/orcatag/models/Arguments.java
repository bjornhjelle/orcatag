package no.orcatag.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by bjorn on 07/10/2018.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Arguments {
    private String username;
    private String areaname;
    private double latitude;
    private double longitude;
    private String directory;
}
