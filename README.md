# BD_projekt_2020

Skład grupy:  
Konrad Przewłoka  
Maria Polak  
Temat Projektu: Baza obsługująca magazyn leków w szpitalu. Kontrolująca stan magazynu oraz aktywne zlecenia na wydawanie leków pacjentom. Chcemy realizować projekt z użyciem Hiberante'a.  
Baza danych: MySql  
Technologia w której będzie realizowana aplikacja: Hiberante/Java, JavaFX

# Opis realizacji

## Struktura bazy 
Zaprojektowano prosty schemat bazy umożliwający ilsutrowanie encji oraz relacji miedzy nimi w modelowanym systemie. Stworzono tabele reprezentujące lekarza ([Doctor](Entities/Doctor.java)), pacjenta ([Patient](Patient.java)) oraz Dostawcę ([Supplier](Supplier.java)), są to encje reprezentujące byty cywilno-prawne takie jak osoby czy firmy. Dodatkowo stworzono tabelę zawierajacą encje reprzentujące leki ([Medicine](Medicine.java)) oraz kolejną reprezentującą recepty ([Prescription](Prescription.java)). Pozostałe dwie tabele służą do  modelowania relacji między encjami, tabela Medicine_Supplier łączy dostawców z lekami w jakie zaopatrują magazyn, natomiast tabela [PrescriptionElement](PrescriptionElement.java) łączy receptę z lekami jakie zostały w niej zapisane, jednocześnie przechowując informację o dawce określonej przez lekarza. Pozostałe relacje modelowane są przy użyciu kluczy obcych. 

![102925755_2973354556094575_146865032297185280_n](https://user-images.githubusercontent.com/32310362/83689088-be327a00-a5ee-11ea-86b2-f8f6c22dcc15.png)

## Funkcjonalność aplikacji bazodanowej
Całość API aplikacji bazodanowej w projekcie znajduje się w katalogu [Handlers](https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/Handlers).
Udostępnione API aplkacji bazodanowej podzielone jest na trzy opisane poniżej kategorie:

### Metody dodajace dane do bazy - [TransactionHandler](https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/Handlers/TransactionHandler.java)
Interfejs służący do definiowania nowych encji oraz relacji zawarty jest w klasie TransactionHandler, która udostępnia poniższe metody:
- addDoctor
- addPatient
- addSupplier
- addMedicine
- addMedicineSupplier
- addPrescription

Najciekawszą z wymienionych metod jest addPrescription która dodaje nową receptę zawierajacą leki podane w formie listy oraz dawki z nimi związane również w formie listy, metoda ta korzysta z dwóch prywatnych metod klasy TransactionHandler, które został stworzone tylko do użycia w tej metodzie, jedna odpowiada za walidację danych druga pomaga zaktualizować stan magazynu po wydaniu recepty na konkretny lek, całość jest wykonywanna w ramach jednet tranzakcji.

### Metody modyfikujące dane w bazie - [DataModificationHandler](https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/Handlers/DataModificationHandler.java)
Interfejs służący do modyfikowania istniejący encji i relacji zawarty jest w klasie DataModificationHandler, która udostępnia poniższe metody:
- modifyDoctorById
- modifyPatientById
- modifySupplierById
- placeOrderForMedicine

Metody modyfikujące encje przy pomocy Id przyjmują konwencje zgodnie z którą przyjmują listę arguentów identyczną jak metody dodajace te encje, natomiast metoda zakłada że jeśli dany argument ma wartość null to pole to nie jest modyfikowane w zmienianej encji.

### Metody dostępowe do danych - [DataAccessHandler](https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/Handlers/DataAccessHandler.java)
Interfejs służący do uzyskiwania dancyh z bazy zawarty jest w klasie DataAccessHandler, która udostępnia poniższe metody:
- getAll<EntitityClass>s
- get<EntityClass>ById
- getAllPatientsCurrentlyOnMed
- getAllPatientsThatUsedMed
- getPrescriptionElements
- getMedicineSuppliers
- getMedicineSuppliers
  
Metody te umożliwają uzyskanie informacji o każdej interesujacej relacji występującej w bazie oraz o każdej encji, umożliwają również  analizę danych, na przykład uzyskanie listy wszystkich pacjentów aktualnie przyjmujących dany lek (getAllPatientsCurrentlyOnMed).
  
Wszystkie metody udostępnianie przez wymienione klasy z rodziny Handler odpowiadają same za otworzenie nowej sesji oraz zamknięcie jej po wykonaniu potrzebnych operacji.

## Istotne klasy - [LogicUtils](https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/LogicUtils)
Klasy, które nie odbowiadają bezpośrdenio z strukturę bazy ani za operacje na niej, umieszczono w katalogu [LogicUtils](https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/LogicUtils).

W celu ułatwienia obsługi operacji na bazie dodano typ wyliczeniowy [DosageType](DosageType.java) reprezentujący jednostkę dawkowania leku, oraz udostępniający statyczne metody fromString i toString. Dość ściśle z typem DosageType związana jest klasa parser, która udostepnia metody statyczne służące do uzyskiwania z podanego Stringa wartości liczbowej opisującej dawkę oraz związaną z nią jednostkę dawkowania:
- parseDosageUnit
- parseDosageValue  
  
Dodatkowo klasa [Parser](Parser.java) udostępnia szereg matod walidacyjnych używanych w klasach z rodziny Handler w celu sprawdzania poprawności dancyh wprowadzanych przez użytkownika:  

- isValidEmail
- isValidPhone
- isValidName
- isValidSurname
- isValidZipCode
- isValidCity

### Klasy opisujące encje - [Entities](https://https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/Entities)
Projekt zawiera również katalog Entities w którym znajdują się klasy opisujące poszczególne encje które są mapowane na tabele z użyciem Hibernate.

W relacji między klasą [Supplier](Supplier.java), a klasą [Medicine](Medicine.java), zdecydowano się na użycie Eager Loadingu, ze względu na uproszczenie operacji związanych z dodawaniem nowych połączeń w ramach tej relacji.


## GUI - [GUI](https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/Gui)
W celu lepszej prezentacji użyteczności API zaimplementowano prosty interfejs graficzny umożliwajacy interakcję z bazą przy pomocy udostępnionego API.
Klasy odpowiedzialne za interfejs graficzny znajdują się w katalogu [Gui]((https://github.com/KonradPR/BD_projekt_2020/tree/master/Projekt%20Szpital/src/Gui)).


## Uwagi techniczne
Baza hostowana jest na serwerach udostępnionych przez AGH.







