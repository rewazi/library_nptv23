package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.Service;
import ee.ivkhkdev.model.Card;

import java.util.List;

public class CardService implements Service<Card> {

    private List<Card> cards;
    private final AppHelper<Card> appHelperCard;

    public CardService(List<Card> cards, AppHelper<Card> appHelperCard) {
        this.cards = cards;
        this.appHelperCard = appHelperCard;
    }

    @Override
    public boolean add() {
        try {
            Card card = appHelperCard.create();
            if (card == null) return false;

            cards.add(card); // Добавляем карту в список
            appHelperCard.getRepository().save(cards); // Сохраняем обновленный список карт в репозиторий

            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean edit(Card card) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getId().equals(card.getId())) {
                cards.set(i, card); // Обновляем данные карты
                appHelperCard.getRepository().save(cards); // Сохраняем обновленный список в репозиторий
                return true;
            }
        }
        System.out.println("Card not found for editing.");
        return false;
    }

    @Override
    public boolean remove(Card card) {
        boolean removed = cards.removeIf(existingCard -> existingCard.getId().equals(card.getId())); // Удаляем карту по ID
        if (removed) {
            appHelperCard.getRepository().save(cards); // Сохраняем изменения
        }
        return removed;
    }

    @Override
    public void print() {
        appHelperCard.printList(this.list()); // Выводим список карт через AppHelper
    }

    @Override
    public List<Card> list() {
        cards = appHelperCard.getRepository().load(); // Загружаем список карт из репозитория
        return cards;
    }
}
