package libraryCatalog.businessLogicInterfaces;

import libraryCatalog.models.Magazine;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;

public interface MagazineBusinessLogicInterface {
    public void createMagazine(Magazine magazine) throws IOException;
    public void getAllMagazine(Iterable<Magazine> magazines, Model model);
    public void getMagazineDetails(Optional<Magazine> magazine , Model model);
    public void editMagazine(Magazine magazine) throws IOException;
    public void deleteMagazine(Magazine magazine);
    public boolean magazineExistByID(Long id);
    public void searchMagazine(Iterable<Magazine> magazines,Model model);
}
