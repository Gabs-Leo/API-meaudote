package com.gabsleo.meaudote.repository;

import com.gabsleo.meaudote.entities.AdoptionAnimal;
import com.gabsleo.meaudote.entities.AppUser;
import com.gabsleo.meaudote.enums.Species;
import com.gabsleo.meaudote.repositories.AdoptionAnimalRepository;
import com.gabsleo.meaudote.repositories.AppUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AdoptionAnimalTests {
    @Autowired
    private AdoptionAnimalRepository adoptionAnimalRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void AdoptionAnimalRepository_Save_ReturnSavedAnimal() {
        AppUser appUser = new AppUser(
                "30276619064",
                "Rogério",
                "Senha123*",
                "rogerio@gmail.com",
                new Date(),
                "99980352186",
                "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                "https://imgv3.fotor.com/images/blog-cover-image/10-profile-picture-ideas-to-make-you-stand-out.jpg",
                false,
                "SP",
                "Franca"
        );
        AppUser savedAppUser = appUserRepository.save(appUser);

        AdoptionAnimal adoptionAnimal = new AdoptionAnimal(
            null,
            "Josué",
            "Adote!",
            2,
            false,
            "https://highlandcanine.com/wp-content/uploads/2019/08/pug.jpg",
            "Franca",
            "SP",
            1f,
            Species.DOG,
            savedAppUser
        );

        AdoptionAnimal savedAdoptionAnimal = adoptionAnimalRepository.save(adoptionAnimal);

        Assertions.assertThat(savedAppUser).isNotNull();
        Assertions.assertThat(savedAppUser.getCpf()).isNotEmpty();
        Assertions.assertThat(savedAdoptionAnimal).isNotNull();
        Assertions.assertThat(savedAdoptionAnimal.getId()).isNotNull();
    }
}
