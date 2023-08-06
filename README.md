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
