# BD_projekt_2020

Skład grupy:  
Konrad Przewłoka  
Maria Polak  
Temat Projektu: Baza obsługująca magazyn leków w szpitalu. Kontrolująca stan magazynu oraz aktywne zlecenia na wydawanie leków pacjentom. Chcemy realizować projekt z użyciem Hiberante'a.  
Baza danych: MySql  
Technologia w której będzie realizowana aplikacja: Hiberante/Java, JavaFX

# Opis realizacji

## Struktura bazy
Zaprojektowano prosty schemat bazy umożliwający ilsutrowanie encji oraz relacji miedzy nimi w modelowanym systemie. Stworzono tabele reprezentujące lekarza (Doctor), pacjenta (Patient) oraz Dostawcę (Supplier), są to encje reprezentujące byty cywilno-prawne takie jak osoby czy firmy. Dodatkowo stworzono tabelę zawierajacą encje reprzentujące leki (Medicine) oraz kolejną reprezentującą recepty (Prescription). Pozostałe dwie tabele służą do  modelowania relacji między encjami, tabela Medicine_Supplier łączy dostawców z lekami w jakie zaopatrują magazyn, natomiast tabela PrescriptionElement łączy receptę z lekami jakie zostały w niej zapisane, jednocześnie przechowując informację o dawce określonej przez lekarza. Pozostałe relacje modelowane są przy użyciu kluczy obcych. 

![102925755_2973354556094575_146865032297185280_n](https://user-images.githubusercontent.com/32310362/83689088-be327a00-a5ee-11ea-86b2-f8f6c22dcc15.png)

## Funkcjonalność aplikacji bazodanowej
Całość API aplikacji bazodanowej w porjekcie znajduje się w katalogu Handlers.
Udostępnione API aplkacji bazodanowej podzielone jest na trzy opisane poniżej kategorie:

### Metody dodajace dane do bazy
Interfejs służący do definiowania nowych encji oraz relacji zawarty jest w klasie TransactionHandler, która udostępnia poniższe metody:
- addDoctor
- addPatient
- addSupplier
- addMedicine
- addMedicineSupplier
- addPrescription
Zakres odpowiedzialności metod łatwo określić na podstawie ich nazw.

### Metody modyfikujące dane w bazie
Interfejs służący do modyfikowania istniejący encji i relacji zawarty jest w klasie DataModificationHandler, która udostępnia poniższe metody:
- modifyDoctorById
- modifyPatientById
- modifySupplierById
- placeOrderForMedicine
Zakres odpowiedzialności metod łatwo określić na podstawie ich nazw.

### Metody dostępowe do danych
Interfejs służący do uzyskiwania dancyh z bazy zawarty jest w klasie DataAccessHandler, która udostępnia poniższe metody:
- getAll<EntitityClass>
- get<EntityClass>ById
- getAllPatientsCurrentlyOnMed
- getAllPatientsThatUsedMed
- getPrescriptionElements
- getMedicineSuppliers
- getMedicineSuppliers
  
Metody te umożliwają uzyskanie informacji o każdej interesujacej relacji występującej w bazie oraz o każdej encji, umożliwają również  analizę danych, na przykład uzyskanie listy wszystkich pacjentó aktualnie przyjmujących dany lek (getAllPatientsCurrentlyOnMed).
  
Wszystkie metody udostępnianie przez wymienione klasy z rodziny Handler odpowiadają same za otworzenie nowejs sesji oraz zamknięcie jej po wykonaniu potrzebnych operacji.

## Istotne klasy
Klasy, które nie odbowiadają bezpośrdenio z strukturę bazy ani za operacje na niej, umieszczono w katalogu LogicUtils.
W celu ułatwienia obsługi operacji na bazie dodano typ wyliczeniowy DosageType reprezentujący jednostkę dawkowania leku, oraz udostępniający statyczne metody fromString i toString. Dość ściśle z typem DosageType związana jest klasa parser, która udostepnia metody statyczne służące do uzyskiwania z podanego Stringa wartościliczbowej opsującej dawkę oraz związaną z nią jednostkę dawkowania:
- parseDosageUnit
- parseDosageValue
Dodatkowo klasa ta udostępnia szereg matod walidacyjnych używanych w klasach z rodziny Handler w celu sprawdzania poprawności dancyh wprowadzanych rpzez użytkownika:
- isValidEmail
- isValidPhone
- isValidName
- isValidSurname
- isValidZipCode
- isValidCity

### Klasy opisujące encje
Projekt zawiera również katalog Entities w którym znajdują się klasy opisujące poszczegulne encje które są mapowane na tabele z użyciem Hibernate.

## GUI
W celu lepszej prezentacji użyteczności API zaimplementowano prosty interfejs graficzny umożliwajacy interakcję z bazą przy pomocy udostępnionego API.
Klasy odpowiedzialne za interfesj graficzny znajdują się w katalogu Gui.


## Uwagi techniczne
Baza hostowana jest na serwerach udostępnionych przez katedrę.







