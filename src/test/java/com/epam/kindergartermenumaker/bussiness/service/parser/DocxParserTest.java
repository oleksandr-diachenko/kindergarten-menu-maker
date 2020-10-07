package com.epam.kindergartermenumaker.bussiness.service.parser;

import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/2/2020
 **/
@ExtendWith(MockitoExtension.class)
@Tag("integration")
class DocxParserTest {

    private static final String CHEESE = "Сир кисломолочний напівжирний";
    private static final String SEMOLINA = "Крупа манна";
    private static final String SUGAR = "Цукор";
    private static final String BUTTER = "Масло вершкове (селянське)";
    private static final String EGG = "Яйце куряче";
    private static final String RAISINS = "Родзинки";
    private static final String CASSEROLE = "Запіканка сирна із сметанним соусом";
    private static final String CASSEROLE_DESCRIPTION = "Підготовлену крупу манну попередньо запарюють, дають набухнути. Сир кисломолочний протирають разом із підготовленими яйцями, додають запарену крупу манну, цукор, сіль, підготовлені родзинки. Добре вимішують і викладають на змащений маслом вершковим лист  шаром завтовшки 2,5-3 см та запікають у духовій шафі за температури 250-270 оС до готовності (30-40 хв.). Запіканку подають із соусом сметанним.10";

    @InjectMocks
    private DocxParser parser;

    @Test
    void shouldParseDocxDocument() throws IOException {
        InputStream inputStream = new ClassPathResource("/doc/zapikanka.docx").getInputStream();

        RecipeForm recipeForm = parser.parse(inputStream);

        verifyZapikanka(recipeForm);
    }

    @Test
    void shouldParseDocxDocumentWithDifferentSpaces() throws IOException {
        InputStream inputStream = new ClassPathResource("/doc/zapikanka-different-spaces.docx").getInputStream();

        RecipeForm recipeForm = parser.parse(inputStream);

        verifyZapikanka(recipeForm);
    }

    @Test
    void shouldParseDocxDocumentWithEmptyStrings() throws IOException {
        InputStream inputStream = new ClassPathResource("/doc/lemon-tea.docx").getInputStream();

        RecipeForm recipeForm = parser.parse(inputStream);

        assertThat(recipeForm.getRecipeName()).isEqualTo("Чай з лимоном");
        assertThat(recipeForm.getRecipeDescription()).contains("Чай-заварку готують");

    }

    @Test
    void shouldParseDocxDocumentWithIncorrectSymbols160() throws IOException {
        InputStream inputStream = new ClassPathResource("/doc/chicken-spaces.docx").getInputStream();

        RecipeForm recipeForm = parser.parse(inputStream);

        assertThat(recipeForm.getRecipeName()).isEqualTo("Тюфтельки курячі 2");
        assertThat(recipeForm.getRecipeDescription()).contains("Філе курине   пропускають ");
        assertThat(recipeForm.getIngredients())
                .contains(getChicken());
    }

    private IngredientForm getChicken() {
        return prepareIngredient("Філе курине", 40, 60, 40,
                60, 0.2, 0.21, 0.01);
    }

    private void verifyZapikanka(RecipeForm recipeForm) {
        assertThat(recipeForm.getCategoryName()).isNull();
        assertThat(recipeForm.getRecipeName()).isEqualTo(CASSEROLE);
        assertThat(recipeForm.getRecipeDescription()).isEqualTo(CASSEROLE_DESCRIPTION);
        assertThat(recipeForm.getIngredients())
                .containsExactlyInAnyOrder(getZapikankaIngredients());
    }

    private IngredientForm[] getZapikankaIngredients() {
        IngredientForm cheese = prepareIngredient(CHEESE, 85, 112, 84,
                111, 0.18, 0.09, 0.02);
        IngredientForm semolina = prepareIngredient(SEMOLINA, 7, 9, 7,
                9, 0.1, 0.01, 0.68);
        IngredientForm sugar = prepareIngredient(SUGAR, 8, 10, 8,
                10, 0, 0, 1);
        IngredientForm butter = prepareIngredient(BUTTER, 3, 4, 3,
                4, 0.01, 0.73, 0.01);
        IngredientForm egg = prepareIngredient(EGG, 5.5, 8, 5,
                7, 0.13, 0.12, 0.01);
        IngredientForm raisins = prepareIngredient(RAISINS, 9, 11, 9,
                11, 0.02, 0.01, 0.66);
        return new IngredientForm[]{cheese, semolina, sugar, butter, egg, raisins};
    }

    private IngredientForm prepareIngredient(String name, double nurseryGrossAmount, double kindergartenGrossAmount,
                                             double nurseryNetAmount, double kindergartenNetAmount,
                                             double protein, double fat, double carbohydrate) {
        IngredientForm ingredientForm = new IngredientForm();
        ingredientForm.setIngredientName(name);
        ingredientForm.setNurseryGrossAmount(nurseryGrossAmount);
        ingredientForm.setKindergartenGrossAmount(kindergartenGrossAmount);
        ingredientForm.setNurseryNetAmount(nurseryNetAmount);
        ingredientForm.setKindergartenNetAmount(kindergartenNetAmount);
        ingredientForm.setProtein(protein);
        ingredientForm.setFat(fat);
        ingredientForm.setCarbohydrate(carbohydrate);
        return ingredientForm;
    }
}