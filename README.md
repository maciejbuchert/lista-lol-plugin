# Plugin lista.lol
Plugin został stworzony w celu wynagradzania wspierających wasze serwery graczy na serwisie lista.lol!
Plugin oferujem możliwość dynamicznego dostosowywania nagród w zależności od długości wykupionego okresu przez gracza.
Dzięki elastycznej konfiguracji masz pełną kontrolę nad kształtowaniem gratyfikacji, które są udzielane na serwerze.
Nasz kod jest w pełni otwarty i wolny od wirusów.

# Pierwsze kroki
1. Skontaktuj się z nami w celu uzyskania klucza API. Najlepiej napisać do nas maila z domeny serwera na adres api@codebit.pl
2. Zainstaluj i skonfiguruj plugin. [Instrukcja poniżej](https://github.com/maciejbuchert/lista-lol-plugin/#instalacja-pluginu)

# Instalacja pluginu
1. Pobierz i rozpakuj najnowszą wersję pluginu z zakładki [Releases](https://github.com/maciejbuchert/lista-lol-plugin/releases)
2. Zawartość katalogu umieść na serwerze w katalogu "plugins"
3. Zrestartuj lub uruchom serwer
4. Skopiuj plik `config.sample` do `config.yml`
5. Otwórz plik `config.yml` i dostosuj go według własnych potrzeb. [Sprawdź przykładową konfigurację](https://github.com/maciejbuchert/lista-lol-plugin/#przyk%C5%82adowa-konfiguracja-pliku-configyml)
6. Zapisz plik do `config.yml`
7. Wykonaj komendę `/reload confirm` w celu przeładowania konfiguracji

# Przykładowa konfiguracja pliku `config.yml`
```yml
config:
  token: "s&bZ#YGGSq!zF2J3UYS*68LLUdnjQAKC" # Klucz API

# Definicje dostępnych przedmiotów
items:
  Item1:
    name: "&bDiament"  # Nazwa przedmiotu
    material: "DIAMOND"  # Materiał przedmiotu
    lore:
      - "Testowy opis"  # Opis przedmiotu
      - "Testowy opis 2"

  Item2:
    name: "&6Piasek"  # Nazwa przedmiotu
    material: "SAND"  # Materiał przedmiotu
    lore:
      - "Niesamowity przedmiot"  # Opis przedmiotu

# Definicje nagród w zależności od wykupionego okresu
rewards:

  # Nagroda dla graczy, którzy wykupią 1 dzień lub więcej
  1+: 
    - "message:&8Otrzymałeś nagrodę"

  # Nagroda dla gracza, który wykupi dokładnie 11 dni
  11:
    - "Item1:99"
    - "Item2:60"
    - "/give %player% diamond"
    - "message:&4Odebrano potężną nagrodę!"

  # Nagrody dla graczy, którzy wykupią okres od 1 do 5 dni
  1-5:
    - "Item1:10"  # 10 sztuk Item1
    - "Item2:20"  # 20 sztuk Item2
    - "/give %player% stone"  # Wywołanie komendy /give
    - "message:&2Odebrano nagrodę"  # Wysłanie wiadomości do gracza
```

# Kontrybuuj
Zachęcamy do rozwouj pluginu. W zakładce Issues można zgłaszać błędy oraz nowe funkcje

# Autor
Dziękujemy @rex89m za utworzenie pluginu
