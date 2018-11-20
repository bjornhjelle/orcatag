package no.orcatag.repositories;

import no.orcatag.models.Folder;
import no.orcatag.models.StoredFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<StoredFolder, Long> {

    List<StoredFolder> findByName(String folderName);

}