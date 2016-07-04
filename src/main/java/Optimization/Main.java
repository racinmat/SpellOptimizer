package Optimization;

import Optimization.Card.*;
import Optimization.Optimizer.IntegerLinearProgrammingOptimizer;
import Optimization.Optimizer.Optimizer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private Text spellText;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//        String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        String jarName = new java.io.File(jarPath).getName();
//        String pathWithoutFile = jarPath.substring(1).replace(jarName, "");
//        System.load(pathWithoutFile + "lpsolve55.dll");
//        System.load(pathWithoutFile + "lpsolve55j.dll");
//        System.loadLibrary("lpsolve55");
//        System.loadLibrary("lpsolve55j");

        primaryStage.setTitle("Spell optimizer");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 1020, 1020);
        primaryStage.setScene(scene);

        Label minCastChanceLabel = new Label("Minimal cast chance");
        Label maxActionsLabel = new Label("Maximal actions to cast");
        Label maxManaLabel = new Label("Maximal mana cost");
        Label maxCardsLabel = new Label("Maximal cards amount");

        TextField minCastChanceField = new TextField("0");
        TextField maxActionsField = new TextField("0");
        TextField maxManaField = new TextField("0");
        TextField maxCardsField = new TextField("0");

        Button startOptimizationButton = new Button("Find optimal spell");

        grid.add(minCastChanceLabel, 0, 1);
        grid.add(maxActionsLabel, 0, 2);
        grid.add(maxManaLabel, 0, 3);
        grid.add(maxCardsLabel, 0, 4);

        grid.add(minCastChanceField, 1, 1);
        grid.add(maxActionsField, 1, 2);
        grid.add(maxManaField, 1, 3);
        grid.add(maxCardsField, 1, 4);

        startOptimizationButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.add(startOptimizationButton, 0, 5, 2, 1);

        startOptimizationButton.setOnAction(event -> {
            try {
                System.out.println("started optimization");
                int minCastChance = Integer.parseInt(minCastChanceField.getText());
                int maxActions = Integer.parseInt(maxActionsField.getText());
                int maxMana = Integer.parseInt(maxManaField.getText());
                int maxCards = Integer.parseInt(maxCardsField.getText());
                optimize(minCastChance, maxActions, maxMana, maxCards);
            } catch (Exception e) {
                spellText.setText(e.getMessage());
            }
        });

        Label optimalSpellLabel = new Label("Optimal spell:");
        grid.add(optimalSpellLabel, 0, 6);

        HBox hbox = new HBox();
        spellText = new Text(new Spell().toString());
        hbox.getChildren().add(spellText);
        hbox.setStyle("-fx-border-color: black;");

        grid.add(hbox, 0, 7, 2, 4);

        primaryStage.show();
    }

    private void optimize(int minCastChance, int maxActions, int maxMana, int maxCards) {
        Optimizer optimizer;
        optimizer = new IntegerLinearProgrammingOptimizer();
        Spell spell = optimizer.optimize(maxActions, maxMana, maxCards, minCastChance);
//        System.out.println(spell.toString());
        String spellInfo = "";
        spellInfo += "cast chance bonus = " + spell.getCastChanceBonus() + "\n";
        spellInfo += "cast time in actions = " + spell.getCastTime() + "\n";
        spellInfo += "mana cost = " + spell.getMana() + "\n";
        spellInfo += "card amount = " + spell.cards.size() + "\n";

        Map<Class<? extends Card>, String> cardNames = new HashMap<>();
        cardNames.put(AreaCard.class, "Karta oblasti");
        cardNames.put(BasicCard.class, "Základní karta");
        cardNames.put(CastingCard.class, "Karta sesílání");
        cardNames.put(DamageCard.class, "Karta poškození");
        cardNames.put(DurationCard.class, "Karta trvání");
        cardNames.put(ManaCard.class, "Karta many");
        cardNames.put(RangeCard.class, "Karta střely");
        cardNames.put(TimeCard.class, "Karta času");

        Map<Level, String> levelNames = new HashMap<>();
        levelNames.put(Level.LEVEL_1, "1");
        levelNames.put(Level.LEVEL_2, "2");
        levelNames.put(Level.LEVEL_3, "3");
        levelNames.put(Level.LEVEL_4, "4");

        Map<Class<? extends Card>, Map<Level, Integer>> cardAmounts = new HashMap<>();
        for (CardType cardType : CardType.values()) {
            cardAmounts.put(cardType.getCardClass(), new HashMap<>());
            for (Level level : Level.values()) {
                cardAmounts.get(cardType.getCardClass()).put(level, 0);
            }
        }

        for (Card card : spell.cards) {
            int amount = cardAmounts.get(card.getClass()).get(card.level);
            cardAmounts.get(card.getClass()).put(card.level, amount + 1);
        }
        for (Map.Entry<Class<? extends Card>, Map<Level, Integer>> card : cardAmounts.entrySet()) {
            for (Map.Entry<Level, Integer> level : card.getValue().entrySet()) {
                if (level.getValue() > 0) {
                    String cardName = cardNames.get(card.getKey());
                    spellInfo += "\n" + cardName + ", level " + levelNames.get(level.getKey()) + " = " + level.getValue();
                }
            }
        }

        spellText.setText(spellInfo);
    }

}
