package Optimization.Card;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum CardType {

    AREA(AreaCard.class),
    BASIC(BasicCard.class),
    CASTING(CastingCard.class),
    DAMAGE(DamageCard.class),
    DURATION(DurationCard.class),
    MANA(ManaCard.class),
    RANGE(RangeCard.class),
    TIME(TimeCard.class);

    private Class<? extends Card> cardClass;

    private CardType(Class<? extends Card> cardClass) {
        this.cardClass = cardClass;
    }

    public Class<? extends Card> getCardClass() {
        return cardClass;
    }
}
