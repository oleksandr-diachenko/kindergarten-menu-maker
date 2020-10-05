package com.epam.kindergartermenumaker.bussiness.service.parser;

import com.epam.kindergartermenumaker.web.adapter.IngredientForm;
import com.epam.kindergartermenumaker.web.adapter.RecipeForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.apache.commons.math3.util.Precision.round;

/**
 * @author : Oleksandr Diachenko
 * @since : 10/2/2020
 **/
@Service
@Slf4j
public class DocxParser implements Parser<RecipeForm> {

    private static final int RECIPE_NAME_INDEX = 4;
    private static final int TABLE_INDEX = 5;
    private static final int RECIPE_DESCRIPTION_INDEX = 7;
    private static final int FIRST_INGREDIENT_ROW_INDEX = 3;
    private static final int CARBOHYDRATE_INDEX = 9;
    private static final int FAT_INDEX = 7;
    private static final int PROTEIN_INDEX = 5;
    private static final int KINDERGARTEN_NET_INDEX = 4;
    private static final int KINDERGARTEN_GROSS_INDEX = 2;
    private static final int NURSERY_GROSS_INDEX = 1;
    private static final int INGREDIENT_NAME_INDEX = 0;
    private static final int NURSERY_NET_INDEX = 3;
    private static final int SCALE = 2;

    @Override
    public RecipeForm parse(InputStream inputStream) {
        RecipeForm recipeForm = new RecipeForm();

        try (XWPFDocument docx = new XWPFDocument(OPCPackage.open(inputStream));
             XWPFWordExtractor extractor = new XWPFWordExtractor(docx)) {
            List<IBodyElement> bodyElements = getNonEmptyElements(extractor);

            populateRecipeName(recipeForm, bodyElements);

            List<XWPFTableRow> rows = getTableRows(bodyElements);
            recipeForm.setIngredients(getIngredientForms(rows));

            populateRecipeDescription(recipeForm, bodyElements);
            return recipeForm;
        } catch (Exception ex) {
            log.error("Can't parse document");
            throw new IllegalArgumentException(ex);
        }
    }

    private List<IBodyElement> getNonEmptyElements(XWPFWordExtractor extractor) {
        List<IBodyElement> elements = ((XWPFDocument) extractor.getDocument()).getBodyElements();
        return elements.stream()
                .filter(element -> isTable(element) || nonEmptyParagraph(element))
                .collect(Collectors.toList());
    }

    private boolean isTable(IBodyElement element) {
        return element instanceof XWPFTable;
    }

    private boolean nonEmptyParagraph(IBodyElement element) {
        return element instanceof XWPFParagraph
                && !((XWPFParagraph) element).getText().isEmpty();
    }

    private List<IngredientForm> getIngredientForms(List<XWPFTableRow> rows) {
        List<IngredientForm> ingredientForms = new ArrayList<>();
        for (int i = FIRST_INGREDIENT_ROW_INDEX; i < rows.size() - 1; i++) {
            XWPFTableRow row = rows.get(i);
            IngredientForm ingredientForm = populateIngredientForm(row);
            ingredientForms.add(ingredientForm);
        }
        return ingredientForms;
    }

    private void populateRecipeDescription(RecipeForm recipeForm, List<IBodyElement> bodyElements) {
        String recipeDescription = ((XWPFParagraph) bodyElements.get(RECIPE_DESCRIPTION_INDEX)).getParagraphText();
        recipeForm.setRecipeDescription(trimToEmpty(recipeDescription));
    }

    private IngredientForm populateIngredientForm(XWPFTableRow row) {
        IngredientForm ingredientForm = new IngredientForm();
        populateIngredientName(row, ingredientForm);
        populateNurseryGrossAmount(row, ingredientForm);
        populateKindergartenGrossAmount(row, ingredientForm);
        populateNurseryNetAmount(row, ingredientForm);
        populateKindergartenNetAmount(row, ingredientForm);
        populateProteinAmount(row, ingredientForm);
        populateFatAmount(row, ingredientForm);
        populateCarbohydrateAmount(row, ingredientForm);
        return ingredientForm;
    }

    private void populateCarbohydrateAmount(XWPFTableRow row, IngredientForm ingredientForm) {
        double nurseryNet = getNurseryNetAmount(row);
        if (nurseryNet != 0) {
            double carbohydrate = getCellValue(row, CARBOHYDRATE_INDEX);
            ingredientForm.setCarbohydrate(round(carbohydrate / nurseryNet, SCALE));
        }
    }

    private void populateFatAmount(XWPFTableRow row, IngredientForm ingredientForm) {
        double nurseryNet = getNurseryNetAmount(row);
        if (nurseryNet != 0) {
            double fat = getCellValue(row, FAT_INDEX);
            ingredientForm.setFat(round(fat / nurseryNet, SCALE));
        }
    }

    private void populateProteinAmount(XWPFTableRow row, IngredientForm ingredientForm) {
        double nurseryNet = getNurseryNetAmount(row);
        if (nurseryNet != 0) {
            double protein = getCellValue(row, PROTEIN_INDEX);
            ingredientForm.setProtein(round(protein / nurseryNet, SCALE));
        }
    }

    private void populateKindergartenNetAmount(XWPFTableRow row, IngredientForm ingredientForm) {
        ingredientForm.setKindergartenNetAmount(getCellValue(row, KINDERGARTEN_NET_INDEX));
    }

    private void populateNurseryNetAmount(XWPFTableRow row, IngredientForm ingredientForm) {
        ingredientForm.setNurseryNetAmount(getNurseryNetAmount(row));
    }

    private void populateKindergartenGrossAmount(XWPFTableRow row, IngredientForm ingredientForm) {
        ingredientForm.setKindergartenGrossAmount(getCellValue(row, KINDERGARTEN_GROSS_INDEX));
    }

    private void populateNurseryGrossAmount(XWPFTableRow row, IngredientForm ingredientForm) {
        ingredientForm.setNurseryGrossAmount(getCellValue(row, NURSERY_GROSS_INDEX));
    }

    private void populateIngredientName(XWPFTableRow row, IngredientForm ingredientForm) {
        ingredientForm.setIngredientName(row.getCell(INGREDIENT_NAME_INDEX).getText());
    }

    private List<XWPFTableRow> getTableRows(List<IBodyElement> bodyElements) {
        XWPFTable table = bodyElements.get(TABLE_INDEX).getBody().getTableArray(0);
        return table.getRows();
    }

    private void populateRecipeName(RecipeForm recipeForm, List<IBodyElement> bodyElements) {
        String recipeName = ((XWPFParagraph) bodyElements.get(RECIPE_NAME_INDEX)).getParagraphText();
        recipeForm.setRecipeName(recipeName);
    }

    private double getNurseryNetAmount(XWPFTableRow row) {
        return getCellValue(row, NURSERY_NET_INDEX);
    }

    private double getCellValue(XWPFTableRow row, int pos) {
        try {
            return Double.parseDouble(row.getCell(pos).getText().replace(',', '.'));
        } catch (NumberFormatException exception) {
            return 0.0;
        }
    }
}
