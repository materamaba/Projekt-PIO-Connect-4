# Wymagania projektu
- Aplikacja będzie działać w terminalu
- Kod piszemy po Angielsku, a komunikaty po Polsku
- Kod będzie pisany w języku Java
# Wymagania aplikacji
## Klient
- Jest odpowiedzialny za wyświetlanie rozgrywki.
- Umożliwia graczowi polączenie sie z serwerem.
- Jest w nim wywolywane menu.
## Serwer
- Do niego podlączają sie poszczególni klienci.
- Na nim wykonywana jest logika rozgrywki.
## Gracz
- Gracz wybiera do której kolumny chce wrzucic krążek.
## Bot
- Analizuje plansze i sam wybiera gdzie najlepiej wrzucić krążek.
## Menu
- Wyświetla dostępne opcje oraz umożliwia ich wybor.
## Rozgrywka
- Wewnątrz tej klasy znajduje sie logika gry connect 4.
## Plansza
- Przechowuje plansze oraz lokalizacje poszczególnch krążków na niej sie znajdujących.
## Krążek
- Przechowuje parametry wlasciwosci krażka.
# Instrukcja do gry Connect 4
https://pl.wikipedia.org/wiki/Czwórki

# Przykładowy przebieg rozgrywki (z innym graczem)
Komputer 1: Wyświetlenie menu -> wybor opcji multiplayer -> wybor opcji hostuj -> serwer sie uruchamia i czeka na 2 gracza -> wyswietlenie rozgrywki oraz naprzemienne wrzucanie krążków -> po zakonczeniu rozgrywki wyswietlany jest wynik

Komputer 2:  Wyświetlenie menu -> wybor opcji multiplayer -> wybor opcji dolącz -> prosba o wpisanie ip hosta -> wyswietlenie rozgrywki oraz naprzemienne wrzucanie krążków -> po zakonczeniu rozgrywki wyswietlany jest wynik
                                  
# Odnośniki do źródeł
https://docs.oracle.com/en/java/javase/21/docs/api/index.html
https://openjfx.io/javadoc/26/

