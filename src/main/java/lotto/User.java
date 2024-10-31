package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class User {

    private final int purchaseAmount;
    private final ArrayList<Lotto> lottos = new ArrayList<>();
    private final Map<Prize, Integer> prizes = new LinkedHashMap<>();

    public User(String purchaseAmount) {
        this.purchaseAmount = validatePurchaseAmount(purchaseAmount);
        for (int i = 0; i < this.purchaseAmount / 1000; i++) {
            this.lottos.add(new Lotto(Randoms.pickUniqueNumbersInRange(1, 45, 6)));
        }
        for (Prize prize : Prize.values()) {
            this.prizes.put(prize, 0);
        }
    }

    private static int validatePurchaseAmount(String input) {
        int purchaseAmount;

        try {
            purchaseAmount = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("로또 구입 금액은 숫자로 입력해야 합니다.");
        }

        if (purchaseAmount < 1000 || purchaseAmount % 1000 != 0) {
            throw new IllegalArgumentException("로또 구입 금액은 1000원 단위로 입력해야 합니다.");
        }

        return purchaseAmount;
    }

    public ArrayList<Lotto> getLottos() {
        return this.lottos;
    }

    public void setPrize(int matchCount, int bonusMatch) {
        Prize prize = Prize.getPrize(matchCount, bonusMatch);
        this.prizes.put(prize, this.prizes.get(prize) + 1);
    }

    public Map<Prize, Integer> getPrizes() {
        if (this.prizes.isEmpty()) {
            throw new IllegalStateException("당첨 번호를 입력해야 당첨 통계를 확인할 수 있습니다.");
        }
        return this.prizes;
    }

    public double getReturnRate() {
        int totalPrizeAmount = 0;
        for (Prize prize : Prize.values()) {
            totalPrizeAmount += prize.getPrizeAmount() * this.prizes.get(prize);
        }
        return (double) totalPrizeAmount / this.purchaseAmount * 100;
    }
}
