package ee.ivkhkdev.services;

import ee.ivkhkdev.interfaces.AppHelper;
import ee.ivkhkdev.interfaces.FileRepository;
import ee.ivkhkdev.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    private List<Card> cards;
    private AppHelper<Card> appHelperMock;
    private FileRepository<Card> fileRepositoryMock;
    private CardService cardService;

    @BeforeEach
    void setUp() {
        cards = new ArrayList<>();
        appHelperMock = mock(AppHelper.class);
        fileRepositoryMock = mock(FileRepository.class);

        // Настройка мока для репозитория
        when(appHelperMock.getRepository()).thenReturn(fileRepositoryMock);

        // Создаем экземпляр CardService
        cardService = new CardService(cards, appHelperMock);
    }

    @Test
    void testAddCard() {
        Card card = new Card(); // Создаем новую карту
        when(appHelperMock.create()).thenReturn(card);

        boolean result = cardService.add();

        assertTrue(result, "Добавление карты должно вернуть true");
        assertEquals(1, cards.size(), "Карта должна быть добавлена в список");
        verify(fileRepositoryMock).save(cards); // Проверяем, что карта сохраняется в репозитории
    }

    @Test
    void testAddCardWithNull() {
        when(appHelperMock.create()).thenReturn(null);

        boolean result = cardService.add();

        assertFalse(result, "Добавление null карты должно вернуть false");
        assertTrue(cards.isEmpty(), "Список карт должен остаться пустым");
        verify(fileRepositoryMock, never()).save(cards); // Проверяем, что save не вызывается
    }

    @Test
    void testEditCardSuccessful() {
        // Создаем карту с ID и добавляем её в список
        Card originalCard = new Card();
        UUID originalId = originalCard.getId(); // Получаем ID созданной карты
        cards.add(originalCard);

        // Создаем обновленную карту с тем же ID
        Card updatedCard = new Card();
        // Вместо замокивания getId, задаем тот же ID напрямую
        updatedCard.getId(originalId);

        boolean result = cardService.edit(updatedCard);

        assertTrue(result, "Редактирование карты должно вернуть true");
        assertEquals(updatedCard, cards.get(0), "Карта в списке должна быть обновлена");
        verify(fileRepositoryMock).save(cards); // Проверяем, что изменения сохраняются
    }

    @Test
    void testEditCardNotFound() {
        Card card = new Card(); // Создаем карту, которая не добавлена в список

        boolean result = cardService.edit(card);

        assertFalse(result, "Редактирование несуществующей карты должно вернуть false");
        verify(fileRepositoryMock, never()).save(cards); // Сохранение не должно вызываться
    }

    @Test
    void testRemoveCardSuccessful() {
        // Создаем карту и добавляем её в список
        Card card = new Card();
        UUID cardId = card.getId();
        cards.add(card);

        boolean result = cardService.remove(card);

        assertTrue(result, "Удаление существующей карты должно вернуть true");
        assertTrue(cards.isEmpty(), "Список карт должен быть пустым после удаления");
        verify(fileRepositoryMock).save(cards); // Проверяем, что изменения сохраняются
    }

    @Test
    void testRemoveCardNotFound() {
        Card card = new Card(); // Создаем карту, которая не добавлена в список

        boolean result = cardService.remove(card);

        assertFalse(result, "Удаление несуществующей карты должно вернуть false");
        verify(fileRepositoryMock, never()).save(cards); // Сохранение не должно вызываться
    }

    @Test
    void testPrintCards() {
        // Создаем карту и добавляем её в список
        Card card = new Card();
        cards.add(card);

        // Настройка мока, чтобы метод load() возвращал текущий список карт
        when(fileRepositoryMock.load()).thenReturn(cards);

        cardService.print();

        // Проверяем, что printList вызывается с текущим списком карт
        verify(appHelperMock).printList(cards);
    }

    @Test
    void testListCards() {
        List<Card> loadedCards = new ArrayList<>();
        loadedCards.add(new Card());
        when(fileRepositoryMock.load()).thenReturn(loadedCards);

        List<Card> result = cardService.list();

        assertEquals(loadedCards, result, "Список карт должен совпадать с загруженным из репозитория");
        assertEquals(1, result.size(), "Загруженный список должен содержать одну карту");
    }
}
