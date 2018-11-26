package no.orcatag.rest.repositories;

import no.orcatag.rest.models.StoredPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<StoredPicture, Long> {

    List<StoredPicture> findByFolderId(Long folderId);
    StoredPicture findByFolderIdAndFilename(Long folderId, String filename);

}