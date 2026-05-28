# Wymagania projektu
- Aplikacja będzie działać w terminalu
- Kod piszemy po Angielsku, a komunikaty po Polsku
- Kod będzie pisany w języku Java
# Wymagania aplikacji
## Klient
- Z kontem Klienta ściśle powiązany jest jeden Koszyk (Koszyk nie istnieje bez klienta).
- Klient może składać zamówienia.
- Klient może posiadać wiele kuponów rabatowych (lub nie mieć żadnego).
## Katalog i produkty
- Katalog składa się z wielu Produktów.
- Produkty mają Kategorie (kategoria może mieć wiele produktów, produkt należy do kategorii).
- Produkt posiada określoną cenę (atrybut cena: float).
## Koszyk i Zamówienie
- Koszyk może zawierać wiele Produktów.
- Po zatwierdzeniu koszyka powstaje Zamówienie, które również zawiera wybrane Produkty.
- Zamówienie ma typ dostawy (atrybut typDostawy: String).
## Magazyn
- Magazyn przechowuje wiele produktów
## Kupon rabatowy
- Kupon rabatowy obniża cenę konkretnych Produktów.
- Jeden kupon może dotyczyć wielu produktów.
# Przykładowy przebieg procesu zakupowego
Klient -> Przegląda Katalog -> Wybiera Produkt z Kategorii -> Dodaje Produkt do Koszyka -> Przechodzi do okna zamówienia -> Aplikuje Kupon rabatowy (opcjonalnie) -> Składa zamówienie (wybiera typDostawy) -> Aktualizacja stanu w Magazynie.
# Odnośniki do źródeł
https://docs.oracle.com/en/java/javase/21/docs/api/index.html