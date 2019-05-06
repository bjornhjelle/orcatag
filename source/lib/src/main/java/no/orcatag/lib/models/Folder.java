package no.orcatag.lib.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by bjorn on 12/10/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Folder {
    private List<Picture> pictures;
    private String foldername;
}
